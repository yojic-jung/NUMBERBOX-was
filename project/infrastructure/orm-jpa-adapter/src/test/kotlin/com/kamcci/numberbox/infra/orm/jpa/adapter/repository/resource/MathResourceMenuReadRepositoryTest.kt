package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathResourceMenuReadRepositoryTest @Autowired constructor(
    private val mathResourceMenuReadRepository: MathResourceMenuReadRepository
) {
    @Test
    fun `학습 자료 메뉴 조회`() {
        // when
        val resourceMenuList = mathResourceMenuReadRepository.readAll()

        // then
        assertThat(resourceMenuList.size).isPositive()
    }

}