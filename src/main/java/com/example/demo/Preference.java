package com.example.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "preference")
@Data
public class Preference {

    private PageProperty topPage;
    private PageProperty detailPage;

    @Data
    public static class PageProperty {
        private int listLength;
        private boolean checked;
    }
}
