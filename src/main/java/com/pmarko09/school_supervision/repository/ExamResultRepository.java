package com.pmarko09.school_supervision.repository;

import com.pmarko09.school_supervision.model.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

}
