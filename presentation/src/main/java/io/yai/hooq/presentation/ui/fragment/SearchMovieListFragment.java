package io.yai.hooq.presentation.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.yai.hooq.R;
import io.yai.hooq.presentation.mapper.MovieMapper;
import io.yai.hooq.presentation.mvp.model.MovieModel;
import io.yai.hooq.presentation.mvp.presenter.MovieListPresenter;
import io.yai.hooq.presentation.mvp.presenter.SearchMoviesPresenter;
import io.yai.hooq.presentation.mvp.view.MovieListView;
import io.yai.hooq.presentation.ui.BaseFragment;
import io.yai.hooq.presentation.ui.adapter.SearchMoviesAdapter;

public class SearchMovieListFragment extends BaseFragment implements MovieListView {

    public static final String TAG = SearchMovieListFragment.class.getSimpleName();
    public static final String MOVIES = TAG + ".MOVIES";

    private RecyclerView recyclerView;
    private View loadingView, retryView, emptyView;
    private TextView retryMsg, emptyMsg;
    private Button retryButton;
    private int page;

    private SearchMoviesAdapter adapter;
    private MovieListPresenter presenter;
    private GridLayoutManager gridLayoutManager;

    private List<MovieModel> movies;
    private SearchMoviesPresenter searchMoviesPresenter;

    public static SearchMovieListFragment newInstance(List<MovieModel> movies) {

        SearchMovieListFragment fragment = new SearchMovieListFragment();

        Bundle args = new Bundle();
        args.putStringArrayList(MOVIES, (ArrayList<String>) new MovieMapper().serializeModels(movies));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle args = getArguments();

        if(args != null) {
            movies = new MovieMapper().deserializeModels(args.getStringArrayList(MOVIES));
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void instantiatePresenter() {
        presenter = new MovieListPresenter(this, movies);
        page = 1;
    }

    @Override
    public void initializePresenter() {
        presenter.createView();
    }

    @Override
    public void finalizePresenter() {
        presenter.destroyView();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_recycler;
    }

    @Override
    public void mapGUI(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        loadingView = view.findViewById(R.id.view_loading);
        emptyView = view.findViewById(R.id.view_empty);
        retryView = view.findViewById(R.id.view_retry);

        emptyMsg = (TextView) emptyView.findViewById(R.id.text);
        retryMsg = (TextView) retryView.findViewById(R.id.text);

        retryButton = (Button) retryView.findViewById(R.id.button);
    }

    public void setSearchMoviesPresenter(SearchMoviesPresenter basePresenter) {
        this.searchMoviesPresenter = basePresenter;
    }

    public void setNotifyDataChanged(List<MovieModel> newMovies){
        movies.addAll(newMovies);
        adapter.notifyDataSetChanged();
    }

    public List<MovieModel> getMovies() {
        return movies;
    }

    @Override
    public void configureGUI() {

        //RECYLCER VIEW CONFIGURATIONS
        adapter = new SearchMoviesAdapter(getActivity());
        adapter.setHasStableIds(true);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        //RECYLCER VIEW CONFIGURATIONS

        recyclerView.setOnScrollListener(
                new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);

                        if (!recyclerView.canScrollVertically(1)) {
                            page++;
                            searchMoviesPresenter.performSearchContinue(page);
                        }
                    }
                }
        );

        //RETRY BUTTON CONFIGURATIONS
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createView();
            }
        });
        //RETRY BUTTON CONFIGURATIONS

    }

    @Override
    public void renderMovies(List<MovieModel> movies) {
        adapter.setData(movies);
    }

    @Override
    public void clearMovies() {
        adapter.clearData();
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showRetry(String msg) {
        retryMsg.setText(msg);
        retryView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        retryView.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty(String msg) {
        emptyMsg.setText(msg);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmpty() {
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showView() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showFeedback(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void destroyItself() {
        getActivity().finish();
    }
}
