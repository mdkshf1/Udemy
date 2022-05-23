package com.udemy.services;

import com.udemy.DTOs.ReviewTO;
import com.udemy.DTOs.ReviewUpdateTO;
import com.udemy.entities.Course;
import com.udemy.entities.Review;
import com.udemy.entities.Student;
import com.udemy.exceptions.AlreadyFoundException;
import com.udemy.exceptions.NotFoundException;
import com.udemy.repos.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;

    private void checkReview(Long studentId,Long courseId){

        Student student = studentService.getStudent(studentId);
        log.info("Got the Student");
        Course course = courseService.checkCourse(courseId);log.info("Got the Course");
        Review review = reviewRepository.findReviewByStudentAndCourse(student,course);
        if (review != null){
            log.error("Review already found");
            throw new AlreadyFoundException("Review already found for this user");
        }
    }

    public ReviewTO addReview(ReviewTO review){
        checkReview(review.getStudentId(),review.getCourseId());
        log.info("Adding a new Review");
        Student student = studentService.getStudent(review.getStudentId());
        log.info("Got the Student");
        Course course = courseService.checkCourse(review.getCourseId());
        log.info("Got the Course");
        Review review1 = Review.mapper(review,course,student);
        System.out.println(review1);
        reviewRepository.save(review1);
        log.info("Review Saved Successfully");
        return review;
    }
    public List<ReviewTO> getCourseReview(Long id){
        log.info("Getting all the Reviews of a particular Course");
        Course course = courseService.checkCourse(id);
        log.info("Got the course");
        List<Review> reviews = course.getReviews();
        log.info("Showing all the review for a course");
        return reviews.stream().map(ReviewTO::mapper).collect(Collectors.toList());
    }
    public List<ReviewTO> getStudentReview(Long id){
        log.info("Getting all the reviews of a particular Student");
        Student student = studentService.getStudent(id);
        log.info("Got the Student");
        List<Review> reviews = student.getReviews();
        log.info("Showing all the Reviews of a student");
        return reviews.stream().map(ReviewTO::mapper).collect(Collectors.toList());
    }
    public ReviewTO updateReview(Long studentId, Long courseId, ReviewUpdateTO reviewUpdateTO){
        log.info("Updating a review");
        Review review = findReview(studentId,courseId);
        log.info("Got the Review");
        if (reviewUpdateTO.getComment() != null)
            review.setComment(reviewUpdateTO.getComment());
        if (reviewUpdateTO.getRating() != null)
            review.setRating(reviewUpdateTO.getRating());
        reviewRepository.save(review);
        log.info("Review updated");
        return ReviewTO.mapper(review);
    }
    public void deleteReview(Long studentId,Long courseId){
        log.warn("Deleting a review");
        Review review = findReview(studentId,courseId);
        log.info("Got the review");
        reviewRepository.delete(review);
        log.info("Review Deleted");
    }

    private Review findReview(Long studentId,Long courseId){

        Student student = studentService.getStudent(studentId);
        log.info("Got the Student");
        Course course = courseService.checkCourse(courseId);log.info("Got the Course");
        Review review = reviewRepository.findReviewByStudentAndCourse(student,course);
        if (review == null)
        {
            log.error("No review found of the student in this course");
            throw new NotFoundException("No Review found");
        }
        return review;
    }
}
