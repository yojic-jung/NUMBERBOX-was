package com.numberbox.members.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.uuid.Generators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class Members {
	
	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID userUniqId;
	
	@PrePersist
	public void createUserUniqId() {
		//sequential uuid 생성
		UUID uuid = Generators.timeBasedGenerator().generate();
		String[] uuidArr = uuid.toString().split("-");
		String uuidStr = uuidArr[2]+uuidArr[1]+uuidArr[0]+uuidArr[3]+uuidArr[4];
		StringBuffer sb = new StringBuffer(uuidStr);
		sb.insert(8, "-");
		sb.insert(13, "-");
		sb.insert(18, "-");
		sb.insert(23, "-");
		uuid = UUID.fromString(sb.toString());
		this.userUniqId = uuid;
	}
	
	private String email;
    private String password;
    
    private boolean humanStatus;
    private int failCount;
    @CreationTimestamp
    private LocalDateTime lastFailTime;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "userUniqId", referencedColumnName = "userUniqId")
    List<MembersRole> role;

  
    @CreationTimestamp
    private LocalDateTime signupDate;
    @UpdateTimestamp
    private LocalDateTime lastLoginDate;
}