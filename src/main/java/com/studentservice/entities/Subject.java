package com.studentservice.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Subject {

    private String subjectId;
    private String name;
    private String branch;
}
