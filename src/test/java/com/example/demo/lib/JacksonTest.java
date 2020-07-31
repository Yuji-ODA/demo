package com.example.demo.lib;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Arrays;
import java.util.Map;

public class JacksonTest {

    @Test
    void test() throws Exception {
        ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
                .failOnUnknownProperties(false)
                .featuresToEnable(SerializationFeature.INDENT_OUTPUT)
                .featuresToDisable(SerializationFeature.CLOSE_CLOSEABLE)
                .build();

        ObjectNode tree = JsonNodeBuilder.builder()
                .set("name", "orehadarenanoka")
                .set("age", 100053)
                .setArray("history", "first", "second", 1)
                .build();

        
        System.out.println(mapper.convertValue(tree, new TypeReference<Map<String, ?>>() {}));

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
