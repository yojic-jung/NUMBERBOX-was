package com.kamcci.numberbox.infra.orm.converter.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsErrStatusType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class DocsErrStatusTypeConverter : AttributeConverter<DocsErrStatusType, Int> {
    // LeftJoin으로 null 반환 가능
    override fun convertToEntityAttribute(column: Int): DocsErrStatusType? {
        return DocsErrStatusType.entries.find { it.id == column }
    }

    override fun convertToDatabaseColumn(property: DocsErrStatusType): Int {
        return property.id
    }
}