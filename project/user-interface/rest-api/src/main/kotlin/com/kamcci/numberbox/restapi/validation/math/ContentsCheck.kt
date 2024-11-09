package com.kamcci.numberbox.restapi.validation.math

import com.kamcci.numberbox.app.port.orm.math.MathContentsReadOrmPort
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import org.springframework.stereotype.Component
import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.TYPE,
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER,
)
@Constraint(validatedBy = [ContentsCheckValidator::class])
annotation class ContentsCheck(
    val message: String = "존재하지 않는 수학 문제 id 입니다.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)

@Component
class ContentsCheckValidator(
    private val mathContentsReadOrmPort: MathContentsReadOrmPort
) : ConstraintValidator<ContentsCheck, Long> {
    override fun isValid(
        value: Long,
        context: ConstraintValidatorContext?,
    ): Boolean {
        return mathContentsReadOrmPort.existById(value)
    }
}