package com.crud.code.tool.generation;// package com.crud.code.tool.generation;
//
// import cn.hutool.core.util.StrUtil;
//
// public class SwaggerCodeGenerator {
//
//     public static String addApi(Boolean enable, String tags, String description) {
//         if (Boolean.TRUE.equals(enable)) {
//             String format = String.format("@Api(tags=\"%s\"", tags);
//             if (StrUtil.isNotBlank(description)) {
//                 return format + String.format(", description = \"%s\")\n", description);
//             }
//             return format + ")\n";
//         }
//         return "";
//     }
//
//     public static String importApi(Boolean enable) {
//         return Boolean.TRUE.equals(enable) ? "import io.swagger.v3.oas.annotations.tags.Tag;\n" : "";
//     }
//
//     public static String addSchema(Boolean enable,String name){
//
//     }
//
//     public static void main(String[] args) {
//         String s = addApi(true, "", null);
//         System.out.println(s);
//     }
//
// }
