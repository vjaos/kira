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

@Entity
@Table(name = "sprints")
class Sprint(
        var name: String,
        var targets: String? = "",
        @OneToMany(fetch = FetchType.EAGER)
        var tasks: Set<Task> = emptySet(),
        @Temporal(TemporalType.DATE)
        var deadline: LocalDate
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null
}
