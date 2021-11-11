package com.example.kira.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "comments")
class Comment(
        @ManyToOne(fetch = FetchType.EAGER)
        var user: User,
        var comment: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
}
