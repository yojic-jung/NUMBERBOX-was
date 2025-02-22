package com.kamcci.numberbox.restapi.util.file

import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

/**
 * 파일 변환 유틸리티
 *
 * - 유틸 클래스를 통한 static 함수는 모킹 처리 어려움으로 DIP 활용
 */
interface FileUtil {
    fun toFile(multipartFile: MultipartFile): FileUploadDto

    fun toPptSlide(pptInp: InputStream): List<FileUploadDto>
}