package com.seungilahn.application.port.`in`.request

import com.seungilahn.domain.StudentAnswer
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class GradePieceProblemsRequest(
    @field:NotEmpty(message = "Student problem answers must not be empty.")
    val studentProblemAnswers: List<StudentProblemAnswer>?
) {
    data class StudentProblemAnswer(
        @field:NotNull(message = "Problem ID must not be null.")
        val problemId: Long?,

        @field:NotBlank(message = "Student answer must not be blank.")
        val studentAnswer: String?
    )

    fun toStudentAnswers(): List<StudentAnswer> {
        return requireNotNull(studentProblemAnswers) { "Student problem answers must not be null." }
            .map { studentProblemAnswer ->
                StudentAnswer(
                    problemId = requireNotNull(studentProblemAnswer.problemId) { "Problem ID must not be null." },
                    answer = requireNotNull(studentProblemAnswer.studentAnswer) { "Student answer must not be null." }
                )
            }
    }
}
