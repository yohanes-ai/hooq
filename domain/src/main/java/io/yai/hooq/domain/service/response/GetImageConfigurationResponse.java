package io.yai.hooq.domain.service.response;


import io.yai.hooq.domain.entity.ConfigurationEntity;

public class GetImageConfigurationResponse {

    private ConfigurationEntity images;

    public ConfigurationEntity getImages() {
        return images;
    }

    public void setImages(ConfigurationEntity images) {
        this.images = images;
    }
}
