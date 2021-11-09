package com.example.kira.repository

import com.example.kira.entity.Sprint
import org.springframework.data.repository.CrudRepository

interface SprintRepository : CrudRepository<Sprint, Long>
