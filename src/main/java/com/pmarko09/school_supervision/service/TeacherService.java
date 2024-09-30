package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.mapper.TeacherMapper;
import com.pmarko09.school_supervision.model.dto.TeacherDTO;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.model.entity.Teacher;
import com.pmarko09.school_supervision.repository.SubjectRepository;
import com.pmarko09.school_supervision.repository.TeacherRepository;
import com.pmarko09.school_supervision.validation.SubjectValidation;
import com.pmarko09.school_supervision.validation.TeacherValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherMapper teacherMapper;

    public Set<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacherMapper::toDto)
                .collect(Collectors.toSet());
    }

    public TeacherDTO getTeacher(Long id) {
        Teacher teacher = TeacherValidation.teacherExists(teacherRepository, id);
        return teacherMapper.toDto(teacher);
    }

    public TeacherDTO addTeacher(Teacher teacher) {
        TeacherValidation.validateTeacherData(teacher);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public TeacherDTO updateTeacher(Long id, Teacher updatedTeacher) {
        Teacher teacher = TeacherValidation.teacherExists(teacherRepository, id);
        TeacherValidation.validateTeacherData(updatedTeacher);
        Teacher.update(teacher, updatedTeacher);
        return teacherMapper.toDto(teacherRepository.save(teacher));
    }

    public TeacherDTO deleteTeacher(Long id) {
        Teacher teacher = TeacherValidation.teacherExists(teacherRepository, id);
        teacherRepository.delete(teacher);
        return teacherMapper.toDto(teacher);
    }

    public TeacherDTO assignTeacherToSubject(Long teacherId, Long subjectId) {
        Teacher teacher = TeacherValidation.teacherExists(teacherRepository, teacherId);
        Subject subject = SubjectValidation.subjectExists(subjectRepository, subjectId);

        TeacherValidation.validateTeacherData(teacher);
        SubjectValidation.validateSubjectData(subject);

        teacher.setSubject(subject);
        subject.setTeacher(teacher);

        return teacherMapper.toDto(teacherRepository.save(teacher));
    }
}
