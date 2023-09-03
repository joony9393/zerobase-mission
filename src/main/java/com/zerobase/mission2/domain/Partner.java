package com.zerobase.mission2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Partner {
    @Id
    private String id;
    private String username;
    private String password;
    private String phone;
}
