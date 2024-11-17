package com.seungilahn.application.port.out

import com.seungilahn.domain.StudentProblemGrade

interface StudentProblemGradeRepository {
    fun saveAll(studentProblemGrades: List<StudentProblemGrade>): List<StudentProblemGrade>
}
