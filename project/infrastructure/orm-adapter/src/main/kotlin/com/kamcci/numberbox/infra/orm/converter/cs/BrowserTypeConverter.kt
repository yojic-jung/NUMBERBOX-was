package com.kamcci.numberbox.infra.orm.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.BrowserType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class BrowserTypeConverter : AttributeConverter<BrowserType, String> {
    // LeftJoin으로 null 반환 가능
    override fun convertToEntityAttribute(column: String): BrowserType? {
        return BrowserType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: BrowserType): String {
        return property.id
    }
}