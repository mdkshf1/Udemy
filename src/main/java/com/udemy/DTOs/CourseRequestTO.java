package com.udemy.DTOs;

import com.udemy.entities.Course;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CourseRequestTO {
    @Size(min = 5,max = 20,message = "Name of course should be minimum 5 characters and maximum 20 characters long")
    @NotBlank(message = "Name of Course cannot be Blank")
    @NotNull(message = "Name of Course cannot be Null")
    @Column(nullable = false)
    private String name;
    @NotBlank(message = "Amount of Course cannot be Blank")
    @NotNull(message = "Amount of Course cannot be Null")
    @Column(nullable = false)
    private String amount;
    @NotBlank(message = "Description of Course cannot be Blank")
    @NotNull(message = "Description of Course cannot be Null")
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer duration;

    public static Course mapper(CourseRequestTO request){
        Course course = new Course();
        course.setName(request.getName());
        course.setAmount(request.getAmount());
        course.setDescription(request.getDescription());
        course.setDuration(request.getDuration());
        return course;
    }
}
