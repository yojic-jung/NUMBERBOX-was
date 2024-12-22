package com.kamcci.numberbox.restapi.validation.member

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.TYPE,
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
)
@Constraint(validatedBy = [PhoneCheckValidator::class])
annotation class PhoneCheck(
    val message: String = "유효한 휴대폰 번호 형식이 아닙니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class PhoneCheckValidator : ConstraintValidator<PhoneCheck, String> {
    override fun isValid(
        value: String,
        context: ConstraintValidatorContext?,
    ): Boolean {
        return if (value.length < 10 || value.length > 11) false
        else {
            value.all { it.isDigit() }
        }
    }
}