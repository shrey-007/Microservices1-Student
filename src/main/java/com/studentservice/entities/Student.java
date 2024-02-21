package com.studentservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
// column is database=user_id, about,email,name
public class Student {

    @Id
    private String userId;
    private String name;
    private String email;
    private  String about;

    //Transient means ki is propert ko database mai store mt krna.
    //Iske liye alag se microservce banaege
    @Transient
    private List<Marks> marks=new ArrayList<>();


}
