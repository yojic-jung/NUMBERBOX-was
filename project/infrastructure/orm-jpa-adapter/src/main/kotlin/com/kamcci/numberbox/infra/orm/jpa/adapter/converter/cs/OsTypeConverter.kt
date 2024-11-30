package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.cs

import com.kamcci.numberbox.app.domain.enumeration.cs.OsType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class OsTypeConverter : AttributeConverter<OsType, String> {
    // LeftJoin으로 null 반환 가능
    override fun convertToEntityAttribute(column: String?): OsType? {
        return OsType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: OsType): String {
        return property.id
    }
}