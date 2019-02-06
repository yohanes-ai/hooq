package io.yai.hooq.domain.usecase;


import java.util.List;

import io.yai.hooq.domain.BaseUseCase;
import io.yai.hooq.domain.BaseUseCaseCallback;
import io.yai.hooq.domain.entity.MovieEntity;
import io.yai.hooq.domain.service.API;
import io.yai.hooq.domain.service.response.SearchMovieResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchMovieUseCase extends BaseUseCase {

    public interface SearchMovieUseCaseCallback extends BaseUseCaseCallback {
        void onMoviesSearched(List<MovieEntity> movieEntities);
    }

    private String apiKey;
    private String query;
    private int page;

    public SearchMovieUseCase(String apiKey, String query, int page, SearchMovieUseCaseCallback callback) {
        super(callback);
        this.apiKey = apiKey;
        this.query = query;
        this.page = page;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().search(apiKey, query, page, new Callback<SearchMovieResponse>() {
            @Override
            public void success(SearchMovieResponse searchMovieResponse, Response response) {
                ((SearchMovieUseCaseCallback) callback).onMoviesSearched(searchMovieResponse.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getKind() == RetrofitError.Kind.NETWORK) {
                    errorReason = NETWORK_ERROR;
                } else {
                    errorReason = error.getResponse().getReason();
                }
                onCancel();
            }
        });
    }
}
