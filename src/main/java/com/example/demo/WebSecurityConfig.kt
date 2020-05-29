package com.example.demo

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.bind.annotation.ResponseStatus
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
//                .exceptionHandling().authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
//                .exceptionHandling().accessDeniedHandler { request, response, accessDeniedException ->
//                    response.sendRedirect("http://www.yahoo.co.jp")
//                }
//                .formLogin()
//                .and()
//                .headers().frameOptions().disable()
//                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

//    @Configuration
//    open class FilterConf {
//        @Bean
//        open fun filter(): FilterRegistrationBean<MySecurityFilter> {
//            return FilterRegistrationBean(MySecurityFilter()).apply {
//                order = Ordered.HIGHEST_PRECEDENCE
//                addUrlPatterns("/**", "/", "/book")
//            }
//        }
//    }
//
//    override fun configure(auth: AuthenticationManagerBuilder?) {
//        auth?.authenticationProvider(object: AuthenticationProvider {
//            override fun authenticate(authentication: Authentication?): Authentication? {
//                return authentication?.apply {
//                    isAuthenticated = true
//                }
//            }
//
//            override fun supports(authentication: Class<*>?): Boolean = true
//        })
//    }

//    @Configuration
//    open class AuthenticationManagerConf {
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

//            throw object: AuthenticationException("forbidden") {}
//            throw ForbiddenException("forbidden")

            val authentication = MyAuthentication("ユーザ", Collections.singletonList(SimpleGrantedAuthority("ROLE_USER")))
            authentication.isAuthenticated = true
            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        }
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    class ForbiddenException(msg: String): RuntimeException(msg) {
        constructor(msg: String, t: Throwable) : this(msg)
    }

    class MyAuthentication(principal: Any, authorities: Collection<GrantedAuthority>) : AbstractAuthenticationToken(authorities) {

        private val principalHidden = principal

        override fun getCredentials(): Any = ""
        override fun getPrincipal(): Any = principalHidden
    }
}