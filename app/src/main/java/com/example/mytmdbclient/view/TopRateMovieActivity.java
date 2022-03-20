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
import com.example.mytmdbclient.databinding.ActivityTopRateMovieBinding;
import com.example.mytmdbclient.model.Cast;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.model.MainActivityViewModel;
import com.example.mytmdbclient.model.MoviesTopRate;
import com.example.mytmdbclient.model.Trailer;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class TopRateMovieActivity extends AppCompatActivity
{

    private MoviesTopRate moviesTopRate;
    private ActivityTopRateMovieBinding activityTopRateMovieBinding;
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
        setContentView(R.layout.activity_top_rate_movie);


        activityTopRateMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_top_rate_movie);

        setUpToolBarTopRate();


        // RecyclerViewActivity.progressDialog = RecyclerViewActivity.createProgressDialog(TopRateMovieActivity.this);

        activityViewModel = ViewModelProviders.of(this)
                .get(MainActivityViewModel.class);




        Intent intent = getIntent();

        if (intent.hasExtra("movie_topRate"))
        {
            moviesTopRate = getIntent().getParcelableExtra("movie_topRate");

            getTrailerTopRate();
            getCastTopRate();

            activityViewModel.getGenreLiveData().observe(this, new Observer<List<Genre>>()
            {
                @Override
                public void onChanged(List<Genre> genreList)
                {

                    genreArrayList = (ArrayList<Genre>) genreList;
                    activityTopRateMovieBinding.setGenre(getGenres(moviesTopRate.getGenreIds()));
                }
            });

            activityTopRateMovieBinding.setMovieTopRate(moviesTopRate);
        } else
        {
            Toast.makeText(TopRateMovieActivity.this, "No API Data", Toast.LENGTH_SHORT).show();
        }


    }

    private void setUpToolBarTopRate()
    {

        //Toolbar toolbar = findViewById(R.id.toolbar_TopRate);
        Toolbar toolbar = activityTopRateMovieBinding.toolbarTopRate;
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
        final CollapsingToolbarLayout collapsingTopRate = activityTopRateMovieBinding.cTLTopRate;
        collapsingTopRate.setTitle(" ");

        AppBarLayout appBarTopRate = activityTopRateMovieBinding.appBarTopRate;
        appBarTopRate.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
        {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offsetVertical)
            {

                if (scrollRange == -1)
                {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + offsetVertical == 0)
                {
                    collapsingTopRate.setTitle(moviesTopRate.getTitle());
                    isShow = true;
                } else if (isShow)
                {
                    collapsingTopRate.setTitle(" ");
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

    public Object getTrailerTopRate()
    {

        /*if (isNetworkConnected())
        {*/

        int movie_id = getIntent().getExtras().getInt("movie_id", -1);
        //TODO : New
        activityViewModel.setMovieId(moviesTopRate.getId());


        Log.d("TAG", movie_id + "=========================");

        activityViewModel.getTrailerLiveData().observe(this, new Observer<List<Trailer>>()
        {
            @Override
            public void onChanged(List<Trailer> trailers)
            {
                trailerArrayList = (ArrayList<Trailer>) trailers;
                showOnRecyclerTopRateTrailer();
                    /*if (trailers != null && !trailers.isEmpty())
                    {
                        RecyclerViewActivity.progressDialog.dismiss();
                    }*/


            }
        });
        // }

        return trailerArrayList;
    }

    private void showOnRecyclerTopRateTrailer()
    {
        RecyclerView recyclerViewTopRateTrailer = activityTopRateMovieBinding.secondaryLayout.RvTopRateTrailer;

        if (!isNetworkConnected())
        {
            recyclerViewTopRateTrailer.setVisibility(View.GONE);
        } else if (isNetworkConnected() && trailerArrayList.isEmpty())
        {
            recyclerViewTopRateTrailer.setVisibility(View.GONE);
            TextView txtVwNoTrailers = activityTopRateMovieBinding.secondaryLayout.labelTrailers;
            txtVwNoTrailers.setText(getResources().getString(R.string.no_available_trailers));

        }


        //recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(MovieActivity.this));
        recyclerViewTopRateTrailer.setLayoutManager(new LinearLayoutManager(TopRateMovieActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewTopRateTrailer.setHasFixedSize(true);

        trailerAdapter = new TrailerAdapter(trailerArrayList, TopRateMovieActivity.this);
        recyclerViewTopRateTrailer.setAdapter(trailerAdapter);
        recyclerViewTopRateTrailer.smoothScrollToPosition(0);
        trailerAdapter.notifyDataSetChanged();

    }

    public Object getCastTopRate()
    {
        /*if (isNetworkConnected())
        {*/
        int movie_id = getIntent().getExtras().getInt("movie_id", -1);
        activityViewModel.setMovieId(moviesTopRate.getId());

        Log.d("TAG", movie_id + "=========================");

        activityViewModel.getCastListLiveData().observe(this, new Observer<List<Cast>>()
        {
            @Override
            public void onChanged(List<Cast> casts)
            {
                castArrayList = (ArrayList<Cast>) casts;
                showOnRecyclerTopRateCast();
                    /*if (casts != null && !casts.isEmpty())
                    {
                        RecyclerViewActivity.progressDialog.dismiss();
                    }*/
            }
        });
        // }

        return castArrayList;
    }

    private void showOnRecyclerTopRateCast()
    {
        RecyclerView recyclerViewTopRateCast = activityTopRateMovieBinding.secondaryLayout.RvTopRateCast;

        if (!isNetworkConnected())
        {
            recyclerViewTopRateCast.setVisibility(View.GONE);
        }

        recyclerViewTopRateCast.setLayoutManager(new LinearLayoutManager(TopRateMovieActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewTopRateCast.setHasFixedSize(true);

        castAdapter = new CastAdapter(TopRateMovieActivity.this, castArrayList);
        recyclerViewTopRateCast.setAdapter(castAdapter);
        recyclerViewTopRateCast.smoothScrollToPosition(0);
        castAdapter.notifyDataSetChanged();


    }


    //TODO : New Method
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
