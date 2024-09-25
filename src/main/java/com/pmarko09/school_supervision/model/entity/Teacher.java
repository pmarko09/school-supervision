package com.pmarko09.school_supervision.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TEACHERS")
@Getter
@Setter
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @OneToOne(mappedBy = "teacher", cascade = CascadeType.ALL)
    private Subject subject;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teacher other)) {
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
        return "Teacher{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", subject=" + subject.getId() +
                '}';
    }

    public static void update(Teacher teacher, Teacher updateTeacher) {
        updateTeacher.setFirstname(teacher.getFirstname());
        updateTeacher.setLastname(teacher.getLastname());
        updateTeacher.setEmail(teacher.getEmail());
        updateTeacher.setPassword(teacher.getPassword());
        updateTeacher.setSubject(teacher.getSubject());
    }
}
