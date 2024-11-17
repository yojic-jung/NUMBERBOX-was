package com.kamcci.numberbox.members.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmailIdCode {
    @Id
    private String email;

    private String idCode;

    @UpdateTimestamp
    private LocalDateTime sysCreateTime;
}
