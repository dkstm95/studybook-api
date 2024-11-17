package com.seungilahn.application.port.out

import com.seungilahn.domain.StudentProblemGrade

interface StudentProblemGradeRepository {
    fun findAllByPieceId(pieceId: Long): List<StudentProblemGrade>
    fun saveAll(studentProblemGrades: List<StudentProblemGrade>): List<StudentProblemGrade>
}
