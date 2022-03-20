package com.example.mytmdbclient.util;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.mytmdbclient.BuildConfig;
import com.example.mytmdbclient.model.UpComing;
import com.example.mytmdbclient.model.UpComingDBResponse;
import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.view.MainActivity;
import com.example.mytmdbclient.view.UpComingMainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingDataSource extends PageKeyedDataSource<Long, UpComing>
{

    private MovieDataService movieDataService;
    private Application application;

    public UpComingDataSource(MovieDataService movieDataService, Application application)
    {
        this.movieDataService = movieDataService;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, UpComing> callback)
    {

        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org"
                        , Toast.LENGTH_SHORT).show();
            }


            MovieDataService dataService = RetrofitInstance.getService();
            Call<UpComingDBResponse> call = dataService.getUpComingMoviesWithPaging(MainActivity.Api_Key, 1);

            call.enqueue(new Callback<UpComingDBResponse>()
            {
                @Override
                public void onResponse(Call<UpComingDBResponse> call, Response<UpComingDBResponse> response)
                {
                    UpComingDBResponse upComingDBResponse = response.body();

                    if (upComingDBResponse != null && upComingDBResponse.getResults() != null)
                    {
                        ArrayList<UpComing> upComingArrayList = new ArrayList<>();

                        upComingArrayList = (ArrayList<UpComing>) upComingDBResponse.getResults();

                        callback.onResult(upComingArrayList, null, (long) 2);

                        if (UpComingMainActivity.swipeUpcomingLayout.isRefreshing())
                        {
                            UpComingMainActivity.swipeUpcomingLayout.setRefreshing(false);
                        }
                    }


                }

                @Override
                public void onFailure(Call<UpComingDBResponse> call, Throwable t)
                {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(application.getApplicationContext(), "Error fetching trailer data", Toast.LENGTH_SHORT)
                            .show();

                    UpComingMainActivity.rltvPullDownUpComing.setVisibility(View.VISIBLE);
                    UpComingMainActivity.recyclerVwUpComing.setVisibility(View.GONE);
                    if (UpComingMainActivity.swipeUpcomingLayout.isRefreshing())
                    {
                        UpComingMainActivity.swipeUpcomingLayout.setRefreshing(false);
                    }
                    UpComingMainActivity.swipeUpcomingLayout.setRefreshing(false);

                }
            });


        } catch (Exception e)
        {
            Log.d("Error", e.getMessage());
            Toast.makeText(application.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, UpComing> callback)
    {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, UpComing> callback)
    {

        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org", Toast.LENGTH_SHORT)
                        .show();
            }

            MovieDataService dataService = RetrofitInstance.getService();

            Call<UpComingDBResponse> call = dataService.getUpComingMoviesWithPaging(MainActivity.Api_Key, params.key);

            call.enqueue(new Callback<UpComingDBResponse>()
            {
                @Override
                public void onResponse(Call<UpComingDBResponse> call, Response<UpComingDBResponse> response)
                {
                    UpComingDBResponse upComingDBResponse = response.body();

                    if (upComingDBResponse != null && upComingDBResponse.getResults() != null)
                    {
                        ArrayList<UpComing> upComingArrayList = new ArrayList<>();
                        upComingArrayList = (ArrayList<UpComing>) upComingDBResponse.getResults();

                        callback.onResult(upComingArrayList, params.key + 1);

                    }
                }

                @Override
                public void onFailure(Call<UpComingDBResponse> call, Throwable t)
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
