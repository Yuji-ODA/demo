package com.example.demo

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.GrantedAuthoritiesContainer
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@EnableWebSecurity(debug = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        this.authenticationManager()

        // 認可の設定
        http
            .csrf()
                .disable()
//                .csrfTokenRepository(CookieCsrfTokenRepository())
//            .and()
            .authorizeRequests()
//                .antMatchers("/**").hasIpAddress("127.0.0.0/8")
                .antMatchers("/**", "/post/**").permitAll()
                .antMatchers("/api/**", "/console/**").permitAll()
//                    .anyRequest().permitAll()
                .anyRequest().authenticated()
//                    .anyRequest().hasAnyRole("USER") // それ以外は全て認証無しの場合アクセス不許可
            .and()
//                .addFilterBefore(MyFilter(), BasicAuthenticationFilter::class.java)
            .addFilterBefore(MySecurityFilter(AntPathRequestMatcher("/book/**")), BasicAuthenticationFilter::class.java)
//            .addFilterBefore(MySecurityFilter(AntPathRequestMatcher("/post/**")), BasicAuthenticationFilter::class.java)
            .addFilterBefore(MyFilter(AntPathRequestMatcher("/")), BasicAuthenticationFilter::class.java)
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
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.authenticationProvider(object: AuthenticationProvider {
            override fun authenticate(authentication: Authentication?): Authentication? {
                return authentication?.apply {
                    isAuthenticated = true
                }
            }

            override fun supports(authentication: Class<*>?): Boolean = true
        })
    }

    class MyFilter(private val requestMatcher: RequestMatcher = RequestMatcher { true }) : OncePerRequestFilter() {
        override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
            if (requestMatcher.matches(request)) {
                val authentication = MyAuthentication("ユーザ", Collections.singletonList(SimpleGrantedAuthority("ROLE_USER")))
//            authentication.isAuthenticated = true
                SecurityContextHolder.getContext().authentication = authentication
            }

            filterChain.doFilter(request, response)
        }
    }

    class MySecurityFilter(private val requestMatcher: RequestMatcher = RequestMatcher { true }) : OncePerRequestFilter() {
        override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {

//            throw object: AuthenticationException("forbidden") {}
//            throw ForbiddenException("forbidden")

            if (requestMatcher.matches(request)) {
                val authentication = MyAuthentication("ユーザ", Collections.singletonList(SimpleGrantedAuthority("ROLE_USER")))
//            authentication.isAuthenticated = true
                SecurityContextHolder.getContext().authentication = authentication
            }

            filterChain.doFilter(request, response)
        }
    }

    fun example() {
        val filter = RequestHeaderAuthenticationFilter()
        val auth = AuthenticationDetailsSource<HttpServletRequest, GrantedAuthoritiesContainer> { TODO("Not yet implemented") }
        filter.setAuthenticationDetailsSource(auth)
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    class ForbiddenException(msg: String): RuntimeException(msg) {
        constructor(msg: String, t: Throwable) : this(msg)
    }

    class MyAuthentication(principal: Any, authorities: Collection<GrantedAuthority>) : AbstractAuthenticationToken(authorities) {
        private val principalAlias = principal

        override fun getCredentials(): Any = ""
        override fun getPrincipal(): Any = principalAlias
    }
}

