package com.seungilahn.application.port.out

import com.seungilahn.domain.Problem
import com.seungilahn.domain.ProblemSearchCriteria

interface ProblemRepository {
    fun findAllById(problemIds: List<Long>): List<Problem>
    fun findProblemsByCriteria(criteria: ProblemSearchCriteria): List<Problem>
}