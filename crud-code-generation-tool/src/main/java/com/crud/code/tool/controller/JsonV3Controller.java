package com.crud.code.tool.controller;

import com.crud.code.tool.config.GenerateCode;
import com.crud.code.tool.service.JsonV3Service;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/json")
public class JsonV3Controller {

    @PostMapping("/download")
    public ResponseEntity<byte[]> test(
            @RequestParam String javaPackage,
            @RequestBody String content) {

        // 检查参数是否为空
        if (javaPackage == null || javaPackage.isEmpty() || content == null || content.isEmpty()) {
            return ResponseEntity.badRequest().body("javaPackage和content不能为空".getBytes());
        }

        // 读取body数据
        List<GenerateCode> list = JsonV3Service.readContent(content);

        // 获取临时目录
        String tempDir = System.getProperty("java.io.tmpdir");
        String outputDir = tempDir + File.separator + "output";

        File outputDirectory = new File(outputDir);
        try {
            if (!outputDirectory.exists()) {
                outputDirectory.mkdirs();
            }
            JsonV3Service.cal(
                    list,
                    javaPackage,
                    outputDir
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("文件写入失败".getBytes());
        }

        String zipFileName = "code_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".zip";

        // 创建ZIP文件
        String zipFilePath = tempDir + File.separator + zipFileName;
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            // 递归添加文件到ZIP
            addFilesToZip(outputDirectory, zos, outputDirectory.getName());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ZIP文件创建失败".getBytes());
        }

        // 返回ZIP文件
        Path zipPath = Paths.get(zipFilePath);
        byte[] zipBytes;
        try {
            zipBytes = Files.readAllBytes(zipPath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("读取ZIP文件失败".getBytes());
        }

        // 删除临时文件和目录
        try {
            Files.deleteIfExists(zipPath);
            deleteDirectory(outputDirectory);
        } catch (IOException e) {
            e.printStackTrace();
            // 这里可以选择记录日志，或者其他处理方式
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFileName);
        return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
    }

    // 递归添加文件到ZIP
    private void addFilesToZip(File file, ZipOutputStream zos, String basePath) throws IOException {
        if (file.isDirectory()) {
            // 创建一个ZIP条目，用于文件夹
            ZipEntry zipEntry = new ZipEntry(basePath + File.separator + file.getName() + File.separator);
            zos.putNextEntry(zipEntry);
            zos.closeEntry(); // 目录条目不需要写入内容，直接关闭

            // 递归处理子文件和子文件夹
            for (File subFile : file.listFiles()) {
                addFilesToZip(subFile, zos, basePath + File.separator + file.getName());
            }
        } else {
            // 添加文件
            ZipEntry zipEntry = new ZipEntry(basePath + File.separator + file.getName());
            zos.putNextEntry(zipEntry);
            Files.copy(file.toPath(), zos);
            zos.closeEntry();
        }
    }

    // 删除目录及其内容
    private void deleteDirectory(File directory) throws IOException {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                Files.deleteIfExists(file.toPath());
            }
        }
        Files.deleteIfExists(directory.toPath());
    }


    public static void main(String[] args) throws Exception {
        String jsonFileName = "teamplate.settings.json";
        // String output = "crud-code-generation-tool/src/main/resources/output";
        String output = "dashboard-core-service/src/main/java/gt/dashboard/code/svc";

        List<GenerateCode> list = JsonV3Service.readFile(jsonFileName);
        JsonV3Service.cal(
                list,
                "com.crud.code.svc",
                output
        );


    }

}
