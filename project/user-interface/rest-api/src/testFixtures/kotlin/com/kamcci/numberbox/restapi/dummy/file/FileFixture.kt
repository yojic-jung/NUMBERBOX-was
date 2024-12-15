package com.kamcci.numberbox.restapi.dummy.file

import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile

object FileFixture {
    /**
     * MultipartFile 파일
     */
    fun getMultipartFile(name: String, originFileName: String) = MockMultipartFile(
        name,
        originFileName,
        MediaType.MULTIPART_FORM_DATA_VALUE,
        "content".encodeToByteArray()
    )
}