package com.michael.ai_code_review.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
public class ScoreAndRecommendationMapper {

    private Integer score;
    private List<String> recommendations;

    @JsonCreator
    public ScoreAndRecommendationMapper(
            @JsonProperty("score") int score,
            @JsonProperty("recommendations") List<String> recommendations) {
        this.score = score;
        this.recommendations = recommendations;
    }

    public ScoreAndRecommendationMapper() {

    }
}
