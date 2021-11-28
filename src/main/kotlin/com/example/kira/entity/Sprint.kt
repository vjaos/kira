package com.example.kira.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.TemporalType
import org.springframework.data.jpa.repository.Temporal
import java.time.LocalDate
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne

@Entity
@Table(name = "sprints")
data class Sprint(
    var name: String,
    var targets: String? = "",
    @OneToMany(fetch = FetchType.EAGER)
    var tasks: MutableSet<Task> = mutableSetOf(),
    @Temporal(TemporalType.DATE)
    var deadline: LocalDate
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
}
