package com.kammci.modules.logging.engine.util

import com.kamcci.modules.logging.control.service.IPAddressService

class MockIPAddressUtil : IPAddressService {
    override fun getIPAddress(): String {
        return ""
    }

    override fun getPublicIPAddress(): String {
        return ""
    }


}
