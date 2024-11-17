package com.seungilahn.adapter.out.persistence

import com.seungilahn.domain.Problem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SpringDataProblemRepository : JpaRepository<Problem, Long> {
    @Query("SELECT p FROM Problem p JOIN FETCH p.unitCode WHERE p.id IN :problemIds")
    fun fetchAllById(problemIds: List<Long>): List<Problem>
}