package com.example.demo.controller;

import com.example.demo.controller.form.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestOperations;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("post2")
public class Post2Controller {

    private final RestOperations restOperations;
    private final ObjectMapper objectMapper;
    private final CustomMyClassTypeEditor myClassTypeEditor;

    @GetMapping
    public String postData() throws Exception {
        ByteArrayResource resource = new ByteArrayResource("俺のファイル".getBytes(StandardCharsets.UTF_8))
//        {
//            @Override
//            public String getFilename() {
//                return "data.csv";
//            }
//        }
        ;
        HogeSend hoge = new HogeSend("hoge", 100, resource, new MyClassSend("kuso", 10, resource),
                Arrays.asList(new MyClass2("aho", 1, null), new MyClass2("baka", 2, null)));

        Map<String, Object> dest = objectMapper.convertValue(hoge, new TypeReference<>() {});
        dest.put("file", resource);

//        ((Map<String, Object>)dest.get("myClass")).put("file", new ByteArrayResource("なんだ馬鹿野郎".getBytes(StandardCharsets.UTF_8)));
//        dest.put("file", new ByteArrayResource("なんだ馬鹿野郎".getBytes(StandardCharsets.UTF_8)));

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.setAll(dest);
        RequestEntity<MultiValueMap<String, Object>> requestEntity = RequestEntity
                .post(URI.create("http://localhost:8080/post2"))
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body);

        ResponseEntity<String> responseEntity = restOperations.exchange(requestEntity, String.class);
        return responseEntity.getBody();
    }

    @InitBinder("hoge")
    public void bindValues(WebDataBinder binder, HttpServletRequest req) {
        MutablePropertyValues values = new MutablePropertyValues();
        values.add("myName", req.getParameter("my_name"));
        values.add("seqNo", req.getParameter("seq_no"));
        values.add("file", req.getParameter("file"));
        values.add("seqNo", req.getParameter("seq_no"));
        values.add("myClass", req.getParameter("my_class"));
        values.add("myClasses", req.getParameter("my_classes"));

        values.forEach(propertyValue -> System.out.println(propertyValue.getName() + ": " + propertyValue.getValue()));

        binder.registerCustomEditor(MyClass.class, myClassTypeEditor);
        binder.bind(values);
    }

    @PostMapping
    public String recvData(@ModelAttribute Hoge hoge) throws IOException {
        System.out.println(hoge);
        return objectMapper.writeValueAsString(hoge);
    }

    @Component
    @RequiredArgsConstructor
    public static class CustomMyClassTypeEditor extends PropertyEditorSupport {

        private final ObjectMapper mapper;

        @Override
        @SneakyThrows
        public String getAsText(

        ) {
            MyClass myClass = (MyClass)getValue();
            return mapper.writeValueAsString(myClass);
        }

        @Override
        @SneakyThrows
        public void setAsText(String text) throws IllegalArgumentException {
            setValue(mapper.readValue(text, MyClass.class));
        }
    }
}
