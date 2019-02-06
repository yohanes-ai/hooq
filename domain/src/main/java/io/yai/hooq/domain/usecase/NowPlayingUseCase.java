package io.yai.hooq.domain.usecase;


import java.util.List;

import io.yai.hooq.domain.BaseUseCase;
import io.yai.hooq.domain.BaseUseCaseCallback;
import io.yai.hooq.domain.entity.MovieEntity;
import io.yai.hooq.domain.service.API;
import io.yai.hooq.domain.service.response.NowPlayingResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NowPlayingUseCase extends BaseUseCase {
    private int page;

    public interface NowPlayingUseCaseCallback extends BaseUseCaseCallback {
        void onMoviesAppear(List<MovieEntity> movieEntities);
    }

    private String apiKey;

    public NowPlayingUseCase(String apiKey, int page, NowPlayingUseCaseCallback callback) {
        super(callback);
        this.apiKey = apiKey;
        this.page = page;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().nowPlaying(apiKey, page, new Callback<NowPlayingResponse>() {
            @Override
            public void success(NowPlayingResponse searchMovieResponse, Response response) {
                ((NowPlayingUseCaseCallback) callback).onMoviesAppear(searchMovieResponse.getResults());
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
