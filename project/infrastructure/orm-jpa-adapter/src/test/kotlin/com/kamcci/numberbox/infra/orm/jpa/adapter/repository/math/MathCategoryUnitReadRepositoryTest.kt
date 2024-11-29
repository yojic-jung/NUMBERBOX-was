package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathCategoryUnitReadRepositoryTest(
    @Autowired
    private val mathCategoryUnitReadRepository: MathCategoryUnitReadRepository
) {
    @Test
    fun `단원 조회`() {
        val cateList = mathCategoryUnitReadRepository.readAll()

        assertThat(cateList.size).isGreaterThan(0)
    }
}