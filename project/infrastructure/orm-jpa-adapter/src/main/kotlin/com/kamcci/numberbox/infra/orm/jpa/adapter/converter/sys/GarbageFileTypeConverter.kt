package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class GarbageFileTypeConverter : AttributeConverter<GarbageFileType, String> {
    override fun convertToEntityAttribute(column: String): GarbageFileType? {
        return GarbageFileType.entries.find { it.dbData == column }
    }

    override fun convertToDatabaseColumn(property: GarbageFileType): String {
        return property.dbData
    }
}