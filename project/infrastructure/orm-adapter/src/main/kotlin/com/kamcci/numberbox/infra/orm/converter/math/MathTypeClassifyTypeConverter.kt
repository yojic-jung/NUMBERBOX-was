package com.kamcci.numberbox.infra.orm.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.MathTypeClassifyType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class MathTypeClassifyTypeConverter : AttributeConverter<MathTypeClassifyType?, String?> {
    override fun convertToEntityAttribute(column: String?): MathTypeClassifyType? {
        return MathTypeClassifyType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: MathTypeClassifyType?): String? {
        return property?.id
    }
}