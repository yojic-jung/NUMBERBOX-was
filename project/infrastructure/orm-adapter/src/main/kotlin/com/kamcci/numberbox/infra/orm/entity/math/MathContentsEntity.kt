package com.kamcci.numberbox.infra.orm.entity.math

import com.kamcci.numberbox.app.domain.enumeration.math.ContentsClassifyType
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.*

/**
 * 수학문제
 */
@Entity
@Table(name = "math_contents")
class MathContentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var id: Long = 0

    @Column(name = "unit_id", length = 5, nullable = false)
    var unitId: Int? = null

    @Column(name = "type_id", length = 5, nullable = false)
    var typeId: Int? = null

    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    var memberId: UUID? = null

    // 문제 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    var contents: String? = null

    // 컨텐츠 이미지(신규 문제는 사용 안함)
    @Column(length = 70, nullable = true, updatable = false)
    var contentsImg: String? = null

    // 해설 내용
    @Column(columnDefinition = "TEXT", nullable = true)
    var solution: String? = null

    // 해설 이미지(신규 문제는 사용 안함)
    @Column(length = 70, nullable = true, updatable = false)
    var solutionImg: String? = null

    // 문제 이미지 경로(신규 문제는 사용 안함)
    @Column(length = 30, nullable = true, updatable = false)
    var imgPath: String? = null

    // 해설 이미지 경로(신규 문제는 사용 안함)
    @Column(length = 30, nullable = true, updatable = false)
    var solutionImgPath: String? = null

    // 객관식 1번
    @Column(columnDefinition = "TEXT", nullable = true)
    var firNo: String? = null

    // 객관식 2번
    @Column(columnDefinition = "TEXT", nullable = true)
    var secNo: String? = null

    // 객관식 3번
    @Column(columnDefinition = "TEXT", nullable = true)
    var thrNo: String? = null

    // 객관식 4번
    @Column(columnDefinition = "TEXT", nullable = true)
    var fourNo: String? = null

    // 객관식 5번
    @Column(columnDefinition = "TEXT", nullable = true)
    var fifNo: String? = null

    // 객관식/주관식 여부
    @Column(length = 1, nullable = false)
    var multiChoiceType: MultiChoiceType? = null

    // 주관식 정답
    @Column(columnDefinition = "TEXT", nullable = true)
    var answer: String? = null

    // 객관식 정답
    @Column(length = 9, nullable = true)
    var choiceAnswer: String? = null // 전체 체크해서 바이트 체크

    // 출처 - 교재
    @Column(length = 20, nullable = true)
    var orgSrcRef: String? = null

    // 출저 - 문제 번호
    @Column(length = 4, nullable = true)
    var orgSrcNo: Int = 0

    // 난이도
    @Column(length = 1, nullable = false)
    var quesLevel: Int? = null

    // 정답 존재 여부
    @Column(length = 1, nullable = false)
    var ansExistStts: Boolean? = null

    // 서비스 가능 여부
    @Column(length = 1, nullable = false, updatable = false)
    var svcPosbStts: ContentsSvcPosbSttsType = ContentsSvcPosbSttsType.NotRelease

    // 수학 문제 구분
    @Column(length = 1, nullable = false, updatable = false)
    var contentsClassify: ContentsClassifyType? = null

    /**
     * 원본 문제 번호
     *
     * - 변형 문제의 경우 원본 문제 번호를 갖음
     * - 변형 문제 아닌 경우 0
     */
    @Column(length = 11, nullable = false, updatable = false)
    var orgContentsId: Long = 0

    // 변형 문제 갯수
    @Column(length = 4, nullable = false, updatable = false)
    var transConCnt: Int = 0

    @Column(updatable = false)
    @CreationTimestamp
    var sysCreateDate: LocalDateTime? = null

    @Column
    @UpdateTimestamp
    var sysUpdateDate: LocalDateTime? = null

    // 단원 정보
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id", insertable = false, updatable = false)
    var mathUnitInfo: MathCategoryUnitEntity? = null

    // 유형 정보
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns(
        JoinColumn(name = "unit_id", insertable = false, updatable = false),
        JoinColumn(name = "type_id", insertable = false, updatable = false)
    )
    var mathTypeInfo: MathCategoryTypeEntity? = null

    // 자체제작 수학문제 출처
    @OneToMany(mappedBy = "mathContents", fetch = FetchType.LAZY)
    var mathContentsSimilarSrc: MutableList<MathContentsSimilarSrcEntity> = mutableListOf()

    // 라이센스 정보
    @OneToMany(mappedBy = "mathContents", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST])
    var mathContentsLicenses: MutableList<MathContentsLicenseEntity> = mutableListOf()

    // 입시 수학 문제 출처
    @OneToMany(mappedBy = "mathContents", fetch = FetchType.LAZY)
    var mathContentsIpsiSrc: MutableList<MathContentsIpsiSrcEntity> = mutableListOf()
}