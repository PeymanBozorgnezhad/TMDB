package com.example.mytmdbclient.view;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mytmdbclient.Adapter.MovieAdapter;
import com.example.mytmdbclient.BuildConfig;
import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ActivityMainBinding;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.model.MainActivityViewModel;
import com.example.mytmdbclient.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity

{

    private PagedList<Movie> moviesPagedList;//==>>/*private ArrayList<Movie> movieArrayList;*/
    private ArrayList<Genre> genreArrayList = new ArrayList<>();
    public static RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    public static SwipeRefreshLayout swipeRefreshLayout;
    public static RelativeLayout rltvPullDown;
    private MainActivityViewModel activityViewModel;
    public static ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers handlers;
    public static final String Api_Key = BuildConfig.ApiKey;
    public static final String Language = "en-US";
    /*public static final String Language = "fa-IR";*/


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TMDB Popular Movies Today");
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);




        handlers = new MainActivityClickHandlers(this);
        activityMainBinding.setClickHandlers(handlers);


        activityViewModel = ViewModelProviders
                .of(this).get(MainActivityViewModel.class);

        FindViews();


        rltvPullDown.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);


        getPopularMovies();
        GenreMoviesHolder();


        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorRefresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getPopularMovies();
                /*if (swipeRefreshLayout.isRefreshing())
                {
                    swipeRefreshLayout.setRefreshing(false);
                }*/
            }
        });


    }




    public class MainActivityClickHandlers
    {
        private Context mContext;

        public MainActivityClickHandlers(Context mContext)
        {
            this.mContext = mContext;
        }

        public void onRltvPullDownClicked(View view)
        {
            Toast.makeText(mContext, "Pull Down Is Pressed", Toast.LENGTH_LONG).show();
            getPopularMovies();
        }

    }

    private Object GenreMoviesHolder()
    {
        activityViewModel.getGenreLiveData().observe(this, new Observer<List<Genre>>()
        {
            @Override
            public void onChanged(List<Genre> genres)
            {

                genreArrayList = (ArrayList<Genre>) genres;

            }
        });
        return genreArrayList;
    }


    public Object getPopularMovies()
    {


        activityViewModel.getPagedListLiveData().observe(this, new Observer<PagedList<Movie>>()
        {
            @Override
            public void onChanged(PagedList<Movie> movies)
            {
                moviesPagedList = movies;
                showOnRecyclerView();


            }
        });



      /*  activityViewModel.getListLiveData().observe(this, new Observer<List<Movie>>()
        {
            @Override
            public void onChanged(List<Movie> movies)
            {
                moviesPagedList = (ArrayList<Movie>) movies;

                *//*for (Movie m : movies)
                {
                    Log.i("TAG", "onChanged: " + m.getOriginalTitle());
                }*//*

                showOnRecyclerView();

            }
        });*/






       /* MovieDataService movieDataService = RetrofitInstance.getService();

        Call<MovieDBResponse> call = movieDataService.getPopularMovies(this.getString(R.string.api_key));*/

        /*call.enqueue(new Callback<MovieDBResponse>()
        {


            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response)
            {

                rltvPullDown.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                recyclerView.setVisibility(View.VISIBLE);

                MovieDBResponse movieDBResponse = response.body();


                if (movieDBResponse != null && movieDBResponse.getResults() != null)
                {


                    movies = (ArrayList<Movie>) movieDBResponse.getResults();
                    showOnRecyclerView();


                }


            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t)
            {

                Toast.makeText(MainActivity.this, "Error ):", Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
                rltvPullDown.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);


            }
        });
*/

        return moviesPagedList; //==>> /*return movieArrayList;*/
    }

    private void showOnRecyclerView()
    {
        //recyclerView = (RecyclerView) findViewById(R.id.recyclerVw);
        recyclerView = activityMainBinding.recyclerVw;
        movieAdapter = new MovieAdapter(this/*, movieArrayList*/);
        movieAdapter.submitList(moviesPagedList);
        recyclerView.setHasFixedSize(true);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {

            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else
        {


            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));


        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();


    }

    private void FindViews()
    {
        //recyclerView = (RecyclerView) findViewById(R.id.recyclerVw);
        recyclerView = activityMainBinding.recyclerVw;

        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout = activityMainBinding.swipeLayout;

        //rltvPullDown = (RelativeLayout) findViewById(R.id.rltvPullDown);
        rltvPullDown = activityMainBinding.rltvPullDown;
    }



}
