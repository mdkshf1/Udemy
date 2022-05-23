package com.udemy.controllers;

import com.udemy.DTOs.CourseRequestTO;
import com.udemy.DTOs.CourseResponseTO;
import com.udemy.DTOs.StudentResponseTO;
import com.udemy.services.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public ResponseEntity<?> addCourse(@Valid @RequestBody CourseRequestTO course, BindingResult result)
    {
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        try {
            return new ResponseEntity<CourseRequestTO>(courseService.addCourse(course),HttpStatus.OK);
    }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/")
    public ResponseEntity<?> allCourse(){
        return new ResponseEntity<List<CourseResponseTO>>(courseService.allCourse(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable("id")Long id){
        try {
            return new ResponseEntity<CourseResponseTO>(courseService.getCourse(id),HttpStatus.OK);
        }catch (Exception e)
        {
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudents(@PathVariable("id")Long id){
        try {
            return new ResponseEntity<List<StudentResponseTO>>(courseService.getStudents(id),HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable("id")Long id,@Valid @RequestBody CourseResponseTO response,BindingResult result){
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }try {
            return new ResponseEntity<CourseResponseTO>(courseService.updateCourse(response,id),HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable("id")Long id){
        courseService.deleteCourse(id);
        return new ResponseEntity<String>("Course Deleted Successfully",HttpStatus.OK);
    }
    @PutMapping("/subscribe")
    public ResponseEntity<?> subscribeCourse(@RequestParam(name = "courseId")Long courseId,@RequestParam(name = "studentId")Long studentId){
        try {
            courseService.subscribeCourse(courseId,studentId);
            return new ResponseEntity<String>("Thanks for subscribing our course",HttpStatus.OK);
        }catch (Exception e)
        {
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}