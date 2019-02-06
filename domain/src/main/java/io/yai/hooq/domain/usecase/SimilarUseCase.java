package io.yai.hooq.domain.usecase;


import java.util.List;

import io.yai.hooq.domain.BaseUseCase;
import io.yai.hooq.domain.BaseUseCaseCallback;
import io.yai.hooq.domain.entity.MovieEntity;
import io.yai.hooq.domain.service.API;
import io.yai.hooq.domain.service.response.SimilarResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SimilarUseCase extends BaseUseCase {
    private int movieId;

    public interface SimilarUseCaseCallback extends BaseUseCaseCallback {
        void onMoviesAppear(List<MovieEntity> movieEntities);
    }

    private String apiKey;

    public SimilarUseCase(String apiKey, int movieId, SimilarUseCaseCallback callback) {
        super(callback);
        this.apiKey = apiKey;
        this.movieId = movieId;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().similar(apiKey, movieId, new Callback<SimilarResponse>() {
            @Override
            public void success(SimilarResponse searchMovieResponse, Response response) {
                ((SimilarUseCaseCallback) callback).onMoviesAppear(searchMovieResponse.getResults());
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
