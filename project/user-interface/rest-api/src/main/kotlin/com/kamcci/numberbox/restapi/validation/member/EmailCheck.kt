package com.kamcci.numberbox.restapi.validation.member

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.TYPE,
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
)
@Constraint(validatedBy = [EmailCheckValidator::class])
annotation class EmailCheck(
    val message: String = "유효한 이메일 형식이 아닙니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

@Component
class EmailCheckValidator(
    @Value("\${regex.member.email}")
    private val emailRegex: String
) : ConstraintValidator<EmailCheck, String> {
    private lateinit var regexPattern: Regex
    override fun initialize(constraintAnnotation: EmailCheck?) {
        // Regex 객체 초기화
        regexPattern = Regex(emailRegex)
    }

    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?,
    ): Boolean {
        return value?.let { regexPattern.matches(it) } ?: false
    }
}