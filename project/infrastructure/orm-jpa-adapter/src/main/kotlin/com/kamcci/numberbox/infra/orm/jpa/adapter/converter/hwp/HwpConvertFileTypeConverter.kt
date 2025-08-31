package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.hwp

import com.kamcci.numberbox.app.domain.enumeration.hwp.HwpConvertFileType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class HwpConvertFileTypeConverter : AttributeConverter<HwpConvertFileType, String> {
    override fun convertToEntityAttribute(column: String?): HwpConvertFileType? {
        return HwpConvertFileType.entries.find { it.dbData == column }
    }

    override fun convertToDatabaseColumn(property: HwpConvertFileType): String {
        return property.dbData
    }
}