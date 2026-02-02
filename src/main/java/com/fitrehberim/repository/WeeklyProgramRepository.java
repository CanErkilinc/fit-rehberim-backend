package com.fitrehberim.repository;

import com.fitrehberim.model.WeeklyProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WeeklyProgramRepository extends JpaRepository<WeeklyProgram, Long> {
    List<WeeklyProgram> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}