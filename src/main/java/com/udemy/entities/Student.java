package com.udemy.entities;

import com.udemy.DTOs.StudentRequestTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Slf4j
@Entity
public class Student extends AuditingInfo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3,message = "Name should be valid ad at least three characters long ")
    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name cannot be null")
    @Column(nullable = false)
    private String name;
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",message = "Email should be in correct format example 'abc@gmail.com'")
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email cannot be null")
    @Column(nullable = false,unique = true)
    private String email;
    @Size(min = 6,message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    private String password;
    @OneToOne(orphanRemoval = true,cascade = CascadeType.ALL)
    private Passport passport;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Course> courses;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "student")
    private List<Review> reviews;

    public static Student createStudent(StudentRequestTO studentRequestTO){
        Student student = new Student();
        student.setName(studentRequestTO.getName());
        student.setEmail(studentRequestTO.getEmail());
        student.setPassword(studentRequestTO.getPassword());
        return student;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
