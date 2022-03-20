package com.example.mytmdbclient.util;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.mytmdbclient.service.MovieDataService;

public class TopRateDataSourceFactory extends DataSource.Factory
{

    private TopRateMovieDataSource topRateMovieDataSource;
    private MovieDataService movieDataService;
    private Application application;
    private MutableLiveData<TopRateMovieDataSource> topRateMutable;


    public TopRateDataSourceFactory(MovieDataService movieDataService, Application application)
    {
        this.movieDataService = movieDataService;
        this.application = application;
        topRateMutable = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create()
    {
        topRateMovieDataSource = new TopRateMovieDataSource(movieDataService, application);
        topRateMutable.postValue(topRateMovieDataSource);
        return topRateMovieDataSource;
    }


    public MutableLiveData<TopRateMovieDataSource> getTopRateMutable()
    {
        return topRateMutable;
    }
}
