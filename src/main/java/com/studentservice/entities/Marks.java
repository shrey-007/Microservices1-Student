package com.studentservice.entities;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Marks {
    private String marksId;
    private String userId;
    private String subjectId;
    private  int mark;
    private char grade;

    private Subject subject;
}
