package com.zerobase.mission2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Uesr {
    @Id
    private String id;
    private String username;
    private String password;
    private String phone;
    private LocalDateTime recentLogin;
}
