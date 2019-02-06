package io.yai.hooq.presentation.mvp.view;

import io.yai.hooq.presentation.mvp.BaseView;

public interface LoadDataView extends BaseView {

    void showLoading();
    void hideLoading();

    void showRetry(String msg);
    void hideRetry();

    void showEmpty(String msg);
    void hideEmpty();
}
