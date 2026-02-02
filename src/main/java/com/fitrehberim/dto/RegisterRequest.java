package com.fitrehberim.dto;

import com.fitrehberim.model.enums.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private int height;
    private int weight;
    private ActivityLevel activityLevel;
    private FitnessLevel fitnessLevel;
    private Goal goal;
    private List<String> availableDays;
    private String availableTime;
}