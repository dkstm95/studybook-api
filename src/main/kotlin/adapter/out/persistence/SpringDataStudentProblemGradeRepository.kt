package com.seungilahn.adapter.out.persistence

import com.seungilahn.domain.StudentProblemGrade
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataStudentProblemGradeRepository : JpaRepository<StudentProblemGrade, Long> {
    fun findAllByPieceId(pieceId: Long): List<StudentProblemGrade>
}