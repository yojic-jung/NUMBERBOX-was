package com.kamcci.modules.logging.control.service

interface IPAddressService {

    // 클라이언트 ip 추출
    fun getIPAddress(): String

    // 클라이언트 public ip 추출
    fun getPublicIPAddress(): String
}