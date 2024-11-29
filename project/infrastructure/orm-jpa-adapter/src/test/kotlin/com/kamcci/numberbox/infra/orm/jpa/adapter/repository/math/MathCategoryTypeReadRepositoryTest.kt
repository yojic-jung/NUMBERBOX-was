package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathCategoryTypeReadRepositoryTest(
    @Autowired
    private val mathCategoryTypeReadRepository: MathCategoryTypeReadRepository
) {
    @Test
    fun `단원id로 유형 조회`() {
        val typeList = mathCategoryTypeReadRepository.readByUnitId(21001)

        assertThat(typeList.size).isGreaterThan(0)
    }

    @Test
    fun `단원id List로 유형 조회`() {
        val typeList = mathCategoryTypeReadRepository.readByUnitId(listOf(21001, 21002))

        assertThat(typeList.size).isGreaterThan(0)
    }

}