package io.yai.hooq.presentation.mapper;


import io.yai.hooq.domain.BaseMapper;
import io.yai.hooq.domain.entity.ImageEntity;
import io.yai.hooq.presentation.Utils;
import io.yai.hooq.presentation.mvp.model.ImageModel;

public class ImageMapper extends BaseMapper<ImageEntity, ImageModel>{

    private String size;

    public ImageMapper(String size) {
        this.size = size;
    }

    @Override
    public ImageModel toModel(ImageEntity entity) {
        ImageModel model = new ImageModel();

        model.setOriginalURL(Utils.buildCompleteImageURL(entity.getFile_path(), "original"));
        model.setUrl(Utils.buildCompleteImageURL(entity.getFile_path(), size));

        return model;
    }

    @Override
    public ImageModel deserializeModel(String serializedModel) {
        return gson.fromJson(serializedModel, ImageModel.class);
    }
}
