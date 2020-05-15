package com.example.demo.lib;

import com.example.demo.controller.PostController;
import org.junit.jupiter.api.Test;

import static com.example.demo.lib.Functions.dumpProperties;

class FunctionsTest {

    @Test
    void testDumpProperties() {
        PostController.Person ore = new PostController.Person(1, "小田雄二",
                new PostController.Address("埼玉県越谷市東越谷３８－２５", null));

        dumpProperties(ore, (name, value) -> {
            if ("class".equals(name)) {
                return;
            }

            if ("address".equals(name)) {
                dumpProperties(value, (name2, value2) -> {
                    if ("class".equals(name2)) {
                        return;
                    }
                    System.out.println("address." + name2 + ": " + value2);
                });
                return;
            }

            System.out.println(name + ": " + value);
        });
    }
}