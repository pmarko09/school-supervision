package com.pmarko09.school_supervision.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "STUDENTS")
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private Integer cardNumber;
    private String email;
    private String password;

    @ManyToOne
    private SchoolClass schoolClass;

    @ManyToMany
    @JoinTable(
            name = "STUDENT_SUBJECT",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student other)) {
            return false;
        }

        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }

        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", cardNumber=" + cardNumber +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", schoolClass=" + schoolClass.getId() +
                ", subjects=" + subjects.stream().map(Subject::getId).collect(Collectors.toSet()) +
                '}';
    }
}
