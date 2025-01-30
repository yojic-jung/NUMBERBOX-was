package com.kammci.modules.logging.engine.stub.util

import com.kamcci.modules.logging.control.service.IPAddressService

/**
 * IPAddressService 스텁
 */
class MockIPAddressUtil : IPAddressService {
    override fun getIPAddress(): String {
        return ""
    }

    override fun getPublicIPAddress(): String {
        return ""
    }


}
