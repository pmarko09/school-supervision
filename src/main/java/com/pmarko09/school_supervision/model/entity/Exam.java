package com.pmarko09.school_supervision.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "EXAMS")
@Getter
@Setter
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Subject subject;

    private Double grade;
    private LocalDateTime time;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exam other)) {
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
        return "Exam{" +
                "id=" + id +
                ", subject=" + subject.getId() +
                ", grade=" + grade +
                ", time=" + time +
                '}';
    }

    public static void update(Exam exam, Exam updatedExam) {
        exam.setSubject(updatedExam.getSubject());
        exam.setGrade(updatedExam.getGrade());
        exam.setTime(updatedExam.getTime());
    }
}
