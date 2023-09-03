package com.zerobase.mission2.dto;

import com.zerobase.mission2.domain.Partner;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDto {
    private String id;
    private String username;
    private String phone;

    public static PartnerDto fromEntity(Partner partner){
        return PartnerDto.builder()
                .id(partner.getId())
                .username(partner.getUsername())
                .phone(partner.getPhone())
                .build();
    }
}
