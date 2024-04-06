package com.numberbox.members.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
@Table(name = "members_role")
public class MembersRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seqNo;

    @JsonIgnore
    @Column(columnDefinition = "BINARY(16)", updatable = false)
    private UUID userUniqId;

    private boolean enabled;
    private String roleName;

}