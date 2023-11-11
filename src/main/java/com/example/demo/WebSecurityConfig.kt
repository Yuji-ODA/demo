package com.example.demo

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.*
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.GrantedAuthoritiesContainer
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity(debug = true)
class WebSecurityConfig {

    @Bean
    fun webSecurityCustomizer() = WebSecurityCustomizer {
        it.ignoring().antMatchers("/img/**", "/js/**", "/css/**")
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        // 認可の設定
        http
            .csrf { it.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) }
            .authorizeHttpRequests {
                it.antMatchers("/api/**", "/h2-console/**", "/login").permitAll()
                    .anyRequest().authenticated()
            }
            .antMatcher("/h2-console/**").headers { it.frameOptions().disable() }
            .addFilterBefore(securityFilter(), BasicAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
            }
//                .anyRequest().permitAll()
//                .antMatchers("/**").hasIpAddress("127.0.0.0/8")
//                .antMatchers("/**", "/post/**").permitAll()
//                    .anyRequest().permitAll()
//                .anyRequest().authenticated()
//                    .anyRequest().hasAnyRole("USER") // それ以外は全て認証無しの場合アクセス不許可
//                .addFilterBefore(MyFilter(), BasicAuthenticationFilter::class.java)
//            .addFilterBefore(MySecurityFilter(AntPathRequestMatcher("/book/**")), BasicAuthenticationFilter::class.java)
//            .addFilterBefore(MySecurityFilter(AntPathRequestMatcher("/post/**")), BasicAuthenticationFilter::class.java)
//            .addFilterBefore(MyFilter(AntPathRequestMatcher("/")), BasicAuthenticationFilter::class.java)
//                .exceptionHandling().authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
//                .exceptionHandling().accessDeniedHandler { request, response, accessDeniedException ->
//                    response.sendRedirect("http://www.yahoo.co.jp")
//                }
//                .formLogin()
//                .and()
//                .headers().frameOptions().disable()
//                .and()
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .build()

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

    fun authenticationManager(): AuthenticationManager {
        val provider = object : AuthenticationProvider {
            override fun authenticate(authentication: Authentication?): Authentication? = authentication
                ?.apply { isAuthenticated = true }
            override fun supports(authentication: Class<*>?): Boolean = true
        }

        return ProviderManager(provider)
    }

    fun securityFilter() = MySecurityFilter(authenticationManager = authenticationManager())

    class MySecurityFilter(private val requestMatcher: RequestMatcher = RequestMatcher { true }, authenticationManager: AuthenticationManager) : AbstractAuthenticationProcessingFilter(requestMatcher, authenticationManager) {

        override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication =
            MyAuthentication("ユーザ", Collections.singletonList(SimpleGrantedAuthority("ROLE_USER")))
                .apply { details = authenticationDetailsSource.buildDetails(request) }
                .run { authenticationManager.authenticate(this) }
    }

    class MyPreauthFilter : RequestHeaderAuthenticationFilter() {
        override fun getPreAuthenticatedPrincipal(request: HttpServletRequest?): Any {
            TODO("Not yet implemented")
        }

        override fun getPreAuthenticatedCredentials(request: HttpServletRequest?): Any {
            TODO("Not yet implemented")
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

