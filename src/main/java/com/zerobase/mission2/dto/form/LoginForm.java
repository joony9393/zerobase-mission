package com.zerobase.mission2.dto.form;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {
    String id;
    LocalDateTime loginTime;
}
