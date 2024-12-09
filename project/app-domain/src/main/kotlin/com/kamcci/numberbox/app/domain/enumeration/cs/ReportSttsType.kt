package com.kamcci.numberbox.app.domain.enumeration.cs

enum class ReportSttsType(val dbData: Int, val desc: String) {
    Submit(0, "접수"),
    Reply(1, "답변 완료")
}