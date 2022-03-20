package com.example.mytmdbclient.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.util.TopRateDataSourceFactory;
import com.example.mytmdbclient.util.TopRateMovieDataSource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TopRateMainViewModel extends AndroidViewModel
{
    private Executor executor;
    private LiveData<TopRateMovieDataSource> topRateMovieDataSourceLiveData;
    private LiveData<PagedList<MoviesTopRate>> tpRatePagedListLiveData;


    public TopRateMainViewModel(@NonNull Application application)
    {
        super(application);


        MovieDataService movieDataService = RetrofitInstance.getService();
        TopRateDataSourceFactory factory = new TopRateDataSourceFactory(movieDataService, application);

        topRateMovieDataSourceLiveData = factory.getTopRateMutable();


        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)//by pagedList detect mishe..tedad itemhaie k dar other pages qarar migiran
                .setPrefetchDistance(4)
                .build();

        executor = Executors.newFixedThreadPool(5);


        tpRatePagedListLiveData = (new LivePagedListBuilder<Long, MoviesTopRate>(factory, config))
                .setFetchExecutor(executor)
                .build();
    }


    public LiveData<PagedList<MoviesTopRate>> getTpRatePagedListLiveData()
    {
        return tpRatePagedListLiveData;
    }


}
