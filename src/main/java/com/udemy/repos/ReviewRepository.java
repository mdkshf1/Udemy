package com.udemy.repos;

import com.udemy.entities.Course;
import com.udemy.entities.Review;
import com.udemy.entities.ReviewPK;
import com.udemy.entities.Student;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, ReviewPK> {
    Review findReviewByStudentAndCourse(Student student, Course course);
}
