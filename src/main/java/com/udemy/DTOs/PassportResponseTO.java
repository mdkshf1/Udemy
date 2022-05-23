package com.udemy.DTOs;

import com.udemy.entities.Passport;
import com.udemy.entities.Student;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.security.PublicKey;
import java.time.LocalDate;
import java.util.Date;

@Data
public class PassportResponseTO {
    private Long id;
    private String name;
    private String passportNumber;
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfIssue;
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfExpiry;

    public static PassportResponseTO mapper(Passport passport, Student student){
        PassportResponseTO response = new PassportResponseTO();
        response.setId(passport.getId());
        response.setPassportNumber(passport.getPassportNumber());
        response.setDateOfExpiry(passport.getDateOfExpiry());
        response.setDateOfIssue(passport.getDateOfIssue());
        response.setName(student.getName());
        return response;
    }
}
