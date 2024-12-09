package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.FormulaClassificationType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class FormulaClassificationTypeConverter : AttributeConverter<FormulaClassificationType, String> {
    override fun convertToEntityAttribute(column: String?): FormulaClassificationType? {
        return FormulaClassificationType.entries.find { it.dbData == column }
    }

    override fun convertToDatabaseColumn(property: FormulaClassificationType): String {
        return property.name.lowercase()
    }
}