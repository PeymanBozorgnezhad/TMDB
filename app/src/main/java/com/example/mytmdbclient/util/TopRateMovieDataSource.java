package com.example.mytmdbclient.util;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.mytmdbclient.BuildConfig;
import com.example.mytmdbclient.model.MoviesTopRate;
import com.example.mytmdbclient.model.TopRateDBResponse;
import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.view.MainActivity;
import com.example.mytmdbclient.view.TopRateMainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TopRateMovieDataSource extends PageKeyedDataSource<Long, MoviesTopRate>
{

    private MovieDataService movieDataService;
    private Application application;

    public TopRateMovieDataSource(MovieDataService movieDataService, Application application)
    {
        this.movieDataService = movieDataService;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, MoviesTopRate> callback)
    {
        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org"
                        , Toast.LENGTH_SHORT).show();
            }


            MovieDataService dataService = RetrofitInstance.getService();
            Call<TopRateDBResponse> call = dataService.getTopRateMoviesWithPaging(MainActivity.Api_Key, 1);

            call.enqueue(new Callback<TopRateDBResponse>()
            {
                @Override
                public void onResponse(Call<TopRateDBResponse> call, Response<TopRateDBResponse> response)
                {
                    TopRateDBResponse rateDBResponse = response.body();

                    if (rateDBResponse != null && rateDBResponse.getMoviesTopRates() != null)
                    {
                        ArrayList<MoviesTopRate> moviesTopRates = new ArrayList<>();

                        moviesTopRates = (ArrayList<MoviesTopRate>) rateDBResponse.getMoviesTopRates();

                        callback.onResult(moviesTopRates, null, (long) 2);

                        if (TopRateMainActivity.swipeTopRateLayout.isRefreshing())
                        {
                            TopRateMainActivity.swipeTopRateLayout.setRefreshing(false);
                        }
                    }


                }

                @Override
                public void onFailure(Call<TopRateDBResponse> call, Throwable t)
                {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(application.getApplicationContext(), "Error fetching trailer data", Toast.LENGTH_SHORT)
                            .show();

                    TopRateMainActivity.rltvPullDownTopRate.setVisibility(View.VISIBLE);
                    TopRateMainActivity.recyclerVwTopRate.setVisibility(View.GONE);
                    if (TopRateMainActivity.swipeTopRateLayout.isRefreshing())
                    {
                        TopRateMainActivity.swipeTopRateLayout.setRefreshing(false);
                    }
                    TopRateMainActivity.swipeTopRateLayout.setRefreshing(false);

                }
            });


        } catch (Exception e)
        {
            Log.d("Error", e.getMessage());
            Toast.makeText(application.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, MoviesTopRate> callback)
    {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, MoviesTopRate> callback)
    {

        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org", Toast.LENGTH_SHORT)
                        .show();
            }

            MovieDataService dataService = RetrofitInstance.getService();

            Call<TopRateDBResponse> call = dataService.getTopRateMoviesWithPaging(MainActivity.Api_Key, params.key);

            call.enqueue(new Callback<TopRateDBResponse>()
            {
                @Override
                public void onResponse(Call<TopRateDBResponse> call, Response<TopRateDBResponse> response)
                {
                    TopRateDBResponse rateDBResponse = response.body();

                    if (rateDBResponse != null && rateDBResponse.getMoviesTopRates() != null)
                    {
                        ArrayList<MoviesTopRate> moviesTopRates = new ArrayList<>();
                        moviesTopRates = (ArrayList<MoviesTopRate>) rateDBResponse.getMoviesTopRates();

                        callback.onResult(moviesTopRates, params.key + 1);

                    }
                }

                @Override
                public void onFailure(Call<TopRateDBResponse> call, Throwable t)
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
