package io.yai.hooq.presentation.mvp.view;

import java.util.List;

import io.yai.hooq.presentation.mvp.model.MovieModel;

public interface MovieDetailView extends LoadDataView {

    void updateBackground(String value);

    void updateTitle(String value);

    void updateYearOfRelease(String value);
    void hideYearOfRelease();

    void updateHomepage(String value);
    void hideHomepage();

    void updateCompanies(String value);
    void hideCompanies();

    void updateTagline(String value);
    void hideTagline();

    void renderMoviesList(List<MovieModel> movies);
    void removeMoviesList();

    void updateOverview(String value);
    void hideOverview();

    void openMovieWebsite(String url);
    void openGallery();

    void updateToolbarColor();
}
