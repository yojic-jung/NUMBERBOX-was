package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathContentsIpsiReadRepositoryTest(
    @Autowired
    private val mathContentsIpsiReadRepository: MathContentsIpsiReadRepository
) {
    @Test
    fun `제공되는 입시 수학 문제 연도 조회`() {
        mathContentsIpsiReadRepository.readAllIpsiYear()
    }
}