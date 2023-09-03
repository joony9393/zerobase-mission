package com.zerobase.mission2.dto.form;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInForm {
    String id;
    String password;
}
