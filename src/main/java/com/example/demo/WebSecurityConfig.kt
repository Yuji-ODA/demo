package com.example.demo

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // 認可の設定
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/console/**").permitAll()
                .anyRequest().authenticated()  // それ以外は全て認証無しの場合アクセス不許可
                .and()
                .formLogin()
                .and()
                .headers().frameOptions().disable()
    }
}