package com.udemy.entities;

import com.udemy.DTOs.PassportRequestTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;


@Data
@Slf4j
@Entity
public class Passport extends AuditingInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "^[A-Z]{1}-[0-9]{7}$")
    @NotNull(message = "Passport number cannot be null")
    @NotBlank(message = "Passport number cannot be left blank")
    @Column(nullable = false,unique = true)
    private String passportNumber;
    @DateTimeFormat(pattern = "dd-mm-yyyy")
/*    @NotNull(message = "Date of issue cannot be null")
    @NotBlank(message = "Date of issue cannot be left blank")*/
    @Column(nullable = false)
    private LocalDate dateOfIssue;
    @DateTimeFormat(pattern = "dd-mm-yyyy")
/*    @NotNull(message = "Date of Expiry cannot be null")
    @NotBlank(message = "Date of Expiry cannot be left blank")*/
    @Column(nullable = false)
    private LocalDate dateOfExpiry;
    @OneToOne(mappedBy = "passport",cascade = CascadeType.PERSIST)
    private Student student;

    public static Passport mapper(PassportRequestTO request,Student student){
        Passport passport = new Passport();
        passport.setStudent(student);
        passport.setPassportNumber(request.getPassportNumber());
        passport.setDateOfIssue(request.getDateOfIssue());
        passport.setDateOfExpiry(request.getDateOfExpiry());
        student.setPassport(passport);
        return passport;
    }
}
