package com.example.demo.lib;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Arrays;

public class JsonNodeBuilder {
    private final JsonNodeFactory factory;
    private final ObjectNode node;

    private JsonNodeBuilder() {
        factory = JsonNodeFactory.instance;
        node = factory.objectNode();
    }

    private JsonNodeBuilder(ObjectNode node) {
        factory = JsonNodeFactory.instance;
        this.node = node;
    }

    public static JsonNodeBuilder builder() {
        return new JsonNodeBuilder();
    }

    public ObjectNode build() {
        return node;
    }

    public JsonNodeBuilder set(String key, Object value) {
        return new JsonNodeBuilder(node.set(key, factory.pojoNode(value)));
    }

    public JsonNodeBuilder setArray(String key, Object... values) {
        return new JsonNodeBuilder(node.set(key, Arrays.stream(values)
                .reduce(factory.arrayNode(), ArrayNode::addPOJO, ArrayNode::addAll)));
    }
}
