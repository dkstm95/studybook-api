package com.seungilahn.application.port.out

import com.seungilahn.domain.Problem
import com.seungilahn.domain.ProblemSearchCriteria

interface ProblemRepository {
    fun fetchAllById(problemIds: List<Long>): List<Problem>
    fun fetchProblemsByCriteria(criteria: ProblemSearchCriteria): List<Problem>
}