package com.example.demo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "preference")
@Component
class Preference {

    var topPage: PageProperty? = null
    var detailPage: PageProperty? = null

    class PageProperty {
        var listLength: Int = 0
        var isChecked: Boolean = false
    }
}