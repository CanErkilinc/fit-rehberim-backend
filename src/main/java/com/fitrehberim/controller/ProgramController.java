package com.fitrehberim.controller;

import com.fitrehberim.dto.ProgramResponse;
import com.fitrehberim.service.ProgramService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/program")
public class ProgramController {
    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<ProgramResponse>> generateProgram(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(programService.generateProgram(email));
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<ProgramResponse>> getWeeklyProgram(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(programService.getWeeklyProgram(email));
    }
}