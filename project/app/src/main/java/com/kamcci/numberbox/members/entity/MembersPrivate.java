package com.kamcci.numberbox.members.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members_private")
public class MembersPrivate {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    @JsonIgnore
    private UUID userUniqId;

    private String userName;
    private String phoneNumber;
    private String birth;
}
