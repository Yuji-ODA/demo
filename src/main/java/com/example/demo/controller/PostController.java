package com.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("post")
public class PostController {

    private final RestOperations restOperations;
    private final ObjectMapper objectMapper;

    @GetMapping
    public String postData() throws Exception {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("id", 1);
        body.add("name", "小田雄二");
        body.add("address.description", "埼玉県越谷市東越谷３８－２５");
        body.add("address.file", new ByteArrayResource("現住所はココ！！".getBytes(StandardCharsets.UTF_8)) {
            @Override
            public String getFilename() {
                return "data.csv";
            }
        });

        RequestEntity<MultiValueMap<String, Object>> requestEntity = RequestEntity
                .post(URI.create("http://localhost:8080/post"))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body);

        ResponseEntity<String> responseEntity = restOperations.exchange(requestEntity, String.class);
        return responseEntity.getBody();
    }

    @PostMapping
    public String recvData(@ModelAttribute Person person) throws IOException {
        System.out.println(person);
        MultipartFile file = person.getAddress().getFile();
        System.out.println(new String(file.getBytes(), StandardCharsets.UTF_8));
        return objectMapper.writeValueAsString(person);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Person {
        private int id;
        private String name;
        private Address address;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Address {
        private String description;
        private MultipartFile file;
    }
}
