package com.fitrehberim.service;

import com.fitrehberim.dto.ProgramResponse;
import com.fitrehberim.model.*;
import com.fitrehberim.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramService {
    private final WeeklyProgramRepository programRepository;
    private final UserRepository userRepository;
    private final WorkoutGenerator workoutGenerator;

    public ProgramService(WeeklyProgramRepository programRepository, 
                         UserRepository userRepository,
                         WorkoutGenerator workoutGenerator) {
        this.programRepository = programRepository;
        this.userRepository = userRepository;
        this.workoutGenerator = workoutGenerator;
    }

    @Transactional
    public List<ProgramResponse> generateProgram(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        programRepository.deleteByUserId(user.getId());

        List<WeeklyProgram> programs = workoutGenerator.generateProgram(user);
        programRepository.saveAll(programs);

        return programs.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public List<ProgramResponse> getWeeklyProgram(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<WeeklyProgram> programs = programRepository.findByUserId(user.getId());
        
        if (programs.isEmpty()) {
            return generateProgram(email);
        }

        return programs.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private ProgramResponse toResponse(WeeklyProgram program) {
        return ProgramResponse.builder()
            .day(program.getDay())
            .workoutName(program.getWorkoutName())
            .workoutDuration(program.getWorkoutDuration())
            .calorieBurn(program.getCalorieBurn())
            .dietNote(program.getDietNote())
            .isRestDay(program.isRestDay())
            .build();
    }
}