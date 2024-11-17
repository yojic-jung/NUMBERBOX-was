package com.kamcci.numberbox.app.domain.enumeration.sys

/**
 * 삭제 대상 유휴 파일 저장소
 */
enum class GarbageFileType(val dbData: String, val desc: String) {
    S3("S3", "aws s3")
}