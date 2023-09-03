package com.zerobase.mission2.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String location;
    private String description;
    private Integer tableNum;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;
}
