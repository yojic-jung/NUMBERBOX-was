package com.kamcci.numberbox.restapi.controller.hwp

import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.common.FileUploadDto
import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent
import com.kamcci.numberbox.app.domain.enumeration.port.storage.FileType
import com.kamcci.numberbox.app.port.hwp.HwpConvertEventPort
import com.kamcci.numberbox.app.usecase.common.FileUseCase
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
        request: HwpConvertRequest
    ): ResponseEntity<ResponseData<String>> {
        val event = JsonToHwpRequestEvent(memberId, request.jsonMsg)
        // todo kafka에 이벤트 정상 전송 됬는지는 알아야함
        hwpEventPort.requestHwp(event)
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
        req: HwpFileConvertRequest
    ): ResponseEntity<ResponseData<String>> {
        // s3에 파일 업로드
        val fileUpldDto = FileUploadDto("hwp", "application/octet-stream", req.hwpFile.size, req.hwpFile.inputStream)
        val fileNameVo = fileUseCase.upload(fileUpldDto, FileType.JsonToHWP)

        // 업로드한 hwp 파일경로 이벤트 전달
        val event = HwpToHtmlRequestEvent(memberId, "${fileNameVo.path}/${fileNameVo.name}")
        hwpEventPort.requestHtml(event)

        // 4. 변환 컨텐츠 조회
        return ResponseUtil.ok()
    }

// todo
//
//    // 변환 컨텐츠 수정사항 저장
//    @PutMapping("/hwp-to-html")
//    fun update(
//        @UserId memberId: UUID,
//        @RequestBody
//        request: HwpToHtmlUpdateRequest
//    ): ResponseEntity<ResponseData<Any>> {
//        // 변환 컨텐츠 수정
//        hwpConvertContentsWriteCase.update(
//            HwpConvertContentsUpdateDto(
//                id = request.id,
//                memberId = memberId,
//                contents = request.contents,
//                isGrammarConverted = true
//            )
//        ).let { if (it != 1L) throw BusinessInValidException(NOT_MODIFIED) }
//
//        // 변환 컨텐츠 조회
//        val contentsList = hwpConvertContentsReadCase.readAllByMemberId(memberId)
//        return ResponseUtil.ok(mapOf("contentsList" to contentsList))
//    }
//
//    // 변환 컨텐츠 수정사항 저장
//    @DeleteMapping("/hwp-to-html/{contentsId}")
//    fun delete(
//        @UserId memberId: UUID,
//        @PathVariable contentsId: Long
//    ): ResponseEntity<ResponseData<Any>> {
//        // 변환 컨텐츠 수정
//        hwpConvertContentsWriteCase.delete(contentsId, memberId)
//            .let { if (it != 1L) throw BusinessInValidException(NOT_MODIFIED) }
//
//        // 변환 컨텐츠 조회
//        val contentsList = hwpConvertContentsReadCase.readAllByMemberId(memberId)
//        return ResponseUtil.ok(mapOf("contentsList" to contentsList))
//    }
}