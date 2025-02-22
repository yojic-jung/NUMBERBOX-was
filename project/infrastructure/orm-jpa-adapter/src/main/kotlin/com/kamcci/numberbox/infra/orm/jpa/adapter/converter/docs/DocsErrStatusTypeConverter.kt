package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.docs

import com.kamcci.numberbox.app.domain.enumeration.docs.DocsStatusType
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class DocsErrStatusTypeConverter : AttributeConverter<DocsStatusType, Int> {
    // LeftJoin으로 null 반환 가능
    override fun convertToEntityAttribute(column: Int?): DocsStatusType? {
        return DocsStatusType.entries.find { it.dbData == column }
    }

    override fun convertToDatabaseColumn(property: DocsStatusType): Int {
        return property.dbData
    }
}