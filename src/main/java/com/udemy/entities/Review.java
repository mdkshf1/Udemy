package com.udemy.entities;

import com.udemy.DTOs.ReviewTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
@Slf4j
@Entity
@IdClass(ReviewPK.class)
public class Review extends AuditingInfo implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Student student;
    @Id
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private Course course;
    @Min(value = 0,message = "You cannot give rating below than 0")
    @Max(value = 5,message = "You cannot give rating more than 5")
    private Integer rating;
    private String comment;
    public static Review mapper(ReviewTO reviewTO,Course course,Student student){
        Review review = new Review();
        review.setComment(reviewTO.getComment());
        review.setRating(reviewTO.getRating());
        System.out.println(course);
        review.setCourse(course);
        System.out.println(student);
        review.setStudent(student);
        return review;
    }

    @Override
    public String toString() {
        return "Review{" +
                "rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
