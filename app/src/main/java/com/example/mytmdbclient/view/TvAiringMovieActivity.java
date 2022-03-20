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
import com.example.mytmdbclient.databinding.ActivityTvAiringMovieBinding;
import com.example.mytmdbclient.model.Cast;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.model.MainActivityViewModel;
import com.example.mytmdbclient.model.Trailer;
import com.example.mytmdbclient.model.TvAiringToday;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class TvAiringMovieActivity extends AppCompatActivity
{

    private TvAiringToday tvAiringToday;
    /*  private Snackbar snack;*/

    private ActivityTvAiringMovieBinding activityTvAiringMovieBinding;
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
        setContentView(R.layout.activity_tv_airing_movie);


        activityTvAiringMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_airing_movie);

        setUpToolBarTvAiring();


        //RecyclerViewActivity.progressDialog = RecyclerViewActivity.createProgressDialog(TvAiringMovieActivity.this);

        activityViewModel = ViewModelProviders.of(this)
                .get(MainActivityViewModel.class);


        Intent intent = getIntent();

        if (intent.hasExtra("TvAiring"))
        {
            tvAiringToday = getIntent().getParcelableExtra("TvAiring");

            getTrailerTvAiring();
            getCastTvAiring();


            activityViewModel.getGenreLiveData().observe(this, new Observer<List<Genre>>()
            {
                @Override
                public void onChanged(List<Genre> genreList)
                {

                    genreArrayList = (ArrayList<Genre>) genreList;
                    activityTvAiringMovieBinding.setGenre(getGenres(tvAiringToday.getGenreIds()));
                }
            });

            activityTvAiringMovieBinding.setTvAiring(tvAiringToday);
        } else
        {
            Toast.makeText(TvAiringMovieActivity.this, "No API Data", Toast.LENGTH_SHORT).show();
        }


    }

    private void setUpToolBarTvAiring()
    {

        //Toolbar toolbar = findViewById(R.id.toolbar_TvAiring);
        Toolbar toolbar = activityTvAiringMovieBinding.toolbarTvAiring;
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
        final CollapsingToolbarLayout collapsingTvAiring = activityTvAiringMovieBinding.cTLTvAiring;
        collapsingTvAiring.setTitle(" ");

        AppBarLayout appBarTvAiring = activityTvAiringMovieBinding.appBarTvAiring;
        appBarTvAiring.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
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
                    collapsingTvAiring.setTitle(tvAiringToday.getName());
                    isShow = true;
                } else if (isShow)
                {
                    // display an empty string when toolbar is expanded
                    collapsingTvAiring.setTitle(" ");
                    isShow = false;
                }

            }
        });


    }

    public Object getTrailerTvAiring()
    {


        /*if (isNetworkConnected())
        {*/
        int tv_id = getIntent().getExtras().getInt("TvAiring", -1);
        //TODO : New
        activityViewModel.setTvId(tvAiringToday.getId());


        Log.d("TAG", tv_id + "=========================");

        activityViewModel.getTrailerLiveData().observe(this, new Observer<List<Trailer>>()
        {
            @Override
            public void onChanged(List<Trailer> trailers)
            {
                trailerArrayList = (ArrayList<Trailer>) trailers;
                showOnRecyclerTvAiringTrailer();
                    /*if (trailers != null && !trailers.isEmpty())
                    {
                        RecyclerViewActivity.progressDialog.dismiss();
                    }*/
            }
        });

        //}

        return trailerArrayList;
    }

    private void showOnRecyclerTvAiringTrailer()
    {
        RecyclerView recyclerViewTvAiringTrailer = activityTvAiringMovieBinding.secondaryLayout.RvTvAiringTrailer;

        if (!isNetworkConnected())
        {
            recyclerViewTvAiringTrailer.setVisibility(View.GONE);
        } else if (isNetworkConnected() && trailerArrayList.isEmpty())
        {
            recyclerViewTvAiringTrailer.setVisibility(View.GONE);
            TextView txtVwNoTrailers = activityTvAiringMovieBinding.secondaryLayout.labelTrailers;
            txtVwNoTrailers.setText(getResources().getString(R.string.no_available_trailers));

        }


        //recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(MovieActivity.this));
        recyclerViewTvAiringTrailer.setLayoutManager(new LinearLayoutManager(TvAiringMovieActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewTvAiringTrailer.setHasFixedSize(true);

        trailerAdapter = new TrailerAdapter(trailerArrayList, TvAiringMovieActivity.this);
        recyclerViewTvAiringTrailer.setAdapter(trailerAdapter);
        recyclerViewTvAiringTrailer.smoothScrollToPosition(0);
        trailerAdapter.notifyDataSetChanged();
    }


    public Object getCastTvAiring()
    {
         /*if (isNetworkConnected())
        {*/
        int tv_id = getIntent().getExtras().getInt("TvAiring", -1);
        activityViewModel.setTvId(tvAiringToday.getId());

        Log.d("TAG", tv_id + "=========================");

        activityViewModel.getCastListLiveData().observe(this, new Observer<List<Cast>>()
        {
            @Override
            public void onChanged(List<Cast> casts)
            {
                castArrayList = (ArrayList<Cast>) casts;
                showOnRecyclerTvAiringCast();
                    /*if (casts != null && !casts.isEmpty())
                    {
                        RecyclerViewActivity.progressDialog.dismiss();
                    }*/
            }
        });
        //}

        return castArrayList;
    }

    private void showOnRecyclerTvAiringCast()
    {
        RecyclerView recyclerViewTvAiringCast = activityTvAiringMovieBinding.secondaryLayout.RvTvAiringCast;

        if (!isNetworkConnected())
        {
            recyclerViewTvAiringCast.setVisibility(View.GONE);
        }


        recyclerViewTvAiringCast.setLayoutManager(new LinearLayoutManager(TvAiringMovieActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewTvAiringCast.setHasFixedSize(true);

        castAdapter = new CastAdapter(TvAiringMovieActivity.this, castArrayList);
        recyclerViewTvAiringCast.setAdapter(castAdapter);
        recyclerViewTvAiringCast.smoothScrollToPosition(0);
        castAdapter.notifyDataSetChanged();
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
