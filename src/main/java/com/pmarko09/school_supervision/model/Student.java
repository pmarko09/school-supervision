package com.pmarko09.school_supervision.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Student {

    private String firstname;
    private String lastname;
    private int cardNumber;
    private Class studentClass;
    private List<Subject> subjects;
    private List<Exam> exams;

}
