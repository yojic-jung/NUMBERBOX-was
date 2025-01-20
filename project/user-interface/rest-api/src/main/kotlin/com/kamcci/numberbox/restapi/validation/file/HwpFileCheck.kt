package com.kamcci.numberbox.restapi.validation.file

import com.kamcci.numberbox.app.domain.dto.hwp.HwpExtensionType
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import org.springframework.web.multipart.MultipartFile
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.TYPE,
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
)
@Constraint(validatedBy = [HwpFileCheckValidator::class])
annotation class HwpFileCheck(
    val message: String = "20MB 이하의 hwp, hwpx, hwt, hwtx, hml 파일 형식만 가능합니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class HwpFileCheckValidator : ConstraintValidator<HwpFileCheck, MultipartFile> {
    companion object {
        private val HwpExtensions = HwpExtensionType.entries.map { it.name.lowercase() }
        const val MAX_FILE_SIZE = 20 * 1024 * 1024 // 20MB (바이트 단위)

        fun isValidPpt(value: MultipartFile): Boolean {
            // 최대 사이즈 체크
            if (value.size > MAX_FILE_SIZE) return false

            // 허용된 확장자에 포함되는지 확인
            val fileName = value.originalFilename ?: return false
            val extension = fileName.substringAfterLast('.', "").lowercase()
            return HwpExtensions.contains(extension)
        }
    }

    override fun isValid(
        value: MultipartFile,
        context: ConstraintValidatorContext?,
    ): Boolean {
        return isValidPpt(value)
    }
}