package io.yai.hooq.presentation.mvp.view;

import java.util.List;

import io.yai.hooq.presentation.mvp.model.MovieModel;

public interface SearchMoviesView extends LoadDataView {
    void renderMoviesList(List<MovieModel> movies, int page);
    void removeMoviesList();

    void cleanTimer();
}
