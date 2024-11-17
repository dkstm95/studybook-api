package com.seungilahn.adapter.out.persistence

import com.seungilahn.domain.Problem
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataProblemRepository : JpaRepository<Problem, Long> {
}