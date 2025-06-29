package com.kamcci.numberbox.app.domain.vo.math

import com.kamcci.numberbox.app.domain.enumeration.math.*
import java.time.LocalDateTime
import java.util.*

/**
 *  수학 문제 - 라이선스 정보 포함
 */
data class MathIpsiContentsVo(
    // 문제 id
    val contentsId: Long,
    // 문제 제작자
    val memberId: UUID,
    // 단원 id
    val unitId: Int,
    // 유형 id
    val typeId: Int,
    // 문제 내용
    val contents: String,
    // 문제 이미지
    val contentsImg: String?,
    // 해설 내용
    val solution: String?,
    // 해설 이미지
    val solutionImg: String?,
    // 이미지 경로
    val imgPath: String?,
    // 해설 이미지 경로
    val solutionImgPath: String?,
    // 객관식 1번
    val firNo: String?,
    // 객관식 2번
    val secNo: String?,
    // 객관식 3번
    val thrNo: String?,
    // 객관식 4번
    val fourNo: String?,
    // 객관식 5번
    val fifNo: String?,
    // 객관식/주관식 여부
    val multiChoiceType: MultiChoiceType,
    // 주관식 정답
    val answer: String?,
    // 객관식 정답
    val choiceAnswer: String?,
    // 문제 난이도
    val quesLevel: Int,
    // 정답 존재 여부
    val ansExistStts: Boolean,
    // 서비스 가능 여부
    val svcPosbStts: ContentsSvcPosbSttsType,
    // 문제 구분
    val contentsClassify: ContentsClassifyType,
    // 원본 문제 번호
    val orgContentsId: Long?,
    // 변형 문제 수
    val transConCnt: Int?,
    // 문제 생성 시간
    val sysCreateDate: LocalDateTime,
    // 문제 수정 시간
    val sysUpdateDate: LocalDateTime,
    // 가/나형 구분 : 1 (통합) 2 (가) 3 (나)
    val paperType: IpsiPaperType?,
    // 홀수형 번호
    val oddQuesNum: Int,
    // 짝수형 번호
    val evenQuesNum: Int?,
    // 오답률
    val wrongRatio: Int,
    // 출제 연도
    val impYear: Int,
    // 출제 월
    val impMonth: Int,
    // 출제 기관
    val manageIns: IpsiManageInsType,
    // 입시 수학문제 id
    val ipsiSrcId: Long,
    // 프로필 id
    val profileId: Long,
    // 닉네임
    val nickname: String,
    // 프로필 이미지 파일 이름
    val profileImgName: String?,
    // 프로필 이미지 파일 경로
    val profileImgPath: String?,
    // 문제 학년
    val subject: String,
    // 대단원
    val firUnit: String,
    // 중단원
    val secUnit: String,
    // 소단원
    val thrUnit: String,
) {
    var likeCount: Long = 0
}