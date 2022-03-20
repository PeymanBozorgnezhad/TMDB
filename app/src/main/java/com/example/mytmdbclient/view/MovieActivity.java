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
import com.example.mytmdbclient.databinding.ActivityMovieBinding;
import com.example.mytmdbclient.model.Cast;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.model.MainActivityViewModel;
import com.example.mytmdbclient.model.Movie;
import com.example.mytmdbclient.model.Trailer;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity

{

    private Movie movie;
    /*  private Snackbar snack;*/

    private ActivityMovieBinding activityMovieBinding;
    private MainActivityViewModel activityViewModel;
    private ArrayList<Genre> genreArrayList = new ArrayList<>();
    private ArrayList<Trailer> trailerArrayList = new ArrayList<>();
    private ArrayList<Cast> castArrayList = new ArrayList<>();

    private TrailerAdapter trailerAdapter;
    private CastAdapter castAdapter;
    //private Object movieId;

    //private String image;

   /* private ImageView movieImage;
    private TextView movieTitle, movieSynopsis, movieRating, movieReleaseDate;*/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);



        /*getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP|ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_SHOW_CUSTOM);*/


        activityMovieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        setUpToolBarPopular();


        //activityMovieBinding.setLifecycleOwner(this);

        //RecyclerViewActivity.progressDialog = RecyclerViewActivity.createProgressDialog(MovieActivity.this);
        //snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);

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
                    activityMovieBinding.setGenre(getGenres(movie.getGenreIds()));
                }
            });

            //Log.i("TAG", "*******************" + movie.getGenreIds());

            /*Toast.makeText(getApplicationContext(), movie.getOriginalTitle(), Toast.LENGTH_LONG).show();

            Log.i("TAG", "*******************" + movie.getOriginalTitle());*/

            //getSupportActionBar().setTitle(movie.getTitle());

            activityMovieBinding.setMovie(movie);



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
            Toast.makeText(MovieActivity.this, "No API Data", Toast.LENGTH_SHORT).show();
        }


    }

    private void setUpToolBarPopular()
    {
        //Toolbar toolbar = findViewById(R.id.toolbar_Popular);
        Toolbar toolbar = activityMovieBinding.toolbarPopular;
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

        CollapsingToolbarLayout collapsingPopular = activityMovieBinding.cTLPopular;
        collapsingPopular.setTitle(" ");

        AppBarLayout appBarLayoutPopular = activityMovieBinding.appBarPopular;
        appBarLayoutPopular.setExpanded(true);


        appBarLayoutPopular.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener()
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
                    collapsingPopular.setTitle(movie.getTitle()/*getString(R.string.Movies_Details)*/);
                    isShow = true;
                } else if (isShow)
                {
                    collapsingPopular.setTitle(" ");
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
        RecyclerView recyclerViewPopularTrailer = activityMovieBinding.secondaryLayout.RvPopularTrailer;

        if (!isNetworkConnected())
        {
            recyclerViewPopularTrailer.setVisibility(View.GONE);
        } else if (isNetworkConnected() && trailerArrayList.isEmpty())
        {
            recyclerViewPopularTrailer.setVisibility(View.GONE);
            TextView txtVwNoTrailers = activityMovieBinding.secondaryLayout.labelTrailers;
            txtVwNoTrailers.setText(getResources().getString(R.string.no_available_trailers));

        }


        //recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(MovieActivity.this));
        recyclerViewPopularTrailer.setLayoutManager(new LinearLayoutManager(MovieActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewPopularTrailer.setHasFixedSize(true);

        trailerAdapter = new TrailerAdapter(trailerArrayList, MovieActivity.this);
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
        RecyclerView recyclerViewPopularCast = activityMovieBinding.secondaryLayout.RvPopularCast;

        if (!isNetworkConnected())
        {
            recyclerViewPopularCast.setVisibility(View.GONE);
        }

        recyclerViewPopularCast.setLayoutManager(new LinearLayoutManager(MovieActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewPopularCast.setHasFixedSize(true);

        castAdapter = new CastAdapter(MovieActivity.this, castArrayList);
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





    /* private void FindViews()
    {
        //TODO : ImageView
        //movieImage = (ImageView) findViewById(R.id.imgLargeMovie);
        movieImage = activityMovieBinding.imgLargeMovie;

        //TODO : TextView
        //movieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        movieTitle = activityMovieBinding.secondaryLayout.tvMovieTitle;

        //movieSynopsis = (TextView) findViewById(R.id.tvPlotsynopsis);
        movieSynopsis = activityMovieBinding.secondaryLayout.tvPlotsynopsis;

        //movieRating = (TextView) findViewById(R.id.tvMovieRating);
        movieRating = activityMovieBinding.secondaryLayout.tvMovieRating;

        //movieReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        movieReleaseDate = activityMovieBinding.secondaryLayout.tvReleaseDate;

    }
*/


    /*public Object getMovieId()
    {
        activityViewModel.getMovieIdLiveData().observe(this, new Observer<Integer>()
        {
            @Override
            public void onChanged(Integer integer)
            {
                integer = movie.getId();
            }
        });
        return movieId;
    }*/

    /*private void showSnackBar(String text)
    {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show();
    }*/

}
