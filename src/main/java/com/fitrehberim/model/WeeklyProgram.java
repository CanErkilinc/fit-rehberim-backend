package com.fitrehberim.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weekly_programs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeeklyProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
    private String daye;
    private String workoutName;
    private int workoutDuration;
    private int calorieBurn;
    private String dietNote;
    private boolean isRestDay;
}