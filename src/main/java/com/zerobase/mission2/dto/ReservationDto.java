package com.zerobase.mission2.dto;

import com.zerobase.mission2.domain.Reservation;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long reservationId;
    private String userName;
    private LocalDateTime reservationTime;
    private Integer tableNum;

    public static ReservationDto fromEntity(Reservation reservation){
        return ReservationDto.builder()
                .reservationId(reservation.getId())
                .userName(reservation.getUesr().getUsername())
                .reservationTime(reservation.getReservationTime())
                .tableNum(reservation.getTableNum())
                .build();
    }

    public static List<ReservationDto> fromEntityList(List<Reservation> reservations){
        List<ReservationDto> reservationList=new ArrayList<>();
        for(Reservation reservation : reservations){
            reservationList.add(fromEntity(reservation));
        }
        return reservationList;
    }
}
