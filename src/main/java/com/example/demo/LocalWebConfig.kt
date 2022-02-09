package com.example.demo

import org.h2.server.web.WebServlet
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("local")
class LocalWebConfig {

    @Bean
    fun h2servletRegistration(): ServletRegistrationBean<*> =
        ServletRegistrationBean(WebServlet()).apply {
            addUrlMappings("/h2-console/*")
        }
}