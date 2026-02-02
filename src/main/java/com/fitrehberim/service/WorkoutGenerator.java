package com.fitrehberim.service;

import com.fitrehberim.model.*;
import com.fitrehberim.model.enums.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class WorkoutGenerator {
    private static final List<String> ALL_DAYS = Arrays.asList(
        "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar"
    );

    public List<WeeklyProgram> generateProgram(User user) {
        List<WeeklyProgram> programs = new ArrayList<>();
        
        for (String day : ALL_DAYS) {
            if (user.getAvailableDays().contains(day)) {
                programs.add(createWorkoutDay(user, day));
            } else {
                programs.add(createRestDay(user.getId(), day));
            }
        }
        
        return programs;
    }

    private WeeklyProgram createWorkoutDay(User user, String day) {
        WorkoutData workout = selectWorkout(user.getGoal(), user.getFitnessLevel());
        int duration = adjustDuration(workout.duration, user.getFitnessLevel());
        int calories = calculateCalories(user, duration);
        
        return WeeklyProgram.builder()
            .userId(user.getId())
            .day(day)
            .workoutName(workout.name)
            .workoutDuration(duration)
            .calorieBurn(calories)
            .dietNote(getDietNote(user.getGoal()))
            .isRestDay(false)
            .build();
    }

    private WeeklyProgram createRestDay(Long userId, String day) {
        return WeeklyProgram.builder()
            .userId(userId)
            .day(day)
            .workoutName("Dinlenme Günü")
            .workoutDuration(0)
            .calorieBurn(0)
            .dietNote("Kas gelişimi için dinlenme önemlidir")
            .isRestDay(true)
            .build();
    }

    private WorkoutData selectWorkout(Goal goal, FitnessLevel level) {
        Map<Goal, List<WorkoutData>> workouts = new HashMap<>();
        
        workouts.put(Goal.LOSE_WEIGHT, Arrays.asList(
            new WorkoutData("Kardio - Koşu", 40),
            new WorkoutData("HIIT", 30),
            new WorkoutData("Yürüyüş", 50),
            new WorkoutData("Bisiklet", 45)
        ));
        
        workouts.put(Goal.GAIN_MUSCLE, Arrays.asList(
            new WorkoutData("Göğüs-Kol", 50),
            new WorkoutData("Sırt-Omuz", 50),
            new WorkoutData("Bacak", 55),
            new WorkoutData("Full Body", 60)
        ));
        
        workouts.put(Goal.MAINTAIN, Arrays.asList(
            new WorkoutData("Karma Antrenman", 40),
            new WorkoutData("Fonksiyonel", 35),
            new WorkoutData("Yoga", 50),
            new WorkoutData("Pilates", 45)
        ));
        
        List<WorkoutData> list = workouts.get(goal);
        return list.get(new Random().nextInt(list.size()));
    }

    private int adjustDuration(int base, FitnessLevel level) {
        return switch (level) {
            case BEGINNER -> (int)(base * 0.7);
            case INTERMEDIATE -> base;
            case ADVANCED -> (int)(base * 1.2);
        };
    }

    private int calculateCalories(User user, int duration) {
        double bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * 25 + 5;
        double multiplier = switch (user.getActivityLevel()) {
            case LOW -> 1.2;
            case MEDIUM -> 1.5;
            case HIGH -> 1.7;
        };
        return (int)((bmr * multiplier / 1440) * duration * 1.5);
    }

    private String getDietNote(Goal goal) {
        return switch (goal) {
            case LOSE_WEIGHT -> "Kalori açığı: Protein ağırlıklı, düşük karbonhidrat. Bol su.";
            case GAIN_MUSCLE -> "Kalori fazlası: Yüksek protein, orta karb. 5-6 öğün.";
            case MAINTAIN -> "Dengeli beslenme: Protein, karb ve yağ dengeli.";
        };
    }

    private static class WorkoutData {
        String name;
        int duration;
        
        WorkoutData(String name, int duration) {
            this.name = name;
            this.duration = duration;
        }
    }
}