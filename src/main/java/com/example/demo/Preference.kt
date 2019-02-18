package com.example.demo

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "preference")
class Preference {

    var topPage: PageProperty? = null
    var detailPage: PageProperty? = null

    class PageProperty {
        var listLength: Int = 0
        var isChecked: Boolean = false
    }
}