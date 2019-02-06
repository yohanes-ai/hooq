package io.yai.hooq.presentation.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.yai.hooq.R;
import io.yai.hooq.presentation.TMDb;
import io.yai.hooq.presentation.mvp.model.MovieModel;
import io.yai.hooq.presentation.ui.activity.MovieDetailActivity;

public class NowPlayingMoviesAdapter extends RecyclerView.Adapter<NowPlayingMoviesAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<MovieModel> movies = new ArrayList<>();
    private List<MovieModel> filteredMovies = new ArrayList<>();
    private MovieFilter movieFilter = new MovieFilter();

    public NowPlayingMoviesAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MovieModel> movies) {
        this.movies = movies;
        this.filteredMovies = movies;
        notifyDataSetChanged();
    }

    public void clearData() {
        movies.clear();
        filteredMovies.clear();
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position){
        return filteredMovies.get(position).getId();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MovieModel movieModel = filteredMovies.get(i);

        viewHolder.title.setText(movieModel.getName());

        if (TextUtils.isEmpty(movieModel.getYearOfRelease())) {
            viewHolder.subtitle.setVisibility(View.GONE);
        } else {
            viewHolder.subtitle.setText(movieModel.getYearOfRelease());
        }

        TMDb.PICASSO.load(movieModel.getSmallCover()).into(viewHolder.cover);
    }

    @Override
    public int getItemCount() {
        return filteredMovies.size();
    }

    @Override
    public Filter getFilter() {
        return movieFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        private ImageView cover;
        private TextView title, subtitle;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            cover = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    context.startActivity(MovieDetailActivity.getCallingIntent(context, filteredMovies.get(getPosition())));
                }
            }, 200); // <--- Giving time to the ripple effect finish before opening a new activity
        }
    }

    private class MovieFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<MovieModel> tempList = new ArrayList<>();

                // search content in friend list
                for (MovieModel movie : movies) {
                    if (movie.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(movie);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = movies.size();
                filterResults.values = movies;
            }

            return filterResults;
        }

        /**
         * Notify about filtered list to ui
         * @param constraint text
         * @param results filtered result
         */
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredMovies = (ArrayList<MovieModel>) results.values;
            notifyDataSetChanged();
        }
    }
}
