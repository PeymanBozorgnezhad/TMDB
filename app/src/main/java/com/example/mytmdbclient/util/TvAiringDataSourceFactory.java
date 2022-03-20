package com.example.mytmdbclient.util;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.mytmdbclient.service.MovieDataService;

public class TvAiringDataSourceFactory extends DataSource.Factory
{

    private TvAiringDataSource tvAiringDataSource;
    private MovieDataService movieDataService;
    private Application application;
    private MutableLiveData<TvAiringDataSource> tvAiringMutable;


    public TvAiringDataSourceFactory(MovieDataService movieDataService, Application application)
    {
        this.movieDataService = movieDataService;
        this.application = application;
        tvAiringMutable = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create()
    {
        tvAiringDataSource = new TvAiringDataSource(movieDataService, application);
        tvAiringMutable.postValue(tvAiringDataSource);

        return tvAiringDataSource;
    }

    public MutableLiveData<TvAiringDataSource> getTvAiringMutable()
    {
        return tvAiringMutable;
    }
}
