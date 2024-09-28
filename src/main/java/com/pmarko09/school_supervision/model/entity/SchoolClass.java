package com.pmarko09.school_supervision.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "SCHOOL_CLASSES")
@Getter
@Setter
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    private String name;

    @OneToMany(mappedBy = "schoolClass", cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolClass other)) {
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
        return "SchoolClass{" +
                "id=" + id +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", students=" + students.stream().map(Student::getId).collect(Collectors.toSet()) +
                '}';
    }

    public static void update(SchoolClass schoolClass, SchoolClass updatedSchoolClass) {
        schoolClass.setNumber(updatedSchoolClass.getNumber());
        schoolClass.setName(updatedSchoolClass.getName());
        schoolClass.setStudents(updatedSchoolClass.getStudents());
    }
}
