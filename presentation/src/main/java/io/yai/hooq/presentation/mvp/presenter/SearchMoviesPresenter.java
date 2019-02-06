package io.yai.hooq.presentation.mvp.presenter;


import android.text.TextUtils;

import java.util.List;

import io.yai.hooq.BuildConfig;
import io.yai.hooq.domain.entity.ConfigurationEntity;
import io.yai.hooq.domain.entity.MovieEntity;
import io.yai.hooq.domain.usecase.GetImageConfigurationUseCase;
import io.yai.hooq.domain.usecase.SearchMovieUseCase;
import io.yai.hooq.presentation.TMDb;
import io.yai.hooq.presentation.mapper.MovieMapper;
import io.yai.hooq.presentation.mvp.BasePresenter;
import io.yai.hooq.presentation.mvp.view.SearchMoviesView;

public class SearchMoviesPresenter implements BasePresenter {

    private SearchMoviesView view;
    private String apiKey;
    private String lastQuery = "";

    public SearchMoviesPresenter(SearchMoviesView view) {
        this.view = view;
        this.apiKey = BuildConfig.TMDB_API_KEY;
    }

    @Override
    public void createView() {
        hideAllViews();
        view.showLoading();

        checkIfHasTheBaseImageURL();
    }

    @Override
    public void destroyView() {
        view.cleanTimer();
    }

    public String getLastQuery(){
        return lastQuery;
    }

    public void performSearch(String query, final int page) {
        if(!TextUtils.isEmpty(query) && !lastQuery.equals(query.trim())) { // avoid blank searches and consecutive repeated searches

            lastQuery = query.trim(); // store the last query

            view.hideView();
            view.showLoading();

            TMDb.JOB_MANAGER.addJobInBackground(new SearchMovieUseCase(apiKey, lastQuery, page, new SearchMovieUseCase.SearchMovieUseCaseCallback() {
                @Override
                public void onMoviesSearched(List<MovieEntity> movieEntities) {
                    view.hideLoading();
                    view.renderMoviesList(new MovieMapper().toModels(movieEntities), page);
                    view.showView();
                }

                @Override
                public void onError(String reason) {
                    view.hideLoading();
                    view.removeMoviesList();
                    view.showFeedback(reason);
                    lastQuery = "";
                }
            }));
        }
    }

    public void performSearchContinue(final int page) {
        view.hideView();
        view.showLoading();

        TMDb.JOB_MANAGER.addJobInBackground(new SearchMovieUseCase(apiKey, lastQuery, page, new SearchMovieUseCase.SearchMovieUseCaseCallback() {
            @Override
            public void onMoviesSearched(List<MovieEntity> movieEntities) {
                view.hideLoading();
                view.renderMoviesList(new MovieMapper().toModels(movieEntities), page);
                view.showView();
            }

            @Override
            public void onError(String reason) {
                view.hideLoading();
                view.removeMoviesList();
                view.showFeedback(reason);
                lastQuery = "";
            }
        }));
    }

    private void hideAllViews() {
        view.hideView();
        view.hideLoading();
    }

    private void checkIfHasTheBaseImageURL() {
        if(!TMDb.LOCAL_DATA.hasBaseImageURL()) {
            TMDb.JOB_MANAGER.addJobInBackground(new GetImageConfigurationUseCase(apiKey, new GetImageConfigurationUseCase.GetImageConfigurationUseCaseCallback() {
                @Override
                public void onConfigurationDownloaded(ConfigurationEntity configurationEntity) {
                    TMDb.LOCAL_DATA.storeBaseImageURL(configurationEntity.getBase_url());

                    showEmptyMovies();
                }

                @Override
                public void onError(String reason) {
                    view.hideLoading();
                    view.showView();
                    view.showFeedback(reason);
                }
            }));

        } else {
            showEmptyMovies();
        }
    }

    private void showEmptyMovies() {
        view.hideLoading();
        view.removeMoviesList();
        view.showView();
    }
}
