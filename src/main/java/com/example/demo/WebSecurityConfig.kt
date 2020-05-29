package com.example.demo

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity
open class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        // 認可の設定
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/console/**").permitAll()
                    .anyRequest().authenticated()
//                    .anyRequest().hasAnyRole("USER") // それ以外は全て認証無しの場合アクセス不許可
                .and()
//                .addFilterBefore(MyFilter(), BasicAuthenticationFilter::class.java)
                .addFilterBefore(MySecurityFilter(), BasicAuthenticationFilter::class.java)
                .exceptionHandling().authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
//                .formLogin()
                .and()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

//    @Configuration
//    open class FilterConf {
//        @Bean
//        open fun authenticationManager(): AuthenticationManager {
//            return AuthenticationManager {
//                it.apply { isAuthenticated = true }
//            }
//        }
//    }

//    class MyFilter : AbstractAuthenticationProcessingFilter("/login") {
//        override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
//            return MyAuthentication("ユーザ", Collections.singletonList(SimpleGrantedAuthority("ROLE_USER")))
//        }
//    }

    class MySecurityFilter : OncePerRequestFilter() {
        override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
            val authentication = MyAuthentication("ユーザ", Collections.singletonList(SimpleGrantedAuthority("ROLE_USER")))
            authentication.isAuthenticated = true
            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        }
    }

    class MyAuthentication(principal: Any, authorities: Collection<GrantedAuthority>) : AbstractAuthenticationToken(authorities) {

        private val principalHidden = principal

        override fun getCredentials(): Any = ""
        override fun getPrincipal(): Any = principalHidden
    }
}