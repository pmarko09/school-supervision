package com.pmarko09.school_supervision.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "EXAM_RESULT")
@Getter
@Setter
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    private Double grade;
    private LocalDate time;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExamResult other)) {
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
        return "ExamResult{" +
                "id=" + id +
                ", student=" + student.getId() +
                ", exam=" + exam.getId() +
                ", grade=" + grade +
                ", time=" + time +
                '}';
    }

    public static void update(ExamResult examResult, ExamResult updated) {
        examResult.setStudent(updated.getStudent());
        examResult.setExam(updated.getExam());
        examResult.setGrade(updated.getGrade());
        examResult.setTime(updated.getTime());
    }
}
