package com.kamcci.numberbox.restapi.dummy.file

import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile

object FileFixture {
    /**
     * 이미지 파일
     */
    fun getImgFile(name: String, originFileName: String) = MockMultipartFile(
        name,
        originFileName,
        MediaType.MULTIPART_FORM_DATA_VALUE,
        "content".encodeToByteArray()
    )
}