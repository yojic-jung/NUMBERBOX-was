package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import com.kamcci.numberbox.infra.orm.jpa.adapter.sample.math.MathContentsSampleData
import com.kamcci.numberbox.infra.orm.jpa.adapter.sample.math.MathContentsSampleData.getMathContentsModifyDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathContentsFactoryTest {

    // given
    val modifyDtoList = listOf(
        // 주관식, 객관식 정답 존재
        Triple(getMathContentsModifyDto("123", listOf("1", "2")), MultiChoiceType.Multiple, true),
        // 주관식, 객관식 정답 미존재
        Triple(getMathContentsModifyDto(null, null), MultiChoiceType.Essay, false),
        // 주관식, 객관식 정답 미존재
        Triple(getMathContentsModifyDto("", null), MultiChoiceType.Essay, false),
        // 주관식, 객관식 정답 미존재
        Triple(getMathContentsModifyDto("", listOf()), MultiChoiceType.Essay, false),
        // 주관식, 객관식 정답 미존재
        Triple(getMathContentsModifyDto(null, listOf()), MultiChoiceType.Essay, false),
        // 주관식 미존재, 객관식 정답 존재
        Triple(getMathContentsModifyDto(null, listOf("1")), MultiChoiceType.Multiple, true),
        // 주관식 미존재, 객관식 정답 존재
        Triple(getMathContentsModifyDto("", listOf("1")), MultiChoiceType.Multiple, true),
        // 주관식 존재, 객관식 정답 미존재
        Triple(getMathContentsModifyDto("123", null), MultiChoiceType.Essay, true),
        // 주관식 존재, 객관식 정답 미존재
        Triple(getMathContentsModifyDto("123", listOf()), MultiChoiceType.Essay, true),
    )

    @Test
    fun `MathContentsEntity_getSaveEntity - 브랜치 커버리지 테스트`() {
        // when
        for (modifyDto in modifyDtoList) {
            val saveEntity =
                MathContentsFactory.getSaveEntity(ContentsSvcPosbSttsType.Release, modifyDto.first)

            assertThat(saveEntity.multiChoiceType).isEqualTo(modifyDto.second)
            assertThat(saveEntity.ansExistStts).isEqualTo(modifyDto.third)
        }
    }

    @Test
    fun `MathContentsEntity_getUpdtEntity - 브랜치 커버리지 테스트`() {
        // when
        for (modifyDto in modifyDtoList) {
            val saveEntity =
                MathContentsFactory.getUpdtEntity(
                    MathContentsSampleData.getSaveEntity(),
                    ContentsSvcPosbSttsType.Release,
                    modifyDto.first
                )

            assertThat(saveEntity.multiChoiceType).isEqualTo(modifyDto.second)
            assertThat(saveEntity.ansExistStts).isEqualTo(modifyDto.third)
        }
    }
}