package com.example.demo.lib;

import com.example.demo.controller.form.HogeSend;
import com.example.demo.controller.form.MyClass2;
import com.example.demo.controller.form.MyClassSend;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.example.demo.lib.Functions.propertiesRecursiveEach;

class FunctionsTest {

    @Test
    void testDumpProperties() {
        ByteArrayResource resource = new ByteArrayResource("俺のファイル".getBytes(StandardCharsets.UTF_8));
        HogeSend hoge = new HogeSend("hoge", 100, null, new MyClassSend("kuso", 10, resource),
                Arrays.asList(new MyClass2("aho", 1, null), new MyClass2("baka", 2, null)));

        propertiesRecursiveEach(hoge, (names, value) -> {
            String propertuName = String.join(".", names);
            if ("myClasses".equals(names.peek())) {
                List<?> list = (List<Object>)value;
                IntStream.range(0, list.size()).forEach(i -> {
                    Object obj = list.get(i);
                    if (obj instanceof MyClass2) {
                        System.out.println(propertuName + '[' + i + ']' + ": " + value);
                    }
                });

                return;
            }
            System.out.println(propertuName + ": " + value);
        });
    }
}