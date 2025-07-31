package com.kamcci.numberbox.restapi.controller.hwp

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertFileCreateDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent
import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpConvertFileType
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.port.hwp.HwpConvertEventPort
import com.kamcci.numberbox.app.usecase.common.FileUseCase
import com.kamcci.numberbox.app.usecase.hwp.HwpConvertFileWriteCase
import com.kamcci.numberbox.restapi.dto.request.hwp.HwpConvertRequest
import com.kamcci.numberbox.restapi.dto.request.hwp.HwpFileConvertRequest
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * 한글 파일 변환 컨트롤러
 */
@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/hwp/convert/event")
class HwpConvertEventController(
    private val hwpEventPort: HwpConvertEventPort,
    private val fileUseCase: FileUseCase,
    private val hwpConvertFileWriteCase: HwpConvertFileWriteCase,
) {
    /**
     * json to hwp 변환
     * - 변환된 파일은 추후 알림을 통해 전달됨
     */
    @PostMapping("/json-to-hwp")
    fun makeHwpFile(
        @UserId
        memberId: UUID,
        @RequestBody
        request: HwpConvertRequest,
    ): ResponseEntity<ResponseData<String>> {
        // json 문자열 업로드
        val fileNameVo = fileUseCase.uploadJsonData(request.jsonMsg, FileType.JsonToHWP)

        // 변환 요청 정보 db 저장
        val createDto = HwpConvertFileCreateDto(
            memberId = memberId,
            convertType = HwpConvertFileType.JsonToHwp,
            originFileName = fileNameVo.getFileFullName(),
        )
        val id = hwpConvertFileWriteCase.create(createDto)

        // 변환 요청 이벤트 전송
        val event = JsonToHwpRequestEvent(id, fileNameVo.getFileFullName())
        try {
            hwpEventPort.requestHwp(event)
        } catch (e: Exception) {
            // 아무 처리 하지 않음 비동기 콜백에서 처리함
        }

        return ResponseUtil.ok()
    }

    /**
     * hwp to html 변환
     * - 변환된 파일은 추후 알림을 통해 전달됨
     */
    @PostMapping("/hwp-to-html")
    fun makeHtml(
        @UserId memberId: UUID,
        @ModelAttribute @Valid
        req: HwpFileConvertRequest,
    ): ResponseEntity<ResponseData<String>> {
        // s3에 변환 요청 파일 업로드
        val fileUpldDto = FileUploadDto(
            req.hwpFile.originalFilename!!,
            "application/octet-stream",
            req.hwpFile.size,
            req.hwpFile.inputStream,
        )
        val fileNameVo = fileUseCase.upload(fileUpldDto, FileType.HwpToHTML)

        // 변환 요청 정보 db 저장
        val createDto = HwpConvertFileCreateDto(
            memberId = memberId,
            convertType = HwpConvertFileType.HwpToHtml,
            originFileName = fileNameVo.getFileFullName(),
        )
        val id = hwpConvertFileWriteCase.create(createDto)

        // 변환 요청 이벤트 전달
        val event = HwpToHtmlRequestEvent(id, fileNameVo.getFileFullName())
        try {
            hwpEventPort.requestHtml(event)
        } catch (e: Exception) {
            throw e
        }

        // 변환 요청 이벤트 전달 성공 상태 변경
        hwpConvertFileWriteCase.updateIsRequestSuccess(id, true)

        return ResponseUtil.ok()
    }
}
