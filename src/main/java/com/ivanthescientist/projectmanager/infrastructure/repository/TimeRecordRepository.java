package com.ivanthescientist.projectmanager.infrastructure.repository;

import com.ivanthescientist.projectmanager.domain.model.TimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeRecordRepository extends JpaRepository<TimeRecord, Long> {
}