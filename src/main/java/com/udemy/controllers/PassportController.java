package com.udemy.controllers;

import com.udemy.DTOs.PassportResponseTO;
import com.udemy.DTOs.PassportRequestTO;
import com.udemy.services.PassportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/passport")
@Slf4j
@RestController
public class PassportController {
    @Autowired
    private PassportService passportService;


    @GetMapping("/all")
    public ResponseEntity<?> allPassports(){
        return new ResponseEntity<List<PassportResponseTO>>(passportService.allPassport(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPassport(@PathVariable("id")Long id){
        try {
            return new ResponseEntity<PassportResponseTO>(passportService.getPassport(id),HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.OK);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<?> createPassport(@RequestParam(name = "id")Long id,@Valid @RequestBody PassportRequestTO passport,BindingResult result){
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }
        try {
            return new ResponseEntity<PassportResponseTO>(passportService.createPassport(passport,id),HttpStatus.CREATED);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePassport(@PathVariable("id")Long id,@Valid @RequestBody PassportRequestTO passportRequestTO,BindingResult result){
        if (result.hasErrors())
        {
            String error = result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n"));
            log.warn("Validation failed: "+error);
            return new ResponseEntity<String>(result.getAllErrors().stream().map(objectError -> {return objectError.getDefaultMessage();}).collect(Collectors.joining("\n")), HttpStatus.EXPECTATION_FAILED);
        }try {
            return new ResponseEntity<PassportResponseTO>(passportService.updatePassport(id,passportRequestTO),HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePassport(@PathVariable("id")Long id){
        try {
            passportService.deletePassport(id);
            return new ResponseEntity<String>("Passport Deleted Successfully",HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
