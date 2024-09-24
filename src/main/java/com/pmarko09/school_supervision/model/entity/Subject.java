package com.pmarko09.school_supervision.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "SUBJECTS")
@Getter
@Setter
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "subject")
    private Set<Exam> exams = new HashSet<>();                //do tego chyba odrebna metoda w mapperze

    @ManyToMany(mappedBy = "subjects")
    private Set<Student> students = new HashSet<>();            // rowniez

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subject other)) {
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
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacher=" + teacher +
                ", exams=" + exams.stream().map(Exam::getId).collect(Collectors.toSet()) +
                ", students=" + students.stream().map(Student::getId).collect(Collectors.toSet());
    }
}
