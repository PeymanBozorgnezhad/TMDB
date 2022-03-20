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
import com.example.mytmdbclient.databinding.ActivityNowPlayingMovieBinding;
import com.example.mytmdbclient.model.Cast;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.model.MainActivityViewModel;
import com.example.mytmdbclient.model.NowPlaying;
import com.example.mytmdbclient.model.Trailer;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class NowPlayingMovieActivity extends AppCompatActivity
{

    private NowPlaying nowPlaying;
    private ActivityNowPlayingMovieBinding activityNowPlayingMovieBinding;
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
        setContentView(R.layout.activity_now_playing_movie);


        activityNowPlayingMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_now_playing_movie);

        setUpToolBarNowPlaying();


        //RecyclerViewActivity.progressDialog = RecyclerViewActivity.createProgressDialog(NowPlayingMovieActivity.this);

        activityViewModel = ViewModelProviders.of(this)
                .get(MainActivityViewModel.class);


        Intent intent = getIntent();

        if (intent.hasExtra("movie_nowPlaying"))
        {
            nowPlaying = getIntent().getParcelableExtra("movie_nowPlaying");

            getTrailerNowPlaying();
            getCastNowPlaying();


            activityViewModel.getGenreLiveData().observe(this, new Observer<List<Genre>>()
            {
                @Override
                public void onChanged(List<Genre> genreList)
                {

                    genreArrayList = (ArrayList<Genre>) genreList;
                    activityNowPlayingMovieBinding.setGenre(getGenres(nowPlaying.getGenreIds()));
                }
            });

            activityNowPlayingMovieBinding.setNowPlaying(nowPlaying);
        } else
        {
            Toast.makeText(NowPlayingMovieActivity.this, "No API Data", Toast.LENGTH_SHORT).show();
        }


    }

    private void setUpToolBarNowPlaying()
    {

        //Toolbar toolbar = findViewById(R.id.toolbar_NowPlaying);
        Toolbar toolbar = activityNowPlayingMovieBinding.toolbarNowPlaying;
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
        final CollapsingToolbarLayout collapsingNowPlaying = activityNowPlayingMovieBinding.cTLNowPlaying;
        collapsingNowPlaying.setTitle(" ");

        AppBarLayout appBarNowPlaying = activityNowPlayingMovieBinding.appBarNowPlaying;
        appBarNowPlaying.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
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
                // verify if the toolbar is completely collapsed and set the movie name as the tit

                if (scrollRange + offsetVertical == 0)
                {
                    collapsingNowPlaying.setTitle(nowPlaying.getTitle());
                    isShow = true;
                } else if (isShow)
                {
                    // display an empty string when toolbar is expanded
                    collapsingNowPlaying.setTitle(" ");
                    isShow = false;
                }

            }
        });


    }

    //TODO : New
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


    public Object getTrailerNowPlaying()
    {
        /*if (isNetworkConnected())
        {*/
        int movie_id = getIntent().getExtras().getInt("movie_id", -1);
        //TODO : New
        activityViewModel.setMovieId(nowPlaying.getId());


        Log.d("TAG", movie_id + "=========================");

        activityViewModel.getTrailerLiveData().observe(this, new Observer<List<Trailer>>()
        {
            @Override
            public void onChanged(List<Trailer> trailers)
            {
                trailerArrayList = (ArrayList<Trailer>) trailers;
                showOnRecyclerNowPlayingTrailer();
                    /*if (trailers != null && !trailers.isEmpty())
                    {
                        RecyclerViewActivity.progressDialog.dismiss();
                    }*/
            }
        });

        //}
        return trailerArrayList;
    }

    private void showOnRecyclerNowPlayingTrailer()
    {
        RecyclerView recyclerViewNowPlayingTrailer = activityNowPlayingMovieBinding.secondaryLayout.RvNowPlayingTrailers;

        if (!isNetworkConnected())
        {
            recyclerViewNowPlayingTrailer.setVisibility(View.GONE);
        } else if (isNetworkConnected() && trailerArrayList.isEmpty())
        {
            recyclerViewNowPlayingTrailer.setVisibility(View.GONE);
            TextView txtVwNoTrailers = activityNowPlayingMovieBinding.secondaryLayout.labelTrailers;
            txtVwNoTrailers.setText(getResources().getString(R.string.no_available_trailers));

        }


        //recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(MovieActivity.this));
        recyclerViewNowPlayingTrailer.setLayoutManager(new LinearLayoutManager(NowPlayingMovieActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewNowPlayingTrailer.setHasFixedSize(true);

        trailerAdapter = new TrailerAdapter(trailerArrayList, NowPlayingMovieActivity.this);
        recyclerViewNowPlayingTrailer.setAdapter(trailerAdapter);
        recyclerViewNowPlayingTrailer.smoothScrollToPosition(0);
        trailerAdapter.notifyDataSetChanged();

    }


    public Object getCastNowPlaying()
    {
        /*if (isNetworkConnected())
        {*/
        int movie_id = getIntent().getExtras().getInt("movie_id", -1);
        activityViewModel.setMovieId(nowPlaying.getId());

        Log.d("TAG", movie_id + "=========================");

        activityViewModel.getCastListLiveData().observe(this, new Observer<List<Cast>>()
        {
            @Override
            public void onChanged(List<Cast> casts)
            {
                castArrayList = (ArrayList<Cast>) casts;
                showOnRecyclerNowPlayingCast();
                    /*if (casts != null && !casts.isEmpty())
                    {
                        RecyclerViewActivity.progressDialog.dismiss();
                    }*/
            }
        });
        //}

        return castArrayList;
    }

    private void showOnRecyclerNowPlayingCast()
    {
        RecyclerView recyclerViewNowPlayingCast = activityNowPlayingMovieBinding.secondaryLayout.RvNowPlayingCast;

        if (!isNetworkConnected())
        {
            recyclerViewNowPlayingCast.setVisibility(View.GONE);
        }


        recyclerViewNowPlayingCast.setLayoutManager(new LinearLayoutManager(NowPlayingMovieActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewNowPlayingCast.setHasFixedSize(true);

        castAdapter = new CastAdapter(NowPlayingMovieActivity.this, castArrayList);
        recyclerViewNowPlayingCast.setAdapter(castAdapter);
        recyclerViewNowPlayingCast.smoothScrollToPosition(0);
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
