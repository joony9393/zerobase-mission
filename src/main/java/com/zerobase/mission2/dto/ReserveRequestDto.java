package com.zerobase.mission2.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReserveRequestDto {
    private Long storeId;
    private LocalDateTime time;
    private String userName;
    private Integer tableNum;
}
