package com.zerobase.mission2.dto;

import com.zerobase.mission2.domain.Uesr;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String username;
    private String phone;

    public static UserDto fromEntity(Uesr uesr){
        return UserDto.builder()
                .username(uesr.getUsername())
                .phone(uesr.getPhone())
                .build();
    }
}
