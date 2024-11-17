package com.seungilahn.domain

data class ProblemSearchCriteria(
    val unitCodeList: List<String>,
    val problemType: ProblemType?,
    val levelRange: List<Int>,
)
