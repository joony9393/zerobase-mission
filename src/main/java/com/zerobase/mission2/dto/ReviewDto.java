package com.zerobase.mission2.dto;

import com.zerobase.mission2.domain.Review;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private String userName;
    private String storeName;
    private double rating;
    private String comment;

    public static ReviewDto fromEntity(Review review){
        return ReviewDto.builder()
                .userName(review.getUesr().getUsername())
                .storeName(review.getStore().getName())
                .rating(review.getRating())
                .comment(review.getComment())
                .build();
    }

    public static List<ReviewDto> fromEntityList(List<Review> reviews) {
        List<ReviewDto> reviewList = new ArrayList<>();
        for(Review review : reviews){
            reviewList.add(fromEntity(review));
        }
        return reviewList;
    }
}
