package com.example.mytmdbclient.util;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.mytmdbclient.service.MovieDataService;

public class NowPlayingDataSourceFactory extends DataSource.Factory
{

    private NowPlayingMovieDataSource nowPlayingMovieDataSource;
    private MovieDataService movieDataService;
    private Application application;
    private MutableLiveData<NowPlayingMovieDataSource> nowPlayingDataSourceMutable;


    public NowPlayingDataSourceFactory(MovieDataService movieDataService, Application application)
    {
        this.movieDataService = movieDataService;
        this.application = application;
        nowPlayingDataSourceMutable = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create()
    {
        nowPlayingMovieDataSource = new NowPlayingMovieDataSource(movieDataService, application);
        nowPlayingDataSourceMutable.postValue(nowPlayingMovieDataSource);
        return nowPlayingMovieDataSource;
    }

    public MutableLiveData<NowPlayingMovieDataSource> getNowPlayingDataSourceMutable()
    {
        return nowPlayingDataSourceMutable;
    }
}
