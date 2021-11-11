package com.example.kira.repository

import com.example.kira.entity.Task
import org.springframework.data.repository.CrudRepository

interface TaskRepository : CrudRepository<Task, Long>
