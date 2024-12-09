package com.kamcci.numberbox.infra.orm.jpa.adapter.converter.docs

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class ContentsIdListConverter : AttributeConverter<MutableList<Long>, String> {
    override fun convertToEntityAttribute(column: String?): MutableList<Long> {
        return if (column == null) mutableListOf()
        else column.split(",").map { it.toLong() }.toMutableList()
    }

    override fun convertToDatabaseColumn(property: MutableList<Long>): String {
        return property.joinToString(",")
    }
}