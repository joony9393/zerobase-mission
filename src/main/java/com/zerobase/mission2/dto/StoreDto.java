package com.zerobase.mission2.dto;

import com.zerobase.mission2.domain.Store;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private Long id;
    private String name;
    private String location;
    private String description;
    private String partnerId;
    private String partnerName;
    private Integer tableNum;

    public static StoreDto fromEntity(Store store){
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
                .partnerId(store.getPartner().getId())
                .partnerName(store.getPartner().getUsername())
                .tableNum(store.getTableNum())
                .build();
    }

    public static List<StoreDto> fromEntityList(List<Store> stores){
        List<StoreDto> storeDtos=new ArrayList<>();
        for(Store store : stores){
            storeDtos.add(StoreDto.fromEntity(store));
        }
        return storeDtos;
    }
}
