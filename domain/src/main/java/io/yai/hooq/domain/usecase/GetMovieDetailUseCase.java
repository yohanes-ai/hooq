package io.yai.hooq.domain.usecase;

import io.yai.hooq.domain.BaseUseCase;
import io.yai.hooq.domain.BaseUseCaseCallback;
import io.yai.hooq.domain.entity.MovieDetailEntity;
import io.yai.hooq.domain.service.API;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetMovieDetailUseCase extends BaseUseCase {

    public interface GetMovieDetailUseCaseCallback extends BaseUseCaseCallback {
        void onMovieDetailLoaded(MovieDetailEntity movieDetailEntity);
    }

    private int movieID;
    private String apiKey;

    public GetMovieDetailUseCase(String apiKey, int movieID, GetMovieDetailUseCaseCallback callback) {
        super(callback);
        this.movieID = movieID;
        this.apiKey = apiKey;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().movieDetails(apiKey, movieID, new Callback<MovieDetailEntity>() {
            @Override
            public void success(MovieDetailEntity movieDetailEntity, Response response) {
                ((GetMovieDetailUseCaseCallback)callback).onMovieDetailLoaded(movieDetailEntity);
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
