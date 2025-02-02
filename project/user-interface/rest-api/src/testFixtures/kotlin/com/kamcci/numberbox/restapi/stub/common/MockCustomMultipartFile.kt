package com.kamcci.numberbox.restapi.stub.common

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.InputStream

/**
 * mockito에서 제공하는 MockMultipartFile은 originalFileName을 null로 선언할 수 없어 별도 구현
 * - 실제 스프링에서 사용하는 multipartFile은 originalFileName이 null이 나올 수 있음
 */
class MockCustomMultipartFile(
    private val name: String,
    private val originalFileName: String?,
    private val contentType: String,
    private val content: ByteArray
) : MultipartFile {
    override fun getInputStream(): InputStream {
        return content.inputStream()
    }

    override fun getName(): String {
        return name
    }

    override fun getOriginalFilename(): String? {
        return originalFileName
    }

    override fun getContentType(): String {
        return contentType
    }

    override fun isEmpty(): Boolean {
        return content.isEmpty()
    }

    override fun getSize(): Long {
        return content.size.toLong()
    }

    override fun getBytes(): ByteArray {
        return content
    }

    override fun transferTo(dest: File) {
    }
}