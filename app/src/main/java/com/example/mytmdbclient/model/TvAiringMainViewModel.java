package com.example.mytmdbclient.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.util.TvAiringDataSource;
import com.example.mytmdbclient.util.TvAiringDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TvAiringMainViewModel extends AndroidViewModel
{
    private Executor executor;
    private LiveData<TvAiringDataSource> tvAiringDataSourceLiveData;
    private LiveData<PagedList<TvAiringToday>> tvAiringPagedListLiveData;

    public TvAiringMainViewModel(@NonNull Application application)
    {
        super(application);

        MovieDataService movieDataService = RetrofitInstance.getService();
        TvAiringDataSourceFactory factory = new TvAiringDataSourceFactory(movieDataService, application);

        tvAiringDataSourceLiveData = factory.getTvAiringMutable();


        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)//by pagedList detect mishe..tedad itemhaie k dar other pages qarar migiran
                .setPrefetchDistance(4)
                .build();

        executor = Executors.newFixedThreadPool(5);


        tvAiringPagedListLiveData = (new LivePagedListBuilder<Long, TvAiringToday>(factory, config))
                .setFetchExecutor(executor)
                .build();

    }


    public LiveData<PagedList<TvAiringToday>> getTvAiringPagedListLiveData()
    {
        return tvAiringPagedListLiveData;
    }
}
