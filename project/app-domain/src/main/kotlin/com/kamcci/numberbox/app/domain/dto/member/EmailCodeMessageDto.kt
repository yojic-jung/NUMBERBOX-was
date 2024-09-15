package com.kamcci.numberbox.app.domain.dto.member

/**
 * 이메일 검증 코드 메시지 양식
 */
data class EmailCodeMessageDto(
    val recipientEmail: String, // 수신인
    val verifyCode: String // 검증 코드
) {
    // todo 상수화
    fun getTitle() = "[N명의수학] 이메일 인증코드 안내"
    fun getContents() =
        "<div>안녕하세요. N명의수학입니다.<br/>" +
                "요청하신 회원가입 이메일 인증코드는 아래와 같습니다." +
                "</div>" +
                "<div style='margin:\"10px 0\"font-family:\"Malgun Gothic\";font-size:\"20px\"; '>" +
                verifyCode +
                "</div>위 인증코드는 3분간 유효합니다."

}