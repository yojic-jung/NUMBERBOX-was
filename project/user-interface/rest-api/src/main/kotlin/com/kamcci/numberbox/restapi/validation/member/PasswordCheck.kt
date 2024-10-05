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
@Constraint(validatedBy = [PasswordCheckValidator::class])
annotation class PasswordCheck(
    val message: String = "유효한 비밀번호 형식이 아닙니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

@Component
class PasswordCheckValidator(
    @Value("\${regex.member.password}")
    private val passwordRegex: String
) : ConstraintValidator<PasswordCheck, String> {
    private lateinit var regexPattern: Regex
    override fun initialize(constraintAnnotation: PasswordCheck?) {
        // Regex 객체 초기화
        regexPattern = Regex(passwordRegex)
    }

    override fun isValid(
        value: String?,
        context: ConstraintValidatorContext?,
    ): Boolean {
        return value?.let { regexPattern.matches(it) } ?: false
    }
}