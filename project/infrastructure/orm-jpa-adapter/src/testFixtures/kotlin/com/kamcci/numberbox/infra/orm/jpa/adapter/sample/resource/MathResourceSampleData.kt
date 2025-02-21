package com.kamcci.numberbox.infra.orm.jpa.adapter.sample.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo

object MathResourceSampleData {
    fun getMathResourceUpdateDto(): MathResourceUpdateDto {
        return MathResourceUpdateDto(
            resourceId = 1L,
            title = "23423",
            pptFilePath = "123",
            pptFileName = "123",
            pptPageCnt = 3,
            imgPath = "123",
            imgName = "123",
            cateList = listOf("1-1"),
            imgList = listOf(FileNameVo("123", "213")),
        )
    }
}