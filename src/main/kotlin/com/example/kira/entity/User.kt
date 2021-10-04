package com.example.kira.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    var username: String,
    var password: String,
    @OneToMany(fetch = FetchType.EAGER)
    val roles: Set<Role> = emptySet()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
}
