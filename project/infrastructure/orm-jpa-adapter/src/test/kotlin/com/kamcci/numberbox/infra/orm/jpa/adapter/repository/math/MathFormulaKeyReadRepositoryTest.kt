package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathFormulaKeyReadRepositoryTest(
    @Autowired
    private val mathFormulaKeyReadRepository: MathFormulaKeyReadRepository
) {
    @Test
    fun `수식 단축키 조회`() {
        // when
        val formulKeyList = mathFormulaKeyReadRepository.readAll()

        // then
        assertThat(formulKeyList.size).isGreaterThan(0)
    }

}