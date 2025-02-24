package com.michael.ai_code_review.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "code_reviews_table",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"unique_id"})})
public class CodeReviewModel {

    @Id
    @SequenceGenerator(
            name = "code_reviews_table_sequence",
            sequenceName = "code_reviews_table_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "code_reviews_table_sequence"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "suggestions")
    private String suggestions;

    @Column(name = "quality_score")
    private int qualityScore;

    @Column(name = "unique_id")
    private Long uniqueId;
}
