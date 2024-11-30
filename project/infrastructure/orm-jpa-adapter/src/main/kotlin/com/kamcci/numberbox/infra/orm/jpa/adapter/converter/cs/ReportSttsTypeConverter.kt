package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.ReportSttsType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class ReportSttsTypeConverter : AttributeConverter<ReportSttsType, Int> {
    // LeftJoin으로 null 반환 가능
    override fun convertToEntityAttribute(column: Int?): ReportSttsType? {
        return ReportSttsType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: ReportSttsType): Int {
        return property.id
    }
}