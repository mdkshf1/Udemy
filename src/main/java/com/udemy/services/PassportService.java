package com.udemy.services;

import com.udemy.DTOs.PassportResponseTO;
import com.udemy.DTOs.PassportRequestTO;
import com.udemy.entities.Passport;
import com.udemy.entities.Student;
import com.udemy.exceptions.AlreadyFoundException;
import com.udemy.exceptions.NotFoundException;
import com.udemy.repos.PassportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PassportService {

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private SimpleMailService mailService;

    public Passport checkPassport(Long id){
        Optional<Passport> passport = passportRepository.findById(id);
        if (passport.isEmpty()){
            log.warn("Passport with id "+id+" cannot be found");
            throw new NotFoundException("Passport with this id cannot be found");
        }
        log.info("Got the passport with id "+id);
        return passport.get();
    }

    private void checkPassportNumber(String passportNumber){
        Passport passport = passportRepository.findPassportByPassportNumber(passportNumber);
        if (passport != null){
            log.error("Passport number already present");
            throw new AlreadyFoundException("Passport with this number is already present/your passport details are already present");
        }
    }

    public PassportResponseTO createPassport(PassportRequestTO request, Long id){
        checkPassportNumber(request.getPassportNumber());
        Student student = studentService.getStudent(id);
        if (student.getPassport() != null)
            throw new AlreadyFoundException("Your passport is already present");
        Passport passport = Passport.mapper(request,student);
        log.info("Ready to save passport for a student with id "+id);
        passportRepository.save(passport);
        log.info("Passport saved Successfully");
        return PassportResponseTO.mapper(passport,student);
    }

    public PassportResponseTO getPassport(Long id){
        Passport passport = checkPassport(id);
        return PassportResponseTO.mapper(passport,passport.getStudent());
    }
    public List<PassportResponseTO> allPassport(){
        List<Passport> passport = (List<Passport>) passportRepository.findAll();
        log.info("Showing the list of All passport presented in record");
        return passport.stream().map(passport1 -> PassportResponseTO.mapper(passport1,passport1.getStudent())).collect(Collectors.toList());
    }
    public PassportResponseTO updatePassport(Long id,PassportRequestTO request){
        Passport passport = checkPassport(id);
        log.info("Ready to update a passport Details");
        if (request.getPassportNumber() != null)
            passport.setPassportNumber(request.getPassportNumber());
        if (request.getDateOfIssue() != null)
            passport.setDateOfIssue(request.getDateOfIssue());
        if (request.getDateOfExpiry() != null)
            passport.setDateOfExpiry(request.getDateOfExpiry());
        passportRepository.save(passport);
        log.info("Passport with updated details saved successfully");
        return PassportResponseTO.mapper(passport,passport.getStudent());
    }
    public void deletePassport(Long id){
        log.warn("Deleting a passport with id "+id);
        Passport passport = checkPassport(id);
        passportRepository.delete(passport);
        log.info("Passport deleted Successfully with id "+id);
    }

    @Scheduled(initialDelay = 1000L,fixedDelayString = "PT1H")
    void invalidPassport() throws InterruptedException{
        List<Passport> passports = (List<Passport>) passportRepository.findAll();
        passports.stream().map(passport -> {
            if (passport.getDateOfExpiry().getYear() - new Date().getYear() == 0){
                mailService.sendMail(passport.getStudent().getEmail(),"Your update your passport","Your passport date has been finished please update your passport details");
            }
            return 0;
        }).collect(Collectors.toList());
    }
}
