package com.example.mytmdbclient.util;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.mytmdbclient.BuildConfig;
import com.example.mytmdbclient.model.Movie;
import com.example.mytmdbclient.model.MovieDBResponse;
import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.view.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Movie>
{

    private MovieDataService movieDataService;
    private Application application;

    public MovieDataSource(MovieDataService movieDataService, Application application)
    {
        this.movieDataService = movieDataService;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Movie> callback)//LoadInitialCallback<Long, Movie> callback==>>in yek pasokh dahnde has
    {

        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org", Toast.LENGTH_SHORT)
                        .show();
            }


            movieDataService = RetrofitInstance.getService();
            Call<MovieDBResponse> call = movieDataService.getPopularMoviesWihPaging(MainActivity.Api_Key/*application.getApplicationContext().getString(R.string.api_key)*/,
                    1);//chon qrar has dar dakhel in method (data k qarar hastan dar page one ma load beshan )

            call.enqueue(new Callback<MovieDBResponse>()
            {
                @Override
                public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response)
                {

                    MovieDBResponse movieDBResponse = response.body();

                    if (movieDBResponse != null && movieDBResponse.getResults() != null)
                    {

                        ArrayList<Movie> movieArrayList = new ArrayList<>();

                        movieArrayList = (ArrayList<Movie>) movieDBResponse.getResults();

                        callback.onResult(movieArrayList, null, (long) 2);

                        if (MainActivity.swipeRefreshLayout.isRefreshing())
                        {
                            MainActivity.swipeRefreshLayout.setRefreshing(false);
                        }


                    }

                }

                @Override
                public void onFailure(Call<MovieDBResponse> call, Throwable t)
                {

                    Log.d("Error", t.getMessage());
                    Toast.makeText(application.getApplicationContext(), "Error fetching trailer data", Toast.LENGTH_SHORT).show();


                    MainActivity.rltvPullDown.setVisibility(View.VISIBLE);
                    MainActivity.recyclerView.setVisibility(View.GONE);
                    if (MainActivity.swipeRefreshLayout.isRefreshing())
                    {
                        MainActivity.swipeRefreshLayout.setRefreshing(false);
                    }
                    MainActivity.swipeRefreshLayout.setRefreshing(false);
                }
            });
        } catch (Exception e)
        {
            Log.d("Error", e.getMessage());
            Toast.makeText(application.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback)
    {
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Movie> callback)
    {
        //LoadCallback<Long, Movie> callback==>>chon az class daroni invoke mishe type callback ro final dar nazar migirim

        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org", Toast.LENGTH_SHORT)
                        .show();
            }


            movieDataService = RetrofitInstance.getService();

            Call<MovieDBResponse> call = movieDataService.getPopularMoviesWihPaging(MainActivity.Api_Key/*application.getApplicationContext().getString(R.string.api_key)*/,
                    params.key);

            call.enqueue(new Callback<MovieDBResponse>()
            {
                @Override
                public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response)
                {

                    MovieDBResponse movieDBResponse = response.body();

                    if (movieDBResponse != null && movieDBResponse.getResults() != null)
                    {

                        ArrayList<Movie> movieArrayList = new ArrayList<>();
                        movieArrayList = (ArrayList<Movie>) movieDBResponse.getResults();

                        callback.onResult(movieArrayList, params.key + 1);

                    }
                }

                @Override
                public void onFailure(Call<MovieDBResponse> call, Throwable t)
                {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(application.getApplicationContext(), "Error fetching trailer data", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e)
        {
            Log.d("Error", e.getMessage());
            Toast.makeText(application.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }


    }
}
