package io.yai.hooq.presentation.mvp.presenter;


import java.util.List;

import io.yai.hooq.presentation.mvp.BasePresenter;
import io.yai.hooq.presentation.mvp.model.MovieModel;
import io.yai.hooq.presentation.mvp.view.MovieListView;

public class MovieListPresenter implements BasePresenter {

    private MovieListView view;
    private List<MovieModel> movies;

    public MovieListPresenter(MovieListView view, List<MovieModel> movies) {
        this.view = view;
        this.movies = movies;
    }

    @Override
    public void createView() {

        hideAllViews();

        view.showLoading();

        if(movies.isEmpty()) {
            view.showEmpty("There are no movies to show");
            view.hideLoading();
        } else {
            view.renderMovies(movies);
            view.hideLoading();
            view.showView();
        }
    }

    @Override
    public void destroyView() {}

    private void hideAllViews() {
        view.hideView();
        view.hideEmpty();
        view.hideLoading();
        view.hideRetry();
    }
}
