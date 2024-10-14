package com.kamcci.numberbox.infra.orm.converter.math

import com.kamcci.numberbox.app.domain.enumeration.math.IpsiManageInsType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class IpsiManageInsTypeConverter : AttributeConverter<IpsiManageInsType, Int> {
    override fun convertToEntityAttribute(column: Int): IpsiManageInsType? {
        return IpsiManageInsType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: IpsiManageInsType): Int {
        return property.id
    }
}