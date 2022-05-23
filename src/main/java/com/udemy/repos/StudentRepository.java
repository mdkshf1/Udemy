package com.udemy.repos;

import com.udemy.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student,Long> {
    Student findByEmail(String email);
}
