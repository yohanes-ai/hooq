package io.yai.hooq.domain.service;


import io.yai.hooq.domain.entity.MovieDetailEntity;
import io.yai.hooq.domain.service.response.GetImageConfigurationResponse;
import io.yai.hooq.domain.service.response.GetMovieImagesResponse;
import io.yai.hooq.domain.service.response.NowPlayingResponse;
import io.yai.hooq.domain.service.response.SearchMovieResponse;
import io.yai.hooq.domain.service.response.SimilarResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public class API {

    public static final String BASE_URL = "http://api.themoviedb.org/3";

    public interface ROUTES {

        //CONFIGURATIONS
        @GET("/configuration")
        void configurations(@Query("api_key") String apiKey, Callback<GetImageConfigurationResponse> callback);

        //MOVIE SEARCH AUTOCOMPLETE
        @GET("/search/movie")
        void search(@Query("api_key") String apiKey, @Query("query") String query, @Query("page") int page, Callback<SearchMovieResponse> callback);

        //MOVIE DETAIL
        @GET("/movie/{id}")
        void movieDetails(@Query("api_key") String apiKey, @Path("id") int movieID, Callback<MovieDetailEntity> callback);

        @GET("/movie/now_playing")
        void nowPlaying(@Query("api_key") String apiKey, @Query("page") int page, Callback<NowPlayingResponse> callback);

        @GET("/movie/{id}/similar")
        void similar(@Query("api_key") String apiKey, @Path("id") int movieID, Callback<SimilarResponse> callback);

        //MOVIE IMAGES
        @GET("/movie/{id}/images")
        void movieImages(@Query("api_key") String apiKey, @Path("id") int movieID, Callback<GetMovieImagesResponse> callback);
    }

    public static ROUTES http() {
        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(ROUTES.class);
    }

}
