package io.yai.hooq.presentation.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import io.yai.hooq.R;
import io.yai.hooq.presentation.mapper.MovieMapper;
import io.yai.hooq.presentation.mvp.model.MovieModel;
import io.yai.hooq.presentation.ui.BaseActivity;
import io.yai.hooq.presentation.ui.fragment.MovieDetailFragment;

public class MovieDetailActivity extends BaseActivity {

    public static final String TAG = MovieDetailActivity.class.getSimpleName();
    public static final String MOVIE_MODEL = TAG + ".MOVIE_MODEL";

    private MovieModel movieModel;

    public static Intent getCallingIntent(Context context, MovieModel movieModel) {
        Intent intentToBeCalled = new Intent(context, MovieDetailActivity.class);
        intentToBeCalled.putExtra(MOVIE_MODEL, new MovieMapper().serializeModel(movieModel));

        return intentToBeCalled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle args = getIntent().getExtras();

        if(args != null) {
            movieModel = new MovieMapper().deserializeModel(args.getString(MOVIE_MODEL));
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_with_toolbar;
    }

    @Override
    public Fragment getMainFragment() {
        return MovieDetailFragment.newInstance(movieModel);
    }

    public void setDetail(MovieModel movieModel){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, MovieDetailFragment.newInstance(movieModel))
                .addToBackStack(null)
                .commit();
    }
}
