package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.resource.MathResourceImgDummyFactory.getMathResourceImgDummyEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathResourceImgWriteRepositoryTest @Autowired constructor(
    private val mathResourceImgWriteRepository: MathResourceImgWriteRepository
) {
    @Test
    fun `학습 자료 이미지 삭제`() {
        // given
        val resourceId = getMathResourceImgDummyEntity().resourceId

        // when
        val executeRowCnt = mathResourceImgWriteRepository.deleteByResourceId(resourceId)

        // then
        assertThat(executeRowCnt).isPositive()
    }
}