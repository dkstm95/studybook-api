package com.seungilahn.adapter.`in`.web

import com.seungilahn.domain.Problem

data class GetProblemsResponse(
    val id: Long,
    val answer: String,
    val unitCode: String,
    val level: Int,
    val problemType: String
) {
    companion object {
        fun from(problem: Problem) = GetProblemsResponse(
            id = problem.id!!,
            answer = problem.answer,
            unitCode = problem.unitCode.code,
            level = problem.level,
            problemType = problem.type.name
        )
    }
}
