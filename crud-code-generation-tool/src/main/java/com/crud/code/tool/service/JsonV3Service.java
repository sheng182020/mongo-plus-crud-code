package com.crud.code.tool.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.crud.code.tool.config.ColumnInfo;
import com.crud.code.tool.config.GenerateCode;
import com.crud.code.tool.config.OptionConfig;
import com.crud.code.tool.config.TableInfo;
import com.crud.code.tool.generation.Generator;
import com.crud.code.tool.utils.Tools;
import net.minidev.json.writer.JsonReader;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class JsonV3Service {

    public static void cal(
            List<GenerateCode> list
            , String javaPackage
            , String outputPath
    ) throws Exception {

        // CommonCodeGenerator.initRemark();
        for (GenerateCode code : list) {
            String modelName = Tools.lowwerFirst(code.getObjectName());

            code.setModelName(modelName);
            code.setJavaPackage(javaPackage);

            // OptionConfig optionConfig = code.getOptionConfig();
            // optionConfig.setHandleTypePath(javaPackage+".base.handle");
            // code.setOptionConfig(optionConfig);

            Generator.cal(code);

            File targetDIR = new File(outputPath + "/" + modelName);
            targetDIR.mkdirs();
            new File(targetDIR, "/entity").mkdirs();
            new File(targetDIR, "/request").mkdirs();
            new File(targetDIR, "/response").mkdirs();
            new File(targetDIR, "/controller").mkdirs();
            new File(targetDIR, "/service/impl").mkdirs();

            writeFile(code.getControllerCode(), targetDIR.getPath() + "/controller/" + code.getControllerCodeFileName());
            writeFile(code.getServiceCode(), targetDIR.getPath() + "/service/" + code.getServiceCodeFileName());
            writeFile(code.getServiceImplCode(), targetDIR.getPath() + "/service/impl/" + code.getServiceImplCodeFileName());
            writeFile(code.getEntityCode(), targetDIR.getPath() + "/entity/" + code.getEntityCodeFileName());
            writeFile(code.getResponseCode(), targetDIR.getPath() + "/response/" + code.getResponseCodeFileName());
            writeFile(code.getResponseItemCode(), targetDIR.getPath() + "/response/" + code.getResponseItemCodeFileName());
            writeFile(code.getRequestCreateCode(), targetDIR.getPath() + "/request/" + code.getRequestCreateCodeFileName());
            writeFile(code.getRequestUpdateCode(), targetDIR.getPath() + "/request/" + code.getRequestUpdateCodeFileName());
            writeFile(code.getRequestSearchCode(), targetDIR.getPath() + "/request/" + code.getRequestSearchCodeFileName());
            writeFile(code.getRequestSearchPageCode(), targetDIR.getPath() + "/request/" + code.getRequestSearchPageCodeFileName());
            writeFile(code.getRequestDeleteCode(), targetDIR.getPath() + "/request/" + code.getRequestDeleteCodeFileName());

            if (Boolean.TRUE.equals(code.getOptionConfig().getIsHasEnum())) {
                new File(targetDIR, "/enums").mkdirs();
            }
            Map<String, String> map = code.getEnumsCode();
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            for (; it.hasNext(); ) {
                Map.Entry<String, String> entry = it.next();
                String fileName = entry.getKey();
                String enumCode = entry.getValue();

                writeFile(enumCode, targetDIR.getPath() + "/enums/" + fileName);
            }
        }
    }

    public static List<GenerateCode> readContent(String content) {
        List<GenerateCode> list = new ArrayList<>();
        if (JSONUtil.isTypeJSONArray(content)) {
            // 解析为 JSONArray
            JSONArray jsonArray = new JSONArray(content);
            System.out.println("这是一个 JSON 数组，包含 " + jsonArray.size() + " 个元素。");
            // 处理 JSONArray 的逻辑
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // 在这里处理每个 JSONObject
                list.add(processJsonObject(jsonObject));
            }
        } else if (JSONUtil.isTypeJSONObject(content)) {
            // 解析为 JSONObject
            JSONObject jsonObject = new JSONObject(content);
            System.out.println("这是一个 JSON 对象。");
            // 处理 JSONObject 的逻辑
            list.add(processJsonObject(jsonObject));
        }
        return list;
    }

    public static List<GenerateCode> readFile(String fileName) {

        String filePath = "templates/" + fileName; // JSON 文件路径

        try {
            // 使用 ClassLoader 读取资源文件
            InputStream inputStream = JsonReader.class.getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                System.out.println("文件未找到: " + filePath);
                throw new RuntimeException("文件未找到");
            }

            // 读取文件内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            String jsonString = content.toString();
            return readContent(jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static GenerateCode processJsonObject(JSONObject jsonObject) {

        GenerateCode code = new GenerateCode();
        // 获取数据
        String kind = jsonObject.getStr("kind");
        String collectionName = jsonObject.getStr("collectionName");
        String name = jsonObject.getJSONObject("info").getStr("name");
        String description = jsonObject.getJSONObject("info").getStr("description", "");

        OptionConfig optionConfig = new OptionConfig();
        // 可以判断是否生成列表查询api, 有些只需要生成single api
        // if ("singleType".equals(kind)) {
        //     optionConfig.setIsHasPage(Boolean.FALSE);
        //     optionConfig.setIsHasList(Boolean.FALSE);
        // } else if ("collectionType".equals(kind)) {
        //     optionConfig.setIsHasPage(Boolean.TRUE);
        //     optionConfig.setIsHasList(Boolean.TRUE);
        // }
        optionConfig.setIsHasPage(Boolean.TRUE);
        optionConfig.setIsHasList(Boolean.TRUE);

        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableType(kind);
        tableInfo.setTableName(collectionName);
        tableInfo.setTableComment(description);

        code.setTable(tableInfo);
        code.setObjectName(Tools.toHump(name));

        List<ColumnInfo> columnInfoList = new ArrayList<>();
        // 读取 attributes
        JSONObject attributes = jsonObject.getJSONObject("attributes");
        attributes.keySet().forEach(key -> {
            System.out.println(String.format("%s: Handle %s ing...", collectionName, key));
            JSONObject attribute = attributes.getJSONObject(key);
            ColumnInfo columnInfo = convertColumnType(attribute);
            String fieldName = convertFieldName(key);
            if (StrUtil.isNotBlank(fieldName)){
                columnInfo.setDbFieldName(key);
                columnInfo.setColumnName(fieldName);
            }else{
                columnInfo.setColumnName(key);
            }

            if (columnInfo.getIsEnum()) {
                optionConfig.setIsHasEnum(Boolean.TRUE);
            }
            if (columnInfo.getKeyFlag()){
                optionConfig.setIsHasObjectId(Boolean.TRUE);
            }
            columnInfoList.add(columnInfo);
        });
        code.setOptionConfig(optionConfig);
        code.setColumns(columnInfoList);
        return code;
    }

    private static String convertFieldName(String fieldName) {
        if (fieldName.contains("_")) {
            return Tools.toHump(fieldName);
        } else {
            return null;
        }
    }

    /**
     * 写入代码
     */
    private static void writeFile(String code, String filePath) throws IOException {
        Files.writeString(new File(filePath).toPath(), code, Charset.forName("utf8"));
    }

    public static ColumnInfo convertColumnType(JSONObject attribute) {
        ColumnInfo columnInfo = new ColumnInfo();
        Boolean required = attribute.getBool("required", Boolean.FALSE);
        String model = attribute.getStr("model");
        List<String> enumList = attribute.getBeanList("enum", String.class);
        String columnType = attribute.getStr("type", "");
        String plugin = attribute.getStr("plugin");
        String collection = attribute.getStr("collection");
        String defaultValue = attribute.getStr("default");
        Boolean isHasField = Boolean.TRUE;
        String javaClassName = "Object";
        String javaPackage = "";
        Boolean isEnum = Boolean.FALSE;
        Boolean keyFlag = Boolean.FALSE;

        if (StrUtil.isNotBlank(collection) || StrUtil.isNotBlank(model)) {
            javaClassName = String.class.getSimpleName();
            keyFlag = Boolean.TRUE;
        } else if (StrUtil.isNotBlank(plugin)
                || columnType.equalsIgnoreCase("relation")
                || columnType.equalsIgnoreCase("component")
        ) {
            isHasField = Boolean.FALSE;
        } else if (
                columnType.equalsIgnoreCase("string") ||
                        columnType.equalsIgnoreCase("richtext") ||
                        columnType.equalsIgnoreCase("date") ||
                        columnType.equalsIgnoreCase("email")
        ) {
            javaClassName = String.class.getSimpleName();
            // javaPackage = "java.lang.String";
        }
        if (columnType.equalsIgnoreCase("enumeration")
                || columnType.equalsIgnoreCase("enum")) {
            javaClassName = Enum.class.getSimpleName();
            isEnum = Boolean.TRUE;
            // javaPackage="X";
        } else if (columnType.equalsIgnoreCase("double")) {
            Double maximum = attribute.getDouble("maximum");
            Double min = attribute.getDouble("min");
            columnInfo.setMax(maximum);
            columnInfo.setMin(min);
            javaClassName = Double.class.getSimpleName();

        } else if (columnType.equalsIgnoreCase("float")) {
            Float maximum = attribute.getFloat("maximum");
            Float min = attribute.getFloat("min");
            columnInfo.setMax(maximum);
            columnInfo.setMin(min);
            javaClassName = Float.class.getSimpleName();
        } else if (columnType.equalsIgnoreCase("decimal")) {
            BigDecimal maximum = attribute.getBigDecimal("maximum");
            BigDecimal min = attribute.getBigDecimal("min");
            columnInfo.setMax(maximum);
            columnInfo.setMin(min);
            javaClassName = BigDecimal.class.getSimpleName();
            javaPackage="java.math.BigDecimal";
        } else if (columnType.equalsIgnoreCase("bool")
                || columnType.equalsIgnoreCase("boolean")
        ) {
            javaClassName = Boolean.class.getSimpleName();
        } else if (columnType.equalsIgnoreCase("biginteger")
                || columnType.equalsIgnoreCase("long")
                || columnType.equalsIgnoreCase("number")
        ) {
            BigInteger maximum = attribute.getBigInteger("maximum");
            BigInteger min = attribute.getBigInteger("min");
            columnInfo.setMax(maximum);
            columnInfo.setMin(min);
            javaClassName = Long.class.getSimpleName();
        } else if (columnType.equalsIgnoreCase("int")
                || columnType.equalsIgnoreCase("tinyint")
                || columnType.equalsIgnoreCase("smallint")
                || columnType.equalsIgnoreCase("integer")
        ) {
            Integer maximum = attribute.getInt("maximum");
            Integer min = attribute.getInt("min");
            columnInfo.setMax(maximum);
            columnInfo.setMin(min);
            javaClassName = Integer.class.getSimpleName();
        } else if (columnType.equalsIgnoreCase("object")
                || columnType.equalsIgnoreCase("json")
        ) {
            javaClassName = Object.class.getSimpleName();
        } else if (columnType.equalsIgnoreCase("datetime")
                // || columnType.equalsIgnoreCase("date")
                || columnType.equalsIgnoreCase("time")
        ) {
            javaPackage="java.util.Date";
            javaClassName = Date.class.getSimpleName();
        }
        columnInfo.setDefaultValue(defaultValue);
        columnInfo.setIsEnum(isEnum);
        columnInfo.setJavaClassName(javaClassName);
        columnInfo.setKeyFlag(keyFlag);
        columnInfo.setJavaPackage(javaPackage);
        columnInfo.setEnumList(enumList);
        columnInfo.setFieldExists(isHasField);
        columnInfo.setRequired(required);
        columnInfo.setColumnType(columnType);
        return columnInfo;
    }


}
