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
@Constraint(validatedBy = [BirthCheckValidator::class])
annotation class BirthCheck(
    val message: String = "유효한 생년월일 형식이 아닙니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

class BirthCheckValidator : ConstraintValidator<BirthCheck, String> {
    override fun isValid(
        value: String,
        context: ConstraintValidatorContext?,
    ): Boolean {
        return if (value.length != 6) false
        else {
            val result = runCatching {
                value.toInt()
            }
            result.isSuccess
        }
    }
}