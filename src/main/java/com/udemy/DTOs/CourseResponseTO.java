package com.udemy.DTOs;

import com.udemy.entities.Course;
import lombok.Data;

@Data
public class CourseResponseTO {
    private String name;
    private String amount;
    private String description;
    private Integer duration;
    public static CourseResponseTO mapper(Course course){
        CourseResponseTO response = new CourseResponseTO();
        response.setName(course.getName());
        response.setAmount(course.getAmount());
        response.setDescription(course.getDescription());
        response.setDuration(course.getDuration());
        return response;
    }
}
