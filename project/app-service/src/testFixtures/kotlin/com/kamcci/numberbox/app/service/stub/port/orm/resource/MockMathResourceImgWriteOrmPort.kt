package com.kamcci.numberbox.app.service.stub.port.orm.resource

import com.kamcci.numberbox.app.port.orm.resource.MathResourceImgWriteOrmPort
import com.kamcci.numberbox.app.service.constant.MockTestConstant.FAIL_ID

class MockMathResourceImgWriteOrmPort : MathResourceImgWriteOrmPort {
    override fun deleteByResourceId(resourceId: Long): Long {
        return if (resourceId == FAIL_ID) 0L else 1L
    }
}