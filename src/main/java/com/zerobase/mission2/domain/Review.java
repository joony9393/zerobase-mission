package com.zerobase.mission2.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Uesr uesr;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private double rating;
    private String comment;
}
