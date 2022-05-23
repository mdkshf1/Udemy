package com.udemy.services;

import com.udemy.DTOs.CourseResponseTO;
import com.udemy.DTOs.StudentRequestTO;
import com.udemy.DTOs.StudentResponseTO;
import com.udemy.entities.Course;
import com.udemy.entities.Student;
import com.udemy.exceptions.AlreadyFoundException;
import com.udemy.exceptions.NotFoundException;
import com.udemy.repos.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    private void checkStudent(String email){
        Student student = studentRepository.findByEmail(email);
        if (student != null){
            log.error("Student found with email "+email);
            throw new AlreadyFoundException("Student already found");
        }
    }
    public Student findStudent(String email){
        Student student = studentRepository.findByEmail(email);
        if (student == null){
            log.error("Student not found with id "+email);
            throw new NotFoundException("Student not found");
        }
        return student;
    }
    public Student getStudent(Long id){
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()){
            log.error("Student not found with id "+id);
            throw new NotFoundException("Student not found");
        }
        log.info("Got the student with id "+id);
        return student.get();
    }
    public StudentRequestTO createStudent(StudentRequestTO studentRequestTO)
    {
        log.info("Creating a Student");
        checkStudent(studentRequestTO.getEmail());
        Student student = Student.createStudent(studentRequestTO);
        log.info("Saving a student");
        studentRepository.save(student);
        log.info("Student saved with name "+studentRequestTO.getName());
        return studentRequestTO;
    }

    public List<StudentResponseTO> allStudents(){
        log.info("Getting list of all students");
        List<Student> students = (List<Student>) studentRepository.findAll();
        return students.stream().map(StudentResponseTO::mapper).collect(Collectors.toList());
    }
    public StudentResponseTO getStudent(String email){
        log.info("Getting a student with email");
        Student student = findStudent(email);
        log.info("Got the student with email "+email);
        return StudentResponseTO.mapper(student);
    }

    public StudentRequestTO updateStudent(Long id,StudentRequestTO request){
        log.info("Updating details of a student");
        Student student = getStudent(id);
        if (request.getName() != null)
            student.setName(request.getName());
        if (request.getEmail() != null)
            student.setEmail(request.getEmail());
        if (request.getPassword() != null)
            student.setPassword(request.getPassword());
        log.info("Student updated");
        studentRepository.save(student);
        log.info("Saving updated student");
        return StudentRequestTO.mapper(student);
    }
    public void deleteStudent(Long id){
        log.info("Deleting a Student with id "+id);
        Student student = getStudent(id);
        studentRepository.delete(student);
        log.info("Student deleted Successfully");
    }
    public List<CourseResponseTO> getCourse(Long id){
        Student student = getStudent(id);
        List<Course> courses = student.getCourses();
        return courses.stream().map(CourseResponseTO::mapper).collect(Collectors.toList());
    }
}
