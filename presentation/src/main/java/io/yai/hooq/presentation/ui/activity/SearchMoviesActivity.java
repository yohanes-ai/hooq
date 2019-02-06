package io.yai.hooq.presentation.ui.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import io.yai.hooq.R;
import io.yai.hooq.presentation.ui.BaseActivity;
import io.yai.hooq.presentation.ui.fragment.SearchMoviesFragment;

public class SearchMoviesActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("TMDB : Search Movie");
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_with_toolbar;
    }

    @Override
    public Fragment getMainFragment() {
        return SearchMoviesFragment.newInstance();
    }
}
