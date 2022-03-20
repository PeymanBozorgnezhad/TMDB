package com.example.mytmdbclient.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.util.UpComingDataSource;
import com.example.mytmdbclient.util.UpComingDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UpComingMainViewModel extends AndroidViewModel
{

    private Executor executor;
    private LiveData<UpComingDataSource> upComingDataSourceLiveData;
    private LiveData<PagedList<UpComing>> upComingPagedListLiveData;


    public UpComingMainViewModel(@NonNull Application application)
    {
        super(application);

        MovieDataService movieDataService = RetrofitInstance.getService();
        UpComingDataSourceFactory factory = new UpComingDataSourceFactory(movieDataService, application);

        upComingDataSourceLiveData = factory.getUpComingMutable();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)//by pagedList detect mishe..tedad itemhaie k dar other pages qarar migiran
                .setPrefetchDistance(4)
                .build();

        executor = Executors.newFixedThreadPool(5);

        upComingPagedListLiveData = (new LivePagedListBuilder<Long, UpComing>(factory, config))
                .setFetchExecutor(executor)
                .build();


    }

    public LiveData<PagedList<UpComing>> getUpComingPagedListLiveData()
    {
        return upComingPagedListLiveData;
    }
}
