package com.example.demo.lib;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Arrays;

public class JacksonTest {

    @Test
    void test() throws Exception {
        ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
                .failOnUnknownProperties(false)
                .featuresToEnable(SerializationFeature.INDENT_OUTPUT)
                .featuresToDisable(SerializationFeature.CLOSE_CLOSEABLE)
                .build();

        ObjectNode tree = node()
                .<ObjectNode>set("name", valueOf("orehadarenanoka"))
                .<ObjectNode>set("age", valueOf(100053))
                .set("history", arrayOf("first", "second"));

        System.out.println(mapper.writeValueAsString(tree));
    }

    static ObjectNode node() {
        return JsonNodeFactory.instance.objectNode();
    }

    static ValueNode valueOf(Object value) {
        return JsonNodeFactory.instance.pojoNode(value);
    }

    static ArrayNode emptyArray() {
        return JsonNodeFactory.instance.arrayNode();
    }

    static ArrayNode arrayOf(Object... values) {
        return Arrays.stream(values)
                .reduce(emptyArray(), ArrayNode::addPOJO, ArrayNode::addAll);
    }
}
