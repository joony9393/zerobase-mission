package com.zerobase.mission2.dto.form;

import com.zerobase.mission2.dto.PartnerDto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerSignUpForm {
    private String password;
    private PartnerDto partner;
}
