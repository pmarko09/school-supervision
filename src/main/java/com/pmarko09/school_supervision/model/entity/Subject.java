package com.pmarko09.school_supervision.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
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
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.PERSIST)
    private Set<Exam> exams = new HashSet<>();

    @ManyToMany(mappedBy = "subjects", cascade = CascadeType.PERSIST)
    private Set<Student> students = new HashSet<>();

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
                ", teacher=" + teacher.getId() +
                ", exams=" + exams.stream().map(Exam::getId).collect(Collectors.toSet()) +
                ", students=" + students.stream().map(Student::getId).collect(Collectors.toSet());
    }

    public static void update(Subject subject, Subject updatedSubject) {
        updatedSubject.setName(subject.getName());
        updatedSubject.setTeacher(subject.getTeacher());
        updatedSubject.setExams(subject.getExams());
        updatedSubject.setStudents(subject.getStudents());
    }
}
