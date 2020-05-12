package com.example.demo.controller;

import com.example.demo.controller.form.Hoge;
import com.example.demo.controller.form.MyClass;
import com.example.demo.controller.form.MyClass2;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final ObjectMapper objectMapper;
    private final RestOperations restOperations;

    @GetMapping(path = "/post")
    public String postData() throws Exception {
        Hoge hoge = new Hoge("hoge", 100, new MyClass("kuso", 10), Arrays.asList(new MyClass2("aho", 1), new MyClass2("baka", 2)));
//		Hoge hoge = new Hoge("hoge", 100, new MyClass("kuso", 10), Collections.emptyList());
        System.out.println(hoge);

        Map<String, Object> dest = objectMapper.convertValue(hoge, new TypeReference<>() {});
        System.out.println(objectMapper.writeValueAsString(hoge));

        for (Map.Entry<String, Object> entry : dest.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.setAll(dest);
        RequestEntity<MultiValueMap<String, Object>> requestEntity = RequestEntity.post(URI.create("http://localhost:8080/recv"))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body);

        ResponseEntity<String> responseEntity = restOperations.exchange(requestEntity, String.class);
        return responseEntity.getBody();
    }

    @PostMapping(path = "/recv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String recvData(@ModelAttribute Hoge fo) throws Exception {
        String response = fo.toString();
        System.out.println(response);
        return response;
    }
}
