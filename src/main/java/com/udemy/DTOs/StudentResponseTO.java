package com.udemy.DTOs;

import com.udemy.entities.Student;
import lombok.Data;

@Data
public class StudentResponseTO {
    private String name;
    private String email;
    public static StudentResponseTO mapper(Student student){
        StudentResponseTO request = new StudentResponseTO();
        request.setEmail(student.getEmail());
        request.setName(student.getName());
        return request;
    }
}
