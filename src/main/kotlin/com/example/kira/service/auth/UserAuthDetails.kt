package com.example.kira.service.auth

import com.example.kira.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserAuthDetails(private val user: User) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        user.roles.map { role -> SimpleGrantedAuthority(role.toString()) }.toMutableList()

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}