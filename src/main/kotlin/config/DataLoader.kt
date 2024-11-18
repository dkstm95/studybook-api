package com.seungilahn.config

import com.seungilahn.adapter.out.persistence.SpringDataProblemRepository
import com.seungilahn.adapter.out.persistence.SpringDataUnitCodeRepository
import com.seungilahn.domain.Problem
import com.seungilahn.domain.ProblemType
import com.seungilahn.domain.UnitCode
import jakarta.annotation.PostConstruct
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val problemRepository: SpringDataProblemRepository,
    private val unitCodeRepository: SpringDataUnitCodeRepository,
) {

    @PostConstruct
    fun loadExcelData() {
        val file = ClassPathResource("static/backend_recruit_data.xlsx").inputStream
        val workbook = XSSFWorkbook(file)

        val unitCodeSheet = workbook.getSheet("unit_code")
        for (row in unitCodeSheet) {
            if (row.rowNum == 0 || row.rowNum == 1) continue

            val code = row.getCell(1).stringCellValue
            val name = row.getCell(2).stringCellValue

            unitCodeRepository.save(UnitCode.withoutId(code, name))
        }

        val unitCodeMap = unitCodeRepository.findAll().associateBy { it.code }

        val problemSheet = workbook.getSheet("problem")
        for (row in problemSheet) {
            if (row.rowNum == 0 || row.rowNum == 1) continue

            val problemId = row.getCell(0).numericCellValue.toLong()
            val unitCodeStr = row.getCell(1).stringCellValue
            val level = row.getCell(2).numericCellValue.toInt()
            val type = row.getCell(3).stringCellValue
            val answer = row.getCell(4).stringCellValue

            val unitCode = unitCodeMap[unitCodeStr]
                ?: throw IllegalArgumentException("Invalid unit code: $unitCodeStr")

            problemRepository.save(
                Problem.withId(
                    id = problemId,
                    difficultyLevel = level,
                    type = ProblemType.fromString(type),
                    answer = answer,
                    unitCode = unitCode
                )
            )
        }

        workbook.close()
    }

}