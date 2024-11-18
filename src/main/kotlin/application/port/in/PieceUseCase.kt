package com.seungilahn.application.port.`in`

import com.seungilahn.application.port.`in`.request.GradePieceProblemsRequest
import com.seungilahn.application.port.`in`.response.AssignPieceResponse
import com.seungilahn.application.port.`in`.response.CreatePieceResponse
import com.seungilahn.application.port.`in`.response.GetProblemResponse
import com.seungilahn.application.port.`in`.response.GradePieceProblemsResponse
import com.seungilahn.application.port.out.PieceProblemRepository
import com.seungilahn.application.port.out.PieceRepository
import com.seungilahn.application.port.out.ProblemRepository
import com.seungilahn.application.port.out.StudentProblemGradeRepository
import com.seungilahn.domain.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PieceUseCase(
    private val problemRepository: ProblemRepository,
    private val pieceRepository: PieceRepository,
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

        val newPiece = pieceRepository.savePiece(
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
        val piece = pieceRepository.findPieceById(pieceId)
            ?: throw IllegalArgumentException("존재하지 않는 학습지입니다.")

        if (!piece.isSameTeacher(teacherId)) {
            throw IllegalArgumentException("본인이 생성한 학습지만 배정할 수 있습니다.")
        }

        val assignedStudentIds = pieceRepository.findAllAssignmentByPieceId(pieceId).map { it.studentId }
        val unassignedStudentIds = studentIds.filter { it !in assignedStudentIds }

        pieceRepository.saveAllAssignment(
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
        val studentAnswers = request.toStudentAnswers()

        val assignment = pieceRepository.findAssignmentByPieceIdAndStudentId(pieceId, studentId)
            ?: throw IllegalArgumentException("해당 학습지가 배정되지 않았습니다.")

        val problems = problemRepository.fetchAllById(studentAnswers.map { it.problemId })

        val gradingResult = assignment.grade(problems, studentAnswers)

        saveGrades(gradingResult)

        return GradePieceProblemsResponse(
            pieceId = gradingResult.getPieceId(),
            studentId = gradingResult.getStudentId(),
            gradedProblems = gradingResult.getGradedProblems().map {
                GradePieceProblemsResponse.GradedProblem(
                    problemId = it.problemId,
                    studentAnswer = it.studentAnswer,
                    correctAnswer = it.correctAnswer,
                    isCorrect = it.isCorrect
                )
            }
        )
    }

    private fun saveGrades(gradingResult: GradingResult) {
        studentProblemGradeRepository.saveAll(
            gradingResult.getGradedProblems().map { problem ->
                StudentProblemGrade.withoutId(
                    studentId = gradingResult.getStudentId(),
                    pieceId = gradingResult.getPieceId(),
                    problemId = problem.problemId,
                    isCorrect = problem.isCorrect
                )
            }
        )
    }

}
