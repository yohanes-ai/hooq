package io.yai.hooq.presentation.mvp.presenter;

import java.util.List;

import io.yai.hooq.BuildConfig;
import io.yai.hooq.domain.entity.ImageEntity;
import io.yai.hooq.domain.usecase.GetMovieImagesUseCase;
import io.yai.hooq.presentation.TMDb;
import io.yai.hooq.presentation.mapper.ImageMapper;
import io.yai.hooq.presentation.mvp.BasePresenter;
import io.yai.hooq.presentation.mvp.view.MovieImagesView;

public class MovieImagesPresenter implements BasePresenter {

    private MovieImagesView view;
    private int movieID;

    public MovieImagesPresenter(MovieImagesView view, int movieID) {
        this.movieID = movieID;
        this.view = view;
    }

    @Override
    public void createView() {
        hideAllViews();

        view.showLoading();

        downloadMovieImages();
    }

    @Override
    public void destroyView() {}

    private void hideAllViews() {
        view.hideView();
        view.hideEmpty();
        view.hideLoading();
        view.hideRetry();
    }

    private void downloadMovieImages() {
        TMDb.JOB_MANAGER.addJobInBackground(new GetMovieImagesUseCase(BuildConfig.TMDB_API_KEY, movieID, new GetMovieImagesUseCase.GetMovieImagesUseCaseCallback() {
            @Override
            public void onImagesUrlsLoaded(List<ImageEntity> backdrops, List<ImageEntity> posters) {
                view.renderTabs(new ImageMapper("w780").toModels(backdrops), new ImageMapper("w500").toModels(posters));
                view.hideLoading();
                view.showView();
            }

            @Override
            public void onError(String reason) {
                view.showFeedback(reason);
                view.destroyItself();
            }
        }));
    }
}
