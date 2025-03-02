package com.kamcci.numberbox.infra.orm.jpa.adapter.util

import org.assertj.core.api.Assertions.assertThat

object CustomAssertUtil {

    // assert db data to enum class type
    fun <T : Enum<T>> assertDataToEnumMapping(enumClass: Class<T>, action: (T?) -> T?) {
        enumClass.enumConstants.forEach { dbData ->
            // when
            val type = action(dbData)

            // then
            assertThat(type).isEqualTo(dbData)
        }

        // null 변환 검증
        assertNullToNullMapping(action)
    }

    // assert null to null convert
    private fun <T : Enum<T>> assertNullToNullMapping(action: (T?) -> T?) {
        // given
        val dbData = null

        // when
        val type = action(dbData)

        // then - null 체크
        assertThat(type).isNull()
    }
}