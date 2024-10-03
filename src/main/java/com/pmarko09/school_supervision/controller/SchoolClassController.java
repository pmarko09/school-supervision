package com.pmarko09.school_supervision.controller;

import com.pmarko09.school_supervision.model.dto.SchoolClassDTO;
import com.pmarko09.school_supervision.model.entity.SchoolClass;
import com.pmarko09.school_supervision.service.SchoolClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schoolClasses")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @GetMapping
    public List<SchoolClassDTO> getAllSchoolClasses() {
        return schoolClassService.getAllSchoolClasses();
    }

    @PostMapping
    public SchoolClassDTO addSchoolClass(@RequestBody SchoolClass schoolClass) {
        return schoolClassService.addSchoolClass(schoolClass);
    }

    @GetMapping("/{id}")
    public SchoolClassDTO getSchoolClass(@PathVariable Long id) {
        return schoolClassService.getSchoolClass(id);
    }

    @PutMapping("/{id}")
    public SchoolClassDTO updateSchoolClass(@PathVariable Long id, @RequestBody SchoolClass schoolClass) {
        return schoolClassService.updateSchoolClass(id, schoolClass);
    }

    @DeleteMapping("/{id}")
    public SchoolClassDTO deleteSchoolClass(@PathVariable Long id) {
        return schoolClassService.deleteSchoolClass(id);
    }

    @PostMapping("/{schoolClassId}/students/{studentId}")
    public SchoolClassDTO assignSchoolClassToStudents(@PathVariable Long schoolClassId, @PathVariable Long studentId) {
        return schoolClassService.assignSchoolClassToStudents(schoolClassId, studentId);
    }
}
