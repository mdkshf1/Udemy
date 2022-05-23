package com.udemy.DTOs;

import com.udemy.entities.Review;
import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ReviewTO {
    private Long courseId;
    private Long StudentId;
    @Min(value = 0,message = "You cannot give rating below than 0")
    @Max(value = 5,message = "You cannot give rating more than 5")
    private Integer rating;
    private String comment;
    public static ReviewTO mapper(Review review){
        ReviewTO reviewTO = new ReviewTO();
        reviewTO.setComment(review.getComment());
        reviewTO.setRating(review.getRating());
        reviewTO.setCourseId(review.getCourse().getId());
        reviewTO.setStudentId(review.getStudent().getId());
        return reviewTO;
    }
}
