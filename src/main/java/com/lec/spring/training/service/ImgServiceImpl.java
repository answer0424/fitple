package com.lec.spring.training.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ImgServiceImpl implements ImgService {

    @Override
    public void saveImages(List<MultipartFile> files, String dir) throws IOException {
        for (MultipartFile file : files) {
            saveImage(file, dir);
        }
    }

    @Override
    public String saveImage(MultipartFile file, String dir) throws IOException {
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(dir, filename);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        return String.valueOf(path);
        // return 값을 String path으로 줌으로 써 DB에 경로를 저장할 수 있게 한다..
    }

    @Override
    public byte[] loadImage(String filename, String dir) throws IOException {
        Path path = Paths.get(dir, filename);
        return Files.readAllBytes(path);
    }

    @Override
    public void deleteImage(String filename, String dir) throws IOException {
        Path path = Paths.get(dir, filename);
        Files.deleteIfExists(path);
    }

}// end TrainerDetailService
