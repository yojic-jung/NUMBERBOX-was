package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.service.sample.MathResourceSampleData.getMathResourceUpdateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MathResourceFactoryTest {
    @Test
    fun `MathResourceFactory_getUpdateEntity - 브랜치 커버리지 테스트`() {
        // given
        val updateDtoList =
            listOf(
                getMathResourceUpdateDto(),
                MathResourceUpdateDto(
                    resourceId = 1L,
                    title = "",
                    pptFilePath = null,
                    pptFileName = null,
                    pptPageCnt = null,
                    imgPath = null,
                    imgName = null,
                    cateList = listOf("1-1"),
                    imgList = listOf(FileNameVo("123", "213")),
                )
            )
        for (updateDto in updateDtoList) {
            // when
            val updateEntity = MathResourceFactory.getUpdateEntity(MathResourceEntity(), updateDto)

            // then
            assertEntity(updateEntity, updateDto)
        }
    }

    private fun assertEntity(updateEntity: MathResourceEntity, updateDto: MathResourceUpdateDto) {
        assertThat(updateEntity.title).isEqualTo(updateDto.title)
        assertThat(updateEntity.imgPath).isEqualTo(updateDto.imgPath)
        assertThat(updateEntity.imgName).isEqualTo(updateDto.imgName)
        assertThat(updateEntity.pptPath).isEqualTo(updateDto.pptFilePath)
        assertThat(updateEntity.pptName).isEqualTo(updateDto.pptFileName)
        assertThat(updateEntity.pptPageCnt).isEqualTo(updateDto.pptPageCnt ?: 0)
    }

}