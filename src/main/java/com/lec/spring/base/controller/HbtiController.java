package com.lec.spring.base.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/api/hbti")
public class HbtiController {

    private final Path jsonFilePath;

    @Autowired
    public HbtiController(Path hbtiDataPath) {
        this.jsonFilePath = hbtiDataPath;
    }

    @GetMapping("/data")
    public Map<String, Object> getHbtiData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = Files.readString(jsonFilePath);
        return mapper.readValue(jsonContent, new TypeReference<Map<String, Object>>() {});
    }
}
