package com.kamcci.numberbox.infra.orm.converter

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class VerifyCodeTypeConverter : AttributeConverter<VerifyCodeType, Int> {
    override fun convertToEntityAttribute(column: Int): VerifyCodeType? {
        return VerifyCodeType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: VerifyCodeType): Int {
        return property.id
    }
}