package com.kamcci.numberbox.app.service.stub.port.orm.resource

import com.kamcci.numberbox.app.port.orm.resource.MathResourceCateWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID

class MockMathResourceCateWriteOrmPort : MathResourceCateWriteOrmPort {
    override fun deleteByResourceId(resourceId: Long): Long {
        return if (resourceId == FAIL_ID) 0L else 1L
    }
}