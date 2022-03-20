package com.example.mytmdbclient.service;

import com.example.mytmdbclient.model.CastDBResponse;
import com.example.mytmdbclient.model.GenreDBResponse;
import com.example.mytmdbclient.model.MovieDBResponse;
import com.example.mytmdbclient.model.NowPlayingDBResponse;
import com.example.mytmdbclient.model.SimilarDBResponse;
import com.example.mytmdbclient.model.TopRateDBResponse;
import com.example.mytmdbclient.model.TrailerDBResponse;
import com.example.mytmdbclient.model.TvAiringTodayDBResponse;
import com.example.mytmdbclient.model.UpComingDBResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDataService
{
    @GET("movie/popular")
    Call<MovieDBResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MovieDBResponse> getPopularMoviesWihPaging(@Query("api_key") String apiKey, @Query("page") long page);

    @GET("genre/movie/list")
    Call<GenreDBResponse> getGenresIdMovies(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/top_rated")
    Call<TopRateDBResponse> getTopRateMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<TopRateDBResponse> getTopRateMoviesWithPaging(@Query("api_key") String apiKey, @Query("page") long page);

    @GET("movie/upcoming")
    Call<UpComingDBResponse> getUpComingMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<UpComingDBResponse> getUpComingMoviesWithPaging(@Query("api_key") String apiKey, @Query("page") long page);

    @GET("movie/now_playing")
    Call<NowPlayingDBResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<NowPlayingDBResponse> getNowPlayingMoviesWithPaging(@Query("api_key") String apiKey, @Query("page") long page);

    @GET("movie/{movie_id}/videos")
    Call<TrailerDBResponse> getMoviesTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("search/multi")
    Call<MovieDBResponse> searchForMovies(@Query("query") String query, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<CastDBResponse> getCastMovies(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Call<SimilarDBResponse> getSimilarMovies(@Path("movie_id") int id, @Query("api_key") String apiKey);


    /*// Instead of using 4 separate requests we use append_to_response
    // to eliminate duplicate requests and save network bandwidth
    // this request return full movie details, trailers, reviews and cast
    @GET("movie/{id}?append_to_response=videos,credits,reviews")
    LiveData<ApiResponse<Movie>> getMovieDetails(@Path("id") long id);*/


    //TODO : Tv
    @GET("tv/airing_today")
    Call<TvAiringTodayDBResponse> getTvAiring(@Query("api_key") String apiKey);

    @GET("tv/airing_today")
    Call<TvAiringTodayDBResponse> getTvAiringWithPaging(@Query("api_key") String apiKey, @Query("page") long page);

    @GET("tv/{tv_id}/videos")
    Call<TrailerDBResponse> getTvAiringTrailer(@Path("tv_id") int id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/credits")
    Call<CastDBResponse> getCastTvAiring(@Path("tv_id") int id, @Query("api_key") String apiKey);


    /*@GET("tv/{tv_id}?append_to_response=videos,credits")
    LiveData<ApiResponse<TvAppendDBResponse>> getTvDetails(@Path("tv_id") int id);*/


}
