package com.udemy.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class ReviewPK implements Serializable {
    private Long student;
    private Long course;
}
