package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.resource.MathResourceCateDummyFactory.getMathResourceCateDummyEntity
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathResourceCateWriteRepositoryTest @Autowired constructor(
    private val mathResourceCateWriteRepository: MathResourceCateWriteRepository
) {
    private val mathResourceDummyEntity = getMathResourceCateDummyEntity()

    @Test
    fun `학습 자료 카테고리 삭제`() {
        // when
        val executeRowCnt = mathResourceCateWriteRepository.deleteByResourceId(mathResourceDummyEntity.resourceId)

        // then
        Assertions.assertThat(executeRowCnt).isPositive()
    }

}