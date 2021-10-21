package com.example.demo.lib;

import org.junit.jupiter.api.Test;

import java.util.*;

import static com.example.demo.lib.CollectionUtil.appendValue;
import static com.example.demo.lib.CollectionUtil.mergeListMap;
import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilTest {
    @Test
    void testAppendValue() {
        List<String> list = new ArrayList<>() {{
            add("value1");
        }};

        Map<String, List<String>> map1 = new HashMap<>() {{
          put("key1", list);
        }};

        appendValue(map1, "key1", "value2");

        assertThat(map1).containsOnly(Map.entry("key1", Arrays.asList("value1", "value2")));
    }

    @Test
    void testMergeListMap() {
        Map<String, List<String>> map1 = new HashMap<>() {{
            put("key1", new ArrayList<>() {{
                add("value1");
            }});
            put("key2", new ArrayList<>() {{
                add("value3");
            }});
        }};

        Map<String, List<String>> map2 = new HashMap<>() {{
            put("key1", new ArrayList<>() {{
                add("value2");
            }});
            put("key3", new ArrayList<>() {{
                add("value4");
            }});
        }};

        assertThat(mergeListMap(map1, map2))
                .containsOnly(Map.entry("key1", Arrays.asList("value1", "value2")),
                        Map.entry("key2", Arrays.asList("value3")),
                        Map.entry("key3", Arrays.asList("value4")));
    }
}
