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
import com.example.mytmdbclient.databinding.ActivityUpComingMovieBinding;
import com.example.mytmdbclient.model.Cast;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.model.MainActivityViewModel;
import com.example.mytmdbclient.model.Trailer;
import com.example.mytmdbclient.model.UpComing;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class UpComingMovieActivity extends AppCompatActivity
{

    private UpComing upComing;
    private ActivityUpComingMovieBinding activityUpComingMovieBinding;
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
        setContentView(R.layout.activity_up_coming_movie);


        activityUpComingMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_up_coming_movie);

        setUpToolBarUpComing();


        // RecyclerViewActivity.progressDialog = RecyclerViewActivity.createProgressDialog(UpComingMovieActivity.this);

        activityViewModel = ViewModelProviders.of(this)
                .get(MainActivityViewModel.class);


        Intent intent = getIntent();

        if (intent.hasExtra("movie_upComing"))
        {
            upComing = getIntent().getParcelableExtra("movie_upComing");

            getTrailerUpComing();
            getCastUpComing();

            activityViewModel.getGenreLiveData().observe(this, new Observer<List<Genre>>()
            {
                @Override
                public void onChanged(List<Genre> genreList)
                {

                    genreArrayList = (ArrayList<Genre>) genreList;
                    activityUpComingMovieBinding.setGenre(getGenres(upComing.getGenreIds()));
                }
            });

            activityUpComingMovieBinding.setUpComing(upComing);
        } else
        {
            Toast.makeText(UpComingMovieActivity.this, "No API Data", Toast.LENGTH_SHORT).show();
        }


    }

    public void setUpToolBarUpComing()
    {

        //Toolbar toolbar = findViewById(R.id.toolbar_upComing);
        Toolbar toolbar = activityUpComingMovieBinding.toolbarUpComing;
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
        final CollapsingToolbarLayout collapsingUpComing = activityUpComingMovieBinding.cTLUpComing;
        collapsingUpComing.setTitle(" ");

        AppBarLayout appBarUpComing = activityUpComingMovieBinding.appBarUpComing;
        appBarUpComing.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
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
                    collapsingUpComing.setTitle(upComing.getTitle());
                    isShow = true;
                } else if (isShow)
                {
                    collapsingUpComing.setTitle(" ");
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

    public Object getTrailerUpComing()
    {
        /*if (isNetworkConnected())
        {*/
        int movie_id = getIntent().getExtras().getInt("movie_id", -1);
        //TODO : New
        activityViewModel.setMovieId(upComing.getId());


        Log.d("TAG", movie_id + "=========================");

        activityViewModel.getTrailerLiveData().observe(this, new Observer<List<Trailer>>()
        {
            @Override
            public void onChanged(List<Trailer> trailers)
            {
                trailerArrayList = (ArrayList<Trailer>) trailers;
                showOnRecyclerUpComingTrailer();
                    /*if (trailers != null && !trailers.isEmpty())
                    {
                        RecyclerViewActivity.progressDialog.dismiss();
                    }*/
            }
        });
        //}

        return trailerArrayList;
    }

    private void showOnRecyclerUpComingTrailer()
    {
        RecyclerView recyclerViewUpComingTrailer = activityUpComingMovieBinding.secondaryLayout.RvUpComingTrailer;

        if (!isNetworkConnected())
        {
            recyclerViewUpComingTrailer.setVisibility(View.GONE);
        } else if (isNetworkConnected() && trailerArrayList.isEmpty())
        {
            recyclerViewUpComingTrailer.setVisibility(View.GONE);
            TextView txtVwNoTrailers = activityUpComingMovieBinding.secondaryLayout.labelTrailers;
            txtVwNoTrailers.setText(getResources().getString(R.string.no_available_trailers));

        }


        //recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(MovieActivity.this));
        recyclerViewUpComingTrailer.setLayoutManager(new LinearLayoutManager(UpComingMovieActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewUpComingTrailer.setHasFixedSize(true);

        trailerAdapter = new TrailerAdapter(trailerArrayList, UpComingMovieActivity.this);
        recyclerViewUpComingTrailer.setAdapter(trailerAdapter);
        recyclerViewUpComingTrailer.smoothScrollToPosition(0);
        trailerAdapter.notifyDataSetChanged();

    }


    public Object getCastUpComing()
    {
        /*if (isNetworkConnected())
        {*/
        int movie_id = getIntent().getExtras().getInt("movie_id", -1);
        activityViewModel.setMovieId(upComing.getId());

        Log.d("TAG", movie_id + "=========================");

        activityViewModel.getCastListLiveData().observe(this, new Observer<List<Cast>>()
        {
            @Override
            public void onChanged(List<Cast> casts)
            {
                castArrayList = (ArrayList<Cast>) casts;
                showOnRecyclerUpComingCast();
                    /*if (casts != null && !casts.isEmpty())
                    {
                        RecyclerViewActivity.progressDialog.dismiss();
                    }*/
            }
        });
        //}

        return castArrayList;
    }

    private void showOnRecyclerUpComingCast()
    {
        RecyclerView recyclerViewUpComingCast = activityUpComingMovieBinding.secondaryLayout.RvUpComingCast;

        if (!isNetworkConnected())
        {
            recyclerViewUpComingCast.setVisibility(View.GONE);
        }


        recyclerViewUpComingCast.setLayoutManager(new LinearLayoutManager(UpComingMovieActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewUpComingCast.setHasFixedSize(true);

        castAdapter = new CastAdapter(UpComingMovieActivity.this, castArrayList);
        recyclerViewUpComingCast.setAdapter(castAdapter);
        recyclerViewUpComingCast.smoothScrollToPosition(0);
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
