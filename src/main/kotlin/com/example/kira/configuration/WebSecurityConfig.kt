package com.example.kira.configuration

import com.example.kira.service.auth.DummyAuthSuccessHandler
import com.example.kira.service.auth.JwtAuthManager
import com.example.kira.service.auth.converter.JwtAuthConverter
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFilter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(12)

    @Bean
    fun configure(
        http: HttpSecurity,
        authManger: JwtAuthManager,
        jwtConverter: JwtAuthConverter,
        successHandler: DummyAuthSuccessHandler
    ): SecurityFilterChain {
        val filter = AuthenticationFilter(authManger, jwtConverter)
        filter.requestMatcher = AntPathRequestMatcher("/api/**")
        filter.successHandler = successHandler

        http
            .addFilterAt(filter, UsernamePasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers("/login", "/register").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic().disable()
            .formLogin().disable()
            .logout().disable()
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        return http.build()
    }
}
