package com.fitrehberim.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramResponse {
    private String day;
    private String workoutName;
    private int workoutDuration;
    private int calorieBurn;
    private String dietNote;
    private boolean isRestDay;
}