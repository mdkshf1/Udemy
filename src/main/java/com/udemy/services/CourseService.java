package com.udemy.services;

import com.udemy.DTOs.CourseRequestTO;
import com.udemy.DTOs.CourseResponseTO;
import com.udemy.DTOs.StudentResponseTO;
import com.udemy.entities.Course;
import com.udemy.entities.Student;
import com.udemy.exceptions.NotFoundException;
import com.udemy.repos.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentService studentService;

    public Course checkCourse(Long id){
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()){
            log.warn("Course found with this id");
            throw new NotFoundException("Cannot found course with this id");
        }
        log.info("Got the Course with id "+id);
        return course.get();
    }

    public CourseRequestTO addCourse(CourseRequestTO request){
        Course course = CourseRequestTO.mapper(request);
        log.info("Ready to save a new  Course");
        courseRepository.save(course);
        log.info("New Course Saved");
        return request;
    }
    public List<CourseResponseTO> allCourse(){
        List<Course> courses = (List<Course>) courseRepository.findAll();
        log.info("Listing all Courses");
        return courses.stream().map(CourseResponseTO::mapper).collect(Collectors.toList());
    }
    public CourseResponseTO getCourse(Long id){
        Course course = checkCourse(id);
        log.info("Showing the course");
        return CourseResponseTO.mapper(course);
    }
    public CourseResponseTO updateCourse(CourseResponseTO response,Long id){
        Course course = checkCourse(id);
        log.info("Ready to update the course");
        if (response.getName() != null)
            course.setName(response.getName());
        if (response.getAmount() != null)
            course.setAmount(response.getAmount());
        if (response.getDescription() != null)
            course.setDescription(response.getDescription());
        if (response.getDuration() != null)
            course.setDuration(response.getDuration());
        courseRepository.save(course);
        log.info("Course updated Successfully");
        return CourseResponseTO.mapper(course);
    }
    public void deleteCourse(Long id){
        Course course = checkCourse(id);
        log.info("Ready to delete Course with id "+id);
        courseRepository.delete(course);
        log.info("Course deleted with id "+id);
    }
    public void subscribeCourse(Long courseId,Long studentId){
        Student student = studentService.getStudent(studentId);
        Course course = checkCourse(courseId);
        List<Student> students = course.getStudents();
        students.add(student);
        course.setStudents(students);
        List<Course> courses = student.getCourses();
        courses.add(course);
        student.setCourses(courses);
        log.info("Course with id "+courseId+" is subscribed by a student with id "+studentId);
        courseRepository.save(course);
    }
    public List<StudentResponseTO> getStudents(Long id){
        Course course = checkCourse(id);
        List<Student> students = course.getStudents();
        return students.stream().map(StudentResponseTO::mapper).collect(Collectors.toList());
    }
}
