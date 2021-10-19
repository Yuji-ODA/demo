package com.example.demo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "preference")
@Component
class Preference(var topPage: PageProperty?, var detailPage: PageProperty?)

class PageProperty(var listLength: Int, var isChecked: Boolean)
