package com.lec.spring.training.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ImgService {

    void saveImages(List<MultipartFile> files, String dir) throws IOException;
    Path saveImage(MultipartFile file, String dir) throws IOException;
    byte[] loadImage(String filename, String dir) throws IOException;
    void deleteImage(String filename, String dir) throws IOException;

}
