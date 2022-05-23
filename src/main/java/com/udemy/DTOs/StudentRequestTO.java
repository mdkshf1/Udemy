package com.udemy.DTOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.udemy.entities.Student;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Data
@Slf4j
public class StudentRequestTO {
    @Size(min = 3,message = "Name should be valid ad at least three characters long ")
    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")
    @Column(nullable = false)
    private String name;
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",message = "Email should be in correct format example 'abc@gmail.com'")
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email cannot be null")
    @Column(nullable = false)
    private String email;
    @Size(min = 6,message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    private String password;

    public static StudentRequestTO mapper(Student student){
        StudentRequestTO request = new StudentRequestTO();
        request.setEmail(student.getEmail());
        request.setName(student.getName());
        request.setPassword(student.getPassword());
        return request;
    }
}
