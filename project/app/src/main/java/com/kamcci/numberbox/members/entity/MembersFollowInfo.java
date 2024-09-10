package com.kamcci.numberbox.members.entity;

import com.kamcci.numberbox.members.domain.FollowUsersDomain;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MembersFollowInfo {

    @EmbeddedId
    public FollowUsersDomain followUsers;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime sysCreateDate;

}
