package com.udemy.DTOs;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ReviewUpdateTO {
    private String comment;
    @Min(value = 0,message = "You cannot give rating below than 0")
    @Max(value = 5,message = "You cannot give rating more than 5")
    private Integer rating;
}
