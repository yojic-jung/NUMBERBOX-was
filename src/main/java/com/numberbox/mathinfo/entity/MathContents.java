package com.numberbox.mathinfo.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(name="MathContents", entities={@EntityResult(entityClass=MathContents.class)},columns={@ColumnResult(name="contentsNo")})
@Entity
@DynamicUpdate
public class MathContents {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int contentsNo;
	@Column(length = 5, nullable = false)
	int unitUniqNo;
	@Column(length = 2, nullable = false)
	int typeNo;
	
	@JsonIgnore
	@Column(columnDefinition = "BINARY(16)", nullable = false, updatable=false)
	UUID userUniqId;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	String contents;
	@Column(length = 70, nullable = true, updatable=false)
	String contentsImg;
	
	@Column(columnDefinition = "TEXT", nullable = true)
	String solution;
	@Column(length = 70, nullable = true, updatable=false)
	String solutionImg;
	
	@Column(length = 30, nullable = true, updatable=false)
	String imgPath;
	
	@Column(length = 30, nullable = true, updatable=false)
	String solutionImgPath;
	
	@Column(columnDefinition = "TEXT", nullable = true)
	String firNo;
	@Column(columnDefinition = "TEXT", nullable = true)
	String secNo;
	@Column(columnDefinition = "TEXT", nullable = true)
	String thrNo;
	@Column(columnDefinition = "TEXT", nullable = true)
	String fourNo;
	@Column(columnDefinition = "TEXT", nullable = true)
	String fifNo;
	
	@Column(length = 1, nullable = false)
	String multiChoiceType;
	
	@Column(columnDefinition = "TEXT", nullable = true)
	String answer;
	@Column(length = 9, nullable = true)
	String choiceAnswer;					//전체 체크해서 바이트 체크
	
	@Column(length =20, nullable = true)
	String orgSrcRef;
	@Column(length =4, nullable = true)
	int orgSrcNo;
	@Column(length =1, nullable = false)
	int quesLevel;
	@Column(length =1, nullable = false)
	int ansExistStts;
	@Column(length =1, nullable = false, updatable=false)
	int svcPosbStts;
	@Column(length =1, nullable = false, updatable=false)
	int contentsClassify;
	@Column(length =11, nullable = false, updatable=false)
	int orgContentsNo;
	@Column(length =4, nullable = false, updatable=false)
	int transConCnt;
	
	@Column(updatable=false)
	@CreationTimestamp
	LocalDateTime sysCreateDate;
	
	@Column
	@UpdateTimestamp
	LocalDateTime sysUpdateDate;
	
	/*		//pk로 조인 하지 않고 다른 칼럼으로 조인하는 경우 lazy가 작동하지만 사용자가 사용한 경우 아니라 의무적으로 가져옴, 원할 때만 사용 불가
	 * 		//pk 아닌 칼럼으로 연관관계시 연관관계 없애는게 나음
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userUniqId", referencedColumnName = "userUniqId", insertable=false, updatable=false)
	MembersProfile membersProfile;
	*/
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({
        @JoinColumn(name = "unitUniqNo", referencedColumnName = "unitUniqNo", insertable=false, updatable=false),
	})
	MathUnitInfo mathUnitInfo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@NotFound(action=NotFoundAction.IGNORE) 
	@JoinColumns({
        @JoinColumn(name = "unitUniqNo", referencedColumnName = "unitUniqNo", insertable=false, updatable=false),
        @JoinColumn(name = "typeNo", referencedColumnName = "typeNo", insertable=false, updatable=false)
	})
	MathTypeInfo mathTypeInfo;
	
	
	@OneToMany(mappedBy="mathContents", fetch = FetchType.LAZY)
	List<MathContentsComp> mathContentsComp;
	
	//실제 일대일이지만 일대일에서 연관관계의 주인이 외래키를 갖지만 종 테이블이 null일때 참조 불가하므로 oneToMany 종으로 설계
	@OneToMany(mappedBy="mathContents", fetch = FetchType.LAZY)		
	List<MathContentsLicense> mathContentsLicense;
}
