package com.example.mytmdbclient.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytmdbclient.Adapter.CastAdapter;
import com.example.mytmdbclient.Adapter.TrailerAdapter;
import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ActivityScrollingBinding;
import com.example.mytmdbclient.model.Cast;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.model.MainActivityViewModel;
import com.example.mytmdbclient.model.Movie;
import com.example.mytmdbclient.model.Trailer;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity
{
    private Movie movie;
    private ActivityScrollingBinding activityScrollingBinding;

    private MainActivityViewModel activityViewModel;
    private ArrayList<Genre> genreArrayList = new ArrayList<>();
    private ArrayList<Trailer> trailerArrayList = new ArrayList<>();
    private ArrayList<Cast> castArrayList = new ArrayList<>();

    private TrailerAdapter trailerAdapter;
    private CastAdapter castAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        activityScrollingBinding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling);
        setUpToolBarPopScroll();


        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);


        // FindViews();

        Intent intent = getIntent();

        if (intent.hasExtra("movie"))
        {

            movie = getIntent().getParcelableExtra("movie");

            getTrailerPopulars();
            getCastPopulars();


            activityViewModel.getGenreLiveData().observe(this, new Observer<List<Genre>>()
            {
                @Override
                public void onChanged(List<Genre> genreList)
                {
                    genreArrayList = (ArrayList<Genre>) genreList;
                    activityScrollingBinding.setGenre(getGenres(movie.getGenreIds()));
                }
            });

            //Log.i("TAG", "*******************" + movie.getGenreIds());

            /*Toast.makeText(getApplicationContext(), movie.getOriginalTitle(), Toast.LENGTH_LONG).show();

            Log.i("TAG", "*******************" + movie.getOriginalTitle());*/

            //getSupportActionBar().setTitle(movie.getTitle());

            activityScrollingBinding.setMovie(movie);



            /*  TODO : Command

             *//*image = movie.getPosterPath();

            String path = "https://image.tmdb.org/t/p/w500" + image;*//*


         *//*Glide.with(this)
                    .load(path)
                    .placeholder(R.drawable.loading)
                    .into(movieImage);*//*


         *//* movieTitle.setText(movie.getTitle());
            movieSynopsis.setText(movie.getOverview());
            movieRating.setText(Double.toString(movie.getVoteAverage()));
            movieReleaseDate.setText(movie.getReleaseDate());*/



        } else
        {
            Toast.makeText(ScrollingActivity.this, "No API Data", Toast.LENGTH_SHORT).show();
        }





        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void setUpToolBarPopScroll()
    {
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Popular_scroll);
        Toolbar toolbar = activityScrollingBinding.toolbarPopularScroll;
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);

            initCollapsingToolBar();
        }
    }

    private void initCollapsingToolBar()
    {

        CollapsingToolbarLayout collapsingPopScroll = activityScrollingBinding.cTLPopularScroll;
        collapsingPopScroll.setTitle(" ");

        AppBarLayout appBarLayoutPopScroll = activityScrollingBinding.appBar;
        appBarLayoutPopScroll.setExpanded(true);


        appBarLayoutPopScroll.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
        {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset)
            {

                if (scrollRange == -1)
                {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0)
                {
                    collapsingPopScroll.setTitle(movie.getTitle()/*getString(R.string.Movies_Details)*/);
                    isShow = true;
                } else if (isShow)
                {
                    collapsingPopScroll.setTitle(" ");
                    isShow = false;
                }


            }
        });

    }

    private String getGenres(List<Integer> genreIds)
    {
        List<String> movieGenres = new ArrayList<>();
        for (Integer genreId : genreIds)
        {
            for (Genre genre : genreArrayList)
            {
                if (genre.getId().equals(genreId))
                {
                    movieGenres.add(genre.getName());
                    break;
                }
            }
        }
        return TextUtils.join(", ", movieGenres);
    }

    public Object getTrailerPopulars()
    {
        /*if (isNetworkConnected())
        {*/

        int movie_id = getIntent().getExtras().getInt("movie_id", -1);
        activityViewModel.setMovieId(movie.getId());
        //getMovieId();


        Log.d("TAG", movie_id + "=========================");

        activityViewModel.getTrailerLiveData().observe(this, new Observer<List<Trailer>>()
        {
            @Override
            public void onChanged(List<Trailer> trailers)
            {
                trailerArrayList = (ArrayList<Trailer>) trailers;
                showOnRecyclerPopularTrailer();
                    /*if (trailers != null && !trailers.isEmpty())
                    {
                        RecyclerViewActivity.progressDialog.dismiss();
                    }*/
            }

        });
        //  }


        return trailerArrayList;
    }

    private void showOnRecyclerPopularTrailer()
    {
        RecyclerView recyclerViewPopularTrailer = activityScrollingBinding.secondaryLayoutScrolling.RvPopularTrailer;

        if (!isNetworkConnected())
        {
            recyclerViewPopularTrailer.setVisibility(View.GONE);
        } else if (isNetworkConnected() && trailerArrayList.isEmpty())
        {
            recyclerViewPopularTrailer.setVisibility(View.GONE);
            TextView txtVwNoTrailers = activityScrollingBinding.secondaryLayoutScrolling.labelTrailers;
            txtVwNoTrailers.setText(getResources().getString(R.string.no_available_trailers));

        }


        //recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(MovieActivity.this));
        recyclerViewPopularTrailer.setLayoutManager(new LinearLayoutManager(ScrollingActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewPopularTrailer.setHasFixedSize(true);

        trailerAdapter = new TrailerAdapter(trailerArrayList, ScrollingActivity.this);
        recyclerViewPopularTrailer.setAdapter(trailerAdapter);
        recyclerViewPopularTrailer.smoothScrollToPosition(0);
        trailerAdapter.notifyDataSetChanged();

    }


    public Object getCastPopulars()
    {
        /*if (isNetworkConnected())
        {*/
        int movie_id = getIntent().getExtras().getInt("movie_id", -1);
        activityViewModel.setMovieId(movie.getId());

        Log.d("TAG", movie_id + "=========================");

        activityViewModel.getCastListLiveData().observe(this, new Observer<List<Cast>>()
        {
            @Override
            public void onChanged(List<Cast> casts)
            {
                castArrayList = (ArrayList<Cast>) casts;
                showOnRecyclerPopularCast();
                    /*if (casts != null && !casts.isEmpty())
                    {
                        RecyclerViewActivity.progressDialog.dismiss();
                    }*/
            }
        });

        //}
        return castArrayList;
    }

    private void showOnRecyclerPopularCast()
    {
        RecyclerView recyclerViewPopularCast = activityScrollingBinding.secondaryLayoutScrolling.RvPopularCast;

        if (!isNetworkConnected())
        {
            recyclerViewPopularCast.setVisibility(View.GONE);
        }

        recyclerViewPopularCast.setLayoutManager(new LinearLayoutManager(ScrollingActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewPopularCast.setHasFixedSize(true);

        castAdapter = new CastAdapter(ScrollingActivity.this, castArrayList);
        recyclerViewPopularCast.setAdapter(castAdapter);
        recyclerViewPopularCast.smoothScrollToPosition(0);
        castAdapter.notifyDataSetChanged();


    }


    private boolean isNetworkConnected()
    {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null)
        {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        return (networkInfo != null && networkInfo.isConnected());
    }

}
