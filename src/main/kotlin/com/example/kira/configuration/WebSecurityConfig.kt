package com.example.kira.configuration

import com.example.kira.service.auth.JwtAuthManager
import com.example.kira.service.auth.JwtServerAuthConverter
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.AnyRequestMatcher


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class WebSecurityConfig() {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun configure(
        http: HttpSecurity,
        authManger: JwtAuthManager,
        jwtConverter: JwtServerAuthConverter
    ): SecurityFilterChain {
        val filter = AuthenticationFilter(authManger, jwtConverter)
        filter.setRequestMatcher(AntPathRequestMatcher("/api/**"))

        http
            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers("/login", "/register").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()


        return http.build()
    }

}