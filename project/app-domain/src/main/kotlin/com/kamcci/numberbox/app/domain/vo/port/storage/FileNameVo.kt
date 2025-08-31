package com.kamcci.numberbox.app.domain.vo.port.storage

/**
 * 파일 경로 및 이름
 */
data class FileNameVo(val name: String, val path: String) {
    fun getFileFullName() = "${path}/${name}"
}