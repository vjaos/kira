package com.example.kira.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table
import javax.persistence.TemporalType
import org.springframework.data.jpa.repository.Temporal

@Entity
@Table(name = "sprint")
class Sprint(
        var name: String,
        var targets: String,
        @ManyToMany(fetch = FetchType.EAGER)
        var tasks: Set<Task> = emptySet(),
        @Temporal(TemporalType.DATE)
        var deadline: Date
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
}