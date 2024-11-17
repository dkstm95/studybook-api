package com.seungilahn.adapter.out.persistence

import com.seungilahn.application.port.out.StudentProblemGradeRepository
import com.seungilahn.domain.StudentProblemGrade
import org.springframework.stereotype.Repository

@Repository
class StudentProblemGradeRepositoryImpl(
    private val jpaRepository: SpringDataStudentProblemGradeRepository,
) : StudentProblemGradeRepository {

    override fun saveAll(studentProblemGrades: List<StudentProblemGrade>): List<StudentProblemGrade> {
        return jpaRepository.saveAll(studentProblemGrades)
    }

}