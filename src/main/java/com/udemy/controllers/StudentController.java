package com.udemy.controllers;

import com.udemy.DTOs.CourseResponseTO;
import com.udemy.DTOs.StudentRequestTO;
import com.udemy.DTOs.StudentResponseTO;
import com.udemy.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("all")
    public ResponseEntity<?> allStudents(){
        return new ResponseEntity<List<StudentResponseTO>>(studentService.allStudents(),HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getStudent(@PathVariable("email")String email){
        try {
            return new ResponseEntity<StudentResponseTO>(studentService.getStudent(email),HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<?> getCourses(@PathVariable("id")Long id){
        try{
            return new ResponseEntity<List<CourseResponseTO>>(studentService.getCourse(id),HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentRequestTO student, BindingResult result){
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }try{
            return new ResponseEntity<StudentRequestTO>(studentService.createStudent(student),HttpStatus.CREATED);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id")Long id,@Valid @RequestBody StudentRequestTO request,BindingResult result){
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        try {
            return new ResponseEntity<StudentRequestTO>(studentService.updateStudent(id,request),HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id")Long id){
        try {
            studentService.deleteStudent(id);
            return new ResponseEntity<String>("Student deleted Successfully",HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
