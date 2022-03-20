package com.example.mytmdbclient.util;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.mytmdbclient.BuildConfig;
import com.example.mytmdbclient.model.NowPlaying;
import com.example.mytmdbclient.model.NowPlayingDBResponse;
import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.view.MainActivity;
import com.example.mytmdbclient.view.NowPlayingMainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NowPlayingMovieDataSource extends PageKeyedDataSource<Long, NowPlaying>
{

    private MovieDataService movieDataService;
    private Application application;

    public NowPlayingMovieDataSource(MovieDataService movieDataService, Application application)
    {
        this.movieDataService = movieDataService;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, NowPlaying> callback)
    {
        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org"
                        , Toast.LENGTH_SHORT).show();
            }


            MovieDataService dataService = RetrofitInstance.getService();
            Call<NowPlayingDBResponse> call = dataService.getNowPlayingMoviesWithPaging(MainActivity.Api_Key, 1);

            call.enqueue(new Callback<NowPlayingDBResponse>()
            {
                @Override
                public void onResponse(Call<NowPlayingDBResponse> call, Response<NowPlayingDBResponse> response)
                {
                    NowPlayingDBResponse playingDBResponse = response.body();

                    if (playingDBResponse != null && playingDBResponse.getResults() != null)
                    {
                        ArrayList<NowPlaying> nowPlayingArrayList = new ArrayList<>();

                        nowPlayingArrayList = (ArrayList<NowPlaying>) playingDBResponse.getResults();

                        callback.onResult(nowPlayingArrayList, null, (long) 2);

                        if (NowPlayingMainActivity.swipeNowPlayingLayout.isRefreshing())
                        {
                            NowPlayingMainActivity.swipeNowPlayingLayout.setRefreshing(false);
                        }
                    }
                }

                @Override
                public void onFailure(Call<NowPlayingDBResponse> call, Throwable t)
                {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(application.getApplicationContext(), "Error fetching trailer data", Toast.LENGTH_SHORT)
                            .show();

                    NowPlayingMainActivity.rltvPullDownNowPlaying.setVisibility(View.VISIBLE);
                    NowPlayingMainActivity.recyclerVwNowPlaying.setVisibility(View.GONE);
                    if (NowPlayingMainActivity.swipeNowPlayingLayout.isRefreshing())
                    {
                        NowPlayingMainActivity.swipeNowPlayingLayout.setRefreshing(false);
                    }
                    NowPlayingMainActivity.swipeNowPlayingLayout.setRefreshing(false);


                }
            });


        } catch (Exception e)
        {
            Log.d("Error", e.getMessage());
            Toast.makeText(application.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadBefore
            (@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, NowPlaying> callback)
    {

    }

    @Override
    public void loadAfter
            (@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, NowPlaying> callback)
    {


        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org", Toast.LENGTH_SHORT)
                        .show();
            }

            MovieDataService dataService = RetrofitInstance.getService();

            Call<NowPlayingDBResponse> call = dataService.getNowPlayingMoviesWithPaging(MainActivity.Api_Key, params.key);

            call.enqueue(new Callback<NowPlayingDBResponse>()
            {
                @Override
                public void onResponse(Call<NowPlayingDBResponse> call, Response<NowPlayingDBResponse> response)
                {
                    NowPlayingDBResponse playingDBResponse = response.body();

                    if (playingDBResponse != null && playingDBResponse.getResults() != null)
                    {
                        ArrayList<NowPlaying> nowPlayingArrayList = new ArrayList<>();
                        nowPlayingArrayList = (ArrayList<NowPlaying>) playingDBResponse.getResults();

                        callback.onResult(nowPlayingArrayList, params.key + 1);

                    }
                }

                @Override
                public void onFailure(Call<NowPlayingDBResponse> call, Throwable t)
                {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(application.getApplicationContext(), "Error fetching trailer data", Toast.LENGTH_SHORT)
                            .show();

                }
            });


        } catch (Exception e)
        {
            Log.d("Error", e.getMessage());
            Toast.makeText(application.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
