package io.yai.hooq.presentation.mvp.view;

import java.util.List;

import io.yai.hooq.presentation.mvp.model.ImageModel;

public interface GalleryView extends LoadDataView {
    void renderImages(List<ImageModel> images);
    void clearImages();
}
