package org.example.controller;

import org.example.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return Result.error("上传文件不能为空");
        }

        // 项目根目录下的 files 文件夹
        Path uploadDir = Paths.get(System.getProperty("user.dir"), "files");
        Files.createDirectories(uploadDir);

        String originalFilename = file.getOriginalFilename();
        String ext = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        String newFileName = UUID.randomUUID().toString().replace("-", "") + ext;
        Path target = uploadDir.resolve(newFileName);

        // 保存文件
        file.transferTo(target.toFile());

        // 返回相对路径，便于前端或后续使用
        return Result.success("files/" + newFileName);
    }
}
