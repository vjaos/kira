package com.example.kira.entity

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(
    var username: String,
    var password: String,
    @OneToMany(fetch = FetchType.EAGER)
    val roles: Set<Role> = emptySet(),

    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinTable(
        name = "user_to_task",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "task_id")])
    val assignedTasks: MutableSet<Task> = mutableSetOf()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val userId: Long? = null
}
