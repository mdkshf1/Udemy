package com.udemy.repos;

import com.udemy.entities.Passport;
import org.springframework.data.repository.CrudRepository;

public interface PassportRepository extends CrudRepository<Passport,Long> {
    Passport findPassportByPassportNumber(String passportNumber);
}
