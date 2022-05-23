package com.udemy.controllers;

import com.udemy.DTOs.ReviewTO;
import com.udemy.DTOs.ReviewUpdateTO;
import com.udemy.services.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewTO review, BindingResult result){
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }try {
            return new ResponseEntity<ReviewTO>(reviewService.addReview(review),HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/course/{id}")
    public ResponseEntity<?> getCourseReview(@PathVariable("id")Long id){
        try
        {
            return new ResponseEntity<List<ReviewTO>>(reviewService.getCourseReview(id),HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudentReview(@PathVariable("id")Long id){
        return new ResponseEntity<List<ReviewTO>>(reviewService.getStudentReview(id),HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateReview(@RequestParam(name = "studentId")Long studentId, @RequestParam(name = "courseId")Long courseId, @RequestBody ReviewUpdateTO updateTO,BindingResult result){
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }try {
            return new ResponseEntity<ReviewTO>(reviewService.updateReview(studentId,courseId,updateTO),HttpStatus.OK);
        }catch (Exception e)
        {
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/")
    public ResponseEntity<?> deleteReview(@RequestParam(name = "studentId")Long studentId, @RequestParam(name = "courseId")Long courseId){
        reviewService.deleteReview(studentId,courseId);
        return new ResponseEntity<String>("Review Deleted Successfully",HttpStatus.OK);
    }
}
