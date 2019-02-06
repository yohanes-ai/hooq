package io.yai.hooq.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.yai.hooq.R;
import io.yai.hooq.presentation.Utils;
import io.yai.hooq.presentation.ui.activity.NowPlayingMoviesActivity;
import io.yai.hooq.presentation.ui.activity.SearchMoviesActivity;


public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private View container;

    private boolean shouldFade = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        container = findViewById(R.id.container);

        configureToolbar();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, getMainFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!shouldFade) {
            Utils.restoreToolbarColor(this, toolbar);
        }
    }

    private void configureToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public abstract int getLayoutResource();
    public abstract Fragment getMainFragment();

    public Toolbar getToolbar() {
        return toolbar;
    }
    public void setShouldFadeToolbar(boolean shouldFade) {
        this.shouldFade = shouldFade;
    }
    public void removeContentPaddingTop() {
        if(container != null) {
            container.setPadding(0,0,0,0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_now_playing) {
            Intent intent = new Intent(this, NowPlayingMoviesActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_search){
            Intent intent = new Intent(this, SearchMoviesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
