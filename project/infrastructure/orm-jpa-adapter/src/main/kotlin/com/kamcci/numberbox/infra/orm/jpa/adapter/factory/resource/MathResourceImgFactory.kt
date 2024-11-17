package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource

import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceImgEntity

object MathResourceImgFactory {
    fun getSaveEntity(
        resourceEntity: MathResourceEntity,
        imgList: List<FileNameVo>
    ): MutableList<MathResourceImgEntity> {
        val saveEntityList: MutableList<MathResourceImgEntity> = mutableListOf()
        for (img in imgList) {
            saveEntityList.add(
                MathResourceImgEntity()
                    .apply {
                        mathResource = resourceEntity
                        imgPath = img.path
                        imgName = img.name
                    }
            )
        }
        return saveEntityList
    }

    fun getUpdateEntity(
        originEntity: MathResourceEntity,
        imgList: List<FileNameVo>
    ): MutableList<MathResourceImgEntity> {
        val updateEntityList: MutableList<MathResourceImgEntity> = mutableListOf()
        for (img in imgList) {
            updateEntityList.add(
                MathResourceImgEntity()
                    .apply {
                        mathResource = originEntity
                        imgPath = img.path
                        imgName = img.name
                    }
            )
        }
        return updateEntityList
    }

}