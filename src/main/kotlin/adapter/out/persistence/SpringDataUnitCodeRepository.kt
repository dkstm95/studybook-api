package com.seungilahn.adapter.out.persistence

import com.seungilahn.domain.UnitCode
import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataUnitCodeRepository : JpaRepository<UnitCode, Long> {
}