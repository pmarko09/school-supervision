package com.pmarko09.school_supervision.service;

import com.pmarko09.school_supervision.exception.subject.SubjectNotFoundException;
import com.pmarko09.school_supervision.mapper.SubjectMapper;
import com.pmarko09.school_supervision.model.dto.SubjectDTO;
import com.pmarko09.school_supervision.model.entity.Subject;
import com.pmarko09.school_supervision.repository.SubjectRepository;
import com.pmarko09.school_supervision.validation.SubjectValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    public Set<SubjectDTO> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDto)
                .collect(Collectors.toSet());
    }

    public SubjectDTO getSubject(Long id) {
        Subject subject = SubjectValidation.subjectExists(subjectRepository, id);
        return subjectMapper.toDto(subjectRepository.save(subject));
    }

    public SubjectDTO addSubject(Subject subject) {
        SubjectValidation.validateSubjectData(subject);
        return subjectMapper.toDto(subjectRepository.save(subject));
    }

    public SubjectDTO updateSubject(Long id, Subject updatedSubject) {
        Subject subject = SubjectValidation.subjectExists(subjectRepository, id);
        SubjectValidation.validateSubjectData(updatedSubject);
        Subject.update(subject, updatedSubject);
        return subjectMapper.toDto(subjectRepository.save(subject));
    }

    public SubjectDTO deleteSubject(Long id) {
        Subject subject = SubjectValidation.subjectExists(subjectRepository, id);
        subjectRepository.delete(subject);
        return subjectMapper.toDto(subject);
    }
}
