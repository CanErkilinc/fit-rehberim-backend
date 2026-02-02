package com.fitrehberim.model;

import com.fitrehberim.model.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    private int height;
    private int weight;
    
    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;
    
    @Enumerated(EnumType.STRING)
    private FitnessLevel fitnessLevel;
    
    @Enumerated(EnumType.STRING)
    private Goal goal;
    
    @ElementCollection
    private List<String> availableDays;
    
    private String availableTime;
    
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
}