package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class ContentsClassifyTypeConverter : AttributeConverter<ContentsClassifyType, Int> {
    override fun convertToEntityAttribute(column: Int?): ContentsClassifyType? {
        return ContentsClassifyType.entries.find { it.dbData == column }
    }

    override fun convertToDatabaseColumn(property: ContentsClassifyType): Int {
        return property.dbData
    }
}