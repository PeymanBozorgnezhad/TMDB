package com.example.mytmdbclient.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.util.NowPlayingDataSourceFactory;
import com.example.mytmdbclient.util.NowPlayingMovieDataSource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NowPlayingMainViewModel extends AndroidViewModel
{

    private Executor executor;
    private LiveData<NowPlayingMovieDataSource> nowPlayingMovieDataSourceLiveData;
    private LiveData<PagedList<NowPlaying>> nowPlayingPagedListLiveData;


    public NowPlayingMainViewModel(@NonNull Application application)
    {
        super(application);
        MovieDataService movieDataService = RetrofitInstance.getService();
        NowPlayingDataSourceFactory factory = new NowPlayingDataSourceFactory(movieDataService, application);

        nowPlayingMovieDataSourceLiveData = factory.getNowPlayingDataSourceMutable();


        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)//by pagedList detect mishe..tedad itemhaie k dar other pages qarar migiran
                .setPrefetchDistance(4)
                .build();

        executor = Executors.newFixedThreadPool(5);

        nowPlayingPagedListLiveData = (new LivePagedListBuilder<Long, NowPlaying>(factory, config))
                .setFetchExecutor(executor)
                .build();

    }

    public LiveData<PagedList<NowPlaying>> getNowPlayingPagedListLiveData()
    {
        return nowPlayingPagedListLiveData;
    }
}
