package com.zerobase.mission2.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailDto {
    private StoreDto store;
    private List<ReservationDto> reservations;
    private List<ReviewDto> reviews;
}
