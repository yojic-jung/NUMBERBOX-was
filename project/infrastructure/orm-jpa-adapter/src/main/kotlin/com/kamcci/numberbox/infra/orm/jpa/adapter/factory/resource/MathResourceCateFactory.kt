package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource

import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceCateEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceEntity

object MathResourceCateFactory {
    fun getSaveEntity(resourceEntity: MathResourceEntity, cateList: List<String>): MutableList<MathResourceCateEntity> {
        val saveEntityList: MutableList<MathResourceCateEntity> = mutableListOf()
        for (cate in cateList) {
            val cateArr = cate.split("-")
            saveEntityList.add(
                MathResourceCateEntity()
                    .apply {
                        mathResource = resourceEntity
                        mainCateId = cateArr[0].toInt()
                        midCateId = cateArr[1].toInt()
                    }
            )
        }
        return saveEntityList
    }

    fun getUpdateEntity(originEntity: MathResourceEntity, cateList: List<String>): MutableList<MathResourceCateEntity> {
        val updateEntityList: MutableList<MathResourceCateEntity> = mutableListOf()
        for (cate in cateList) {
            val cateArr = cate.split("-")
            updateEntityList.add(
                MathResourceCateEntity()
                    .apply {
                        mathResource = originEntity
                        mainCateId = cateArr[0].toInt()
                        midCateId = cateArr[1].toInt()
                    }
            )
        }
        return updateEntityList
    }

}