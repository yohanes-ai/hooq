package io.yai.hooq.domain.service.response;

import java.util.List;

import io.yai.hooq.domain.entity.MovieEntity;

public class SimilarResponse {
    private List<MovieEntity> results;

    public List<MovieEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieEntity> results) {
        this.results = results;
    }
}
