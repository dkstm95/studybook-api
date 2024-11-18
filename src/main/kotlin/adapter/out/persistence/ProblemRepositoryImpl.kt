package com.seungilahn.adapter.out.persistence

import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import com.seungilahn.application.port.out.ProblemRepository
import com.seungilahn.domain.Problem
import com.seungilahn.domain.ProblemSearchCriteria
import com.seungilahn.domain.QProblem
import org.springframework.stereotype.Repository

@Repository
class ProblemRepositoryImpl(
    private val jpaRepository: SpringDataProblemRepository,
    private val jpaQueryFactory: JPAQueryFactory
) : ProblemRepository {

    override fun fetchAllById(problemIds: List<Long>): List<Problem> {
        return jpaRepository.fetchAllById(problemIds)
    }

    override fun fetchProblemsByCriteria(criteria: ProblemSearchCriteria): List<Problem> {
        val qProblem = QProblem.problem
        val builder = BooleanBuilder()

        if (criteria.unitCodeList.isNotEmpty()) {
            builder.and(qProblem.unitCode.code.`in`(criteria.unitCodeList))
        }

        if (criteria.problemType != null) {
            builder.and(qProblem.type.eq(criteria.problemType))
        }

        if (criteria.levelRange.isNotEmpty()) {
            builder.and(qProblem.difficultyLevel.`in`(criteria.levelRange))
        }

        return jpaQueryFactory.selectFrom(qProblem)
            .innerJoin(qProblem.unitCode).fetchJoin()
            .where(builder)
            .fetch()
    }

}