package com.kamcci.numberbox.app.domain.vo.port.storage

/**
 * 업로드한 ppt파일명 vo
 */
data class PptFileNameVo(
    val pptFilePath: String,
    val pptFileName: String,
    val slideImgFileNameVo: List<FileNameVo>
)