package io.yai.hooq.domain.usecase;

import io.yai.hooq.domain.BaseUseCase;
import io.yai.hooq.domain.BaseUseCaseCallback;
import io.yai.hooq.domain.entity.ConfigurationEntity;
import io.yai.hooq.domain.service.API;
import io.yai.hooq.domain.service.response.GetImageConfigurationResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetImageConfigurationUseCase extends BaseUseCase {

    public interface GetImageConfigurationUseCaseCallback extends BaseUseCaseCallback {
        void onConfigurationDownloaded(ConfigurationEntity configurationEntity);
    }

    private String apiKey;

    public GetImageConfigurationUseCase(String apiKey, GetImageConfigurationUseCaseCallback callback) {
        super(callback);
        this.apiKey = apiKey;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().configurations(apiKey, new Callback<GetImageConfigurationResponse>() {
            @Override
            public void success(GetImageConfigurationResponse getImageConfigurationResponse, Response response) {
                ((GetImageConfigurationUseCaseCallback) callback).onConfigurationDownloaded(getImageConfigurationResponse.getImages());
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
