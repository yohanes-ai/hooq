package io.yai.hooq.presentation.mvp.view;

import java.util.List;

import io.yai.hooq.presentation.mvp.model.MovieModel;

public interface MovieListView extends LoadDataView {

    void renderMovies(List<MovieModel> movies);
    void clearMovies();

}
