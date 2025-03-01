package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.math

import com.kamcci.numberbox.infra.orm.jpa.adapter.annotation.TcDBJpaTest
import com.kamcci.numberbox.infra.orm.jpa.adapter.dummy.math.MathCategoryDummyFactory.getMathCateUnitIdList
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@TcDBJpaTest
class MathCategoryTypeReadRepositoryTest(
    @Autowired
    private val mathCategoryTypeReadRepository: MathCategoryTypeReadRepository
) {
    private val mathCateUnitIdList = getMathCateUnitIdList()

    @Test
    fun `단원id로 유형 조회`() {
        // given
        val anyUnitId = mathCateUnitIdList.first()

        // when
        val typeList = mathCategoryTypeReadRepository.readByUnitId(anyUnitId)

        assertThat(typeList.size).isPositive()
    }

    @Test
    fun `단원id List로 유형 조회`() {
        // given
        val anyUnitIdList = mathCateUnitIdList.subList(0, 10)

        // when
        val typeList = mathCategoryTypeReadRepository.readByUnitId(anyUnitIdList)

        assertThat(typeList.size).isPositive()
    }

}