package com.kamcci.numberbox.restapi.validation.file

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
@Constraint(validatedBy = [ImgFileCheckValidator::class])
annotation class ImgFileCheck(
    val message: String = "이미지 파일 형식만 가능합니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class ImgFileCheckValidator : ConstraintValidator<ImgFileCheck, MultipartFile> {
    companion object {
        private val ImgExtensions = listOf("jpg", "jpeg", "png", "bmp", "gif", "tif", "tiff", "webp", "heic", "heif")
        private val MaxFileSize = 20 * 1024 * 1024 // 20MB (바이트 단위)

        fun isValidImg(value: MultipartFile?): Boolean {
            // null인 경우 체크 안함
            if (value == null || value.isEmpty) return true

            // 최대 사이즈 체크
            if (value.size > MaxFileSize) return false

            // 허용된 확장자에 포함되는지 확인
            val fileName = value.originalFilename ?: return false
            val extension = fileName.substringAfterLast('.', "").lowercase()
            return ImgExtensions.contains(extension)
        }
    }

    override fun isValid(
        value: MultipartFile?,
        context: ConstraintValidatorContext?,
    ): Boolean {
        return isValidImg(value)
    }
}