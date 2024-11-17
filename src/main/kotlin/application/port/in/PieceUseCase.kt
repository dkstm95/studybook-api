package com.seungilahn.application.port.`in`

import com.seungilahn.adapter.`in`.web.GetProblemResponse
import com.seungilahn.application.port.out.*
import com.seungilahn.domain.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PieceUseCase(
    private val problemRepository: ProblemRepository,
    private val pieceRepository: PieceRepository,
    private val pieceAssignmentRepository: PieceAssignmentRepository,
    private val pieceProblemRepository: PieceProblemRepository,
    private val studentProblemGradeRepository: StudentProblemGradeRepository,
) {

    companion object {
        private const val MAX_PIECE_PROBLEMS_COUNT = 50
    }

    @Transactional
    fun create(teacherId: Long, pieceName: String, problemIds: List<Long>): CreatePieceResponse {
        if (problemIds.isEmpty() || problemIds.size > MAX_PIECE_PROBLEMS_COUNT) {
            throw IllegalArgumentException("문제는 1개 이상 ${MAX_PIECE_PROBLEMS_COUNT}개 이하로 선택해주세요.")
        }

        val newPiece = pieceRepository.save(
            Piece.withoutId(teacherId = teacherId, name = pieceName)
        )

        val problems = problemRepository.fetchAllById(problemIds)
        pieceProblemRepository.saveAll(
            problems.map { PieceProblem.withoutId(pieceId = newPiece.id!!, problemId = it.id!!) }
        )

        return CreatePieceResponse(
            pieceId = newPiece.id!!,
            pieceName = newPiece.name,
            teacherId = newPiece.teacherId,
            problems = problems.map { GetProblemResponse.from(it) }
        )
    }

    @Transactional
    fun assign(pieceId: Long, studentIds: List<Long>, teacherId: Long): AssignPieceResponse {
        val piece = pieceRepository.findByIdOrNull(pieceId)
            ?: throw IllegalArgumentException("존재하지 않는 학습지입니다.")

        if (!piece.isSameTeacher(teacherId)) {
            throw IllegalArgumentException("본인이 생성한 학습지만 배정할 수 있습니다.")
        }

        val assignedStudentIds = pieceAssignmentRepository.findAllByPieceId(pieceId).map { it.studentId }
        val unassignedStudentIds = studentIds.filter { it !in assignedStudentIds }

        pieceAssignmentRepository.saveAll(
            unassignedStudentIds.map { PieceAssignment.withoutId(studentId = it, piece = piece) }
        )

        return AssignPieceResponse(
            pieceId = piece.id!!,
            assignedStudentsIds = studentIds,
        )
    }

    @Transactional
    fun gradePieceProblems(
        pieceId: Long,
        request: GradePieceProblemsRequest,
        studentId: Long
    ): GradePieceProblemsResponse {
        val assignment = pieceAssignmentRepository.findByPieceIdAndStudentId(pieceId, studentId)
            ?: throw IllegalArgumentException("해당 학습지가 배정되지 않았습니다.")

        val pieceProblemIds = pieceProblemRepository.findAllByPieceId(assignment.piece.id!!).map { it.problemId }
        val requestProblemIds = request.studentProblemAnswers!!.map { it.problemId!! }

        require(pieceProblemIds.containsAll(requestProblemIds)) {
            "학습지에 포함된 문제만 채점할 수 있습니다."
        }

        val problems = problemRepository.fetchAllById(requestProblemIds)
        val problemMap = problems.associateBy { it.id!! }

        val studentProblemGrades = createStudentProblemGrades(
            studentProblemAnswers = request.studentProblemAnswers,
            problemMap = problemMap,
            studentId = studentId,
            pieceId = pieceId
        )

        studentProblemGradeRepository.saveAll(studentProblemGrades)

        return createGradePieceProblemsResponse(
            pieceId = pieceId,
            studentId = studentId,
            studentProblemAnswers = request.studentProblemAnswers,
            studentProblemGrades = studentProblemGrades,
            problemMap = problemMap
        )
    }

    private fun createStudentProblemGrades(
        studentProblemAnswers: List<GradePieceProblemsRequest.StudentProblemAnswer>,
        problemMap: Map<Long, Problem>,
        studentId: Long,
        pieceId: Long
    ): List<StudentProblemGrade> {
        return studentProblemAnswers.map { studentProblemAnswer ->
            val problem = problemMap[studentProblemAnswer.problemId!!]!!
            val isCorrect = problem.isCorrect(studentProblemAnswer.studentAnswer!!)
            StudentProblemGrade.withoutId(
                studentId = studentId,
                pieceId = pieceId,
                problemId = problem.id!!,
                isCorrect = isCorrect
            )
        }
    }

    private fun createGradePieceProblemsResponse(
        pieceId: Long,
        studentId: Long,
        studentProblemAnswers: List<GradePieceProblemsRequest.StudentProblemAnswer>,
        studentProblemGrades: List<StudentProblemGrade>,
        problemMap: Map<Long, Problem>
    ): GradePieceProblemsResponse {
        val studentProblemGradesMap = studentProblemGrades.associateBy { it.problemId }
        return GradePieceProblemsResponse(
            pieceId = pieceId,
            studentId = studentId,
            gradedProblems = studentProblemAnswers.map { studentProblemAnswer ->
                val studentProblemGrade = studentProblemGradesMap[studentProblemAnswer.problemId!!]!!
                GradePieceProblemsResponse.GradedProblem(
                    problemId = studentProblemAnswer.problemId,
                    studentAnswer = studentProblemAnswer.studentAnswer!!,
                    correctAnswer = problemMap[studentProblemAnswer.problemId]!!.answer,
                    isCorrect = studentProblemGrade.isCorrect
                )
            }
        )
    }

}
