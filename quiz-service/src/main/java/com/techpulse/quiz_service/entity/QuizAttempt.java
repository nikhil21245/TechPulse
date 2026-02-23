package com.techpulse.quiz_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quiz_attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;



    private Integer score;

    private Boolean passed;
    @Column(nullable = true)
    private LocalDateTime startedAt;
    @Column(nullable = true)
    private LocalDateTime submittedAt;

    @ManyToOne
    private Quiz quiz;

    @OneToMany(
            mappedBy = "attempt",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserAnswer> answers = new ArrayList<>();
}

