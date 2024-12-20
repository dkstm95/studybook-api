package com.seungilahn.application.port.`in`.response

import com.seungilahn.domain.Problem

data class GetProblemResponse(
    val id: Long,
    val answer: String,
    val unitCode: String,
    val level: Int,
    val problemType: String
) {
    companion object {
        fun from(problem: Problem) = GetProblemResponse(
            id = problem.id!!,
            answer = problem.answer,
            unitCode = problem.unitCode.code,
            level = problem.difficultyLevel,
            problemType = problem.type.name
        )
    }
}
