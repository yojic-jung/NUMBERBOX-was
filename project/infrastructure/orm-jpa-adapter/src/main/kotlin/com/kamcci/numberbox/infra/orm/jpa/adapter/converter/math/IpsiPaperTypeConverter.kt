package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.IpsiPaperType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class IpsiPaperTypeConverter : AttributeConverter<IpsiPaperType, Int> {
    override fun convertToEntityAttribute(column: Int): IpsiPaperType? {
        return IpsiPaperType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: IpsiPaperType): Int {
        return property.id
    }
}