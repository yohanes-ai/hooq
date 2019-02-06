package io.yai.hooq.presentation.mvp.view;

import java.util.List;

import io.yai.hooq.presentation.mvp.model.ImageModel;

public interface MovieImagesView extends LoadDataView {
    void renderTabs(List<ImageModel> backdrops, List<ImageModel> posters);
}
