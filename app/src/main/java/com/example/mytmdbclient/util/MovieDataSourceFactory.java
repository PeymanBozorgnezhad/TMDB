package com.example.mytmdbclient.util;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.mytmdbclient.service.MovieDataService;

public class MovieDataSourceFactory extends DataSource.Factory
{

    private MovieDataSource movieDataSource;
    private MovieDataService movieDataService;
    private Application application;
    private MutableLiveData<MovieDataSource> mutableLiveData;

    public MovieDataSourceFactory(MovieDataService movieDataService, Application application)
    {
        this.movieDataService = movieDataService;
        this.application = application;
        mutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource create()//output az type DataSource has
    {
        movieDataSource = new MovieDataSource(movieDataService, application);
        mutableLiveData.postValue(movieDataSource);//send b view model

        return movieDataSource;
    }


    //method getter ro invoke konim b eazaye mutableLiveData


    public MutableLiveData<MovieDataSource> getMutableLiveData()
    {
        return mutableLiveData;
    }
}
