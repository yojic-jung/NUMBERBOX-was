package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsIpsiReadRepositoryTest(
    @Autowired
    private val mathContentsIpsiReadRepository: MathContentsIpsiReadRepository
) {
    @Test
    fun `제공되는 입시 수학 문제 연도 조회`() {
        // when
        val ipsiYears = mathContentsIpsiReadRepository.readAllIpsiYear()

        // then
        assertThat(ipsiYears.size).isPositive()
    }
}