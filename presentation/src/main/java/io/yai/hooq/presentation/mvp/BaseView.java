package io.yai.hooq.presentation.mvp;

public interface BaseView {

    void showView();
    void hideView();

    void showFeedback(String msg);
    void destroyItself();
}
