package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathResourceCateWriteRepositoryTest(
    @Autowired
    private val mathResourceCateWriteRepository: MathResourceCateWriteRepository
) {
    @Test
    fun `학습 자료 카테고리 삭제`() {
        // when
        val executeRowCnt = mathResourceCateWriteRepository.deleteByResourceId(1)

        // then
        Assertions.assertThat(executeRowCnt).isGreaterThan(0)
    }

}