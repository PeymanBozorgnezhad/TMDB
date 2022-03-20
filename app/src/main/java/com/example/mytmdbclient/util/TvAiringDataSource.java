package com.example.mytmdbclient.util;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.mytmdbclient.BuildConfig;
import com.example.mytmdbclient.model.TvAiringToday;
import com.example.mytmdbclient.model.TvAiringTodayDBResponse;
import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.view.MainActivity;
import com.example.mytmdbclient.view.TvAiringMainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



    public class TvAiringDataSource extends PageKeyedDataSource<Long, TvAiringToday>
    {

        private MovieDataService movieDataService;
        private Application application;

        public TvAiringDataSource(MovieDataService movieDataService, Application application)
        {
            this.movieDataService = movieDataService;
            this.application = application;
        }

        @Override
        public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, TvAiringToday> callback)
        {
            try
            {
                if (BuildConfig.ApiKey.isEmpty())
                {
                    Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org"
                            , Toast.LENGTH_SHORT).show();
                }


                MovieDataService dataService = RetrofitInstance.getService();
                Call<TvAiringTodayDBResponse> call = dataService.getTvAiringWithPaging(MainActivity.Api_Key, 1);

                call.enqueue(new Callback<TvAiringTodayDBResponse>()
                {
                    @Override
                    public void onResponse(Call<TvAiringTodayDBResponse> call, Response<TvAiringTodayDBResponse> response)
                    {
                        TvAiringTodayDBResponse tvAiringDBResponse = response.body();

                        if (tvAiringDBResponse != null && tvAiringDBResponse.getResults() != null)
                        {
                            ArrayList<TvAiringToday> tvAiringTodayArrayList = new ArrayList<>();

                            tvAiringTodayArrayList = (ArrayList<TvAiringToday>) tvAiringDBResponse.getResults();

                            callback.onResult(tvAiringTodayArrayList, null, (long) 2);

                            if (TvAiringMainActivity.swipeTvAiringLayout.isRefreshing())
                            {
                                TvAiringMainActivity.swipeTvAiringLayout.setRefreshing(false);
                            }
                        }


                    }

                    @Override
                    public void onFailure(Call<TvAiringTodayDBResponse> call, Throwable t)
                    {
                        Log.d("Error", t.getMessage());
                        Toast.makeText(application.getApplicationContext(), "Error fetching trailer data", Toast.LENGTH_SHORT)
                                .show();

                        TvAiringMainActivity.rltvPullDownTvAiring.setVisibility(View.VISIBLE);
                        TvAiringMainActivity.recyclerVwTvAiring.setVisibility(View.GONE);
                        if (TvAiringMainActivity.swipeTvAiringLayout.isRefreshing())
                        {
                            TvAiringMainActivity.swipeTvAiringLayout.setRefreshing(false);
                        }
                        TvAiringMainActivity.swipeTvAiringLayout.setRefreshing(false);

                    }
                });


            } catch (Exception e)
            {
                Log.d("Error", e.getMessage());
                Toast.makeText(application.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, TvAiringToday> callback)
        {

        }

        @Override
        public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, TvAiringToday> callback)
        {

            try
            {
                if (BuildConfig.ApiKey.isEmpty())
                {
                    Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org", Toast.LENGTH_SHORT)
                            .show();
                }

                MovieDataService dataService = RetrofitInstance.getService();

                Call<TvAiringTodayDBResponse> call = dataService.getTvAiringWithPaging(MainActivity.Api_Key, params.key);

                call.enqueue(new Callback<TvAiringTodayDBResponse>()
                {
                    @Override
                    public void onResponse(Call<TvAiringTodayDBResponse> call, Response<TvAiringTodayDBResponse> response)
                    {
                        TvAiringTodayDBResponse tvAiringDBResponse = response.body();

                        if (tvAiringDBResponse != null && tvAiringDBResponse.getResults() != null)
                        {
                            ArrayList<TvAiringToday> tvAiringTodays = new ArrayList<>();
                            tvAiringTodays = (ArrayList<TvAiringToday>) tvAiringDBResponse.getResults();

                            callback.onResult(tvAiringTodays, params.key + 1);

                        }
                    }

                    @Override
                    public void onFailure(Call<TvAiringTodayDBResponse> call, Throwable t)
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


