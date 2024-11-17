package com.seungilahn.application.port.`in`.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class GradePieceProblemsRequest(
    @field:NotEmpty
    val studentProblemAnswers: List<StudentProblemAnswer>?
) {
    data class StudentProblemAnswer(
        @field:NotNull
        val problemId: Long?,

        @field:NotBlank
        val studentAnswer: String?
    )
}
