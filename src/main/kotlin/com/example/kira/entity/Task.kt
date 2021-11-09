package com.example.kira.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "tasks")
data class Task(
        var name: String,
        var description: String? = "",
        var status: String? = "",
        @OneToMany(fetch = FetchType.EAGER)
        var comments: Set<Comment> = emptySet(),
        var priority: String? = "",
        @ManyToMany(fetch = FetchType.EAGER)
        var performers: Set<User> = emptySet()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
}
