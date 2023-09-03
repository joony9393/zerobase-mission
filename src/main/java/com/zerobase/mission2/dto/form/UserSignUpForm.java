package com.zerobase.mission2.dto.form;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpForm {
    private String id;
    private String username;
    private String password;
    private String phone;
}
