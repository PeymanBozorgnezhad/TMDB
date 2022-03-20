package com.example.mytmdbclient.util;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.mytmdbclient.service.MovieDataService;

public class UpComingDataSourceFactory extends DataSource.Factory
{

    private UpComingDataSource upComingDataSource;
    private MovieDataService movieDataService;
    private Application application;
    private MutableLiveData<UpComingDataSource> upComingMutable;

    public UpComingDataSourceFactory(MovieDataService movieDataService, Application application)
    {
        this.movieDataService = movieDataService;
        this.application = application;
        upComingMutable = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create()
    {
        upComingDataSource = new UpComingDataSource(movieDataService, application);
        upComingMutable.postValue(upComingDataSource);
        return upComingDataSource;
    }

    public MutableLiveData<UpComingDataSource> getUpComingMutable()
    {
        return upComingMutable;
    }
}
