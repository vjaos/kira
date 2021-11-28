package com.example.kira.entity

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "tasks")
data class Task(
        var name: String,
        var description: String,
        var status: TaskStatus,
        @OneToMany(fetch = FetchType.EAGER)
        var comments: Set<Comment> = emptySet(),
        var priority: String,
        @ManyToMany(mappedBy = "assignedTasks")
        var performers: MutableSet<User> = mutableSetOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val taskId: Long? = null
}
