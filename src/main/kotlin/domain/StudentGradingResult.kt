package com.seungilahn.domain

class StudentGradingResult private constructor(
    private val assignment: PieceAssignment,
    private val gradedProblems: List<GradedProblem>
) {
    fun getPieceId(): Long = assignment.piece.id!!
    fun getStudentId(): Long = assignment.studentId
    fun getGradedProblems(): List<GradedProblem> = gradedProblems

    data class GradedProblem(
        val problemId: Long,
        val studentAnswer: String,
        val correctAnswer: String,
        val isCorrect: Boolean
    )

    companion object {
        fun create(
            assignment: PieceAssignment,
            problems: List<Problem>,
            studentAnswers: List<StudentAnswer>
        ): StudentGradingResult {
            val problemMap = problems.associateBy { it.id!! }

            val gradedProblems = studentAnswers.map { studentAnswer ->
                val problem = problemMap[studentAnswer.problemId]
                    ?: throw IllegalArgumentException("존재하지 않는 문제입니다: ${studentAnswer.problemId}")

                GradedProblem(
                    problemId = problem.id!!,
                    studentAnswer = studentAnswer.answer,
                    correctAnswer = problem.answer,
                    isCorrect = problem.isCorrect(studentAnswer.answer)
                )
            }
            return StudentGradingResult(assignment, gradedProblems)
        }
    }
}
