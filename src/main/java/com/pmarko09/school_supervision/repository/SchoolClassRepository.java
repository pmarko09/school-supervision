package com.pmarko09.school_supervision.repository;

import com.pmarko09.school_supervision.model.entity.SchoolClass;
import com.pmarko09.school_supervision.service.SchoolClassService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
}
