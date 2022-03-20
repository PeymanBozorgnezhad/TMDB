package com.example.mytmdbclient.util;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.mytmdbclient.BuildConfig;
import com.example.mytmdbclient.model.Cast;
import com.example.mytmdbclient.model.CastDBResponse;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.model.GenreDBResponse;
import com.example.mytmdbclient.model.Movie;
import com.example.mytmdbclient.model.MovieDBResponse;
import com.example.mytmdbclient.model.MoviesTopRate;
import com.example.mytmdbclient.model.NowPlaying;
import com.example.mytmdbclient.model.NowPlayingDBResponse;
import com.example.mytmdbclient.model.SimilarMovies;
import com.example.mytmdbclient.model.TopRateDBResponse;
import com.example.mytmdbclient.model.Trailer;
import com.example.mytmdbclient.model.TrailerDBResponse;
import com.example.mytmdbclient.model.TvAiringToday;
import com.example.mytmdbclient.model.TvAiringTodayDBResponse;
import com.example.mytmdbclient.model.UpComing;
import com.example.mytmdbclient.model.UpComingDBResponse;
import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieRepository
{
    private ArrayList<NowPlaying> nowPlayingArrayList = new ArrayList<>();
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private ArrayList<MoviesTopRate> topRateArrayList = new ArrayList<>();
    private ArrayList<Genre> genres = new ArrayList<>();
    private ArrayList<UpComing> upComingArrayList = new ArrayList<>();
    private ArrayList<Trailer> trailerArrayList = new ArrayList<>();
    private int mMovieId;
    private int mTvId;
    private ArrayList<Cast> castArrayList = new ArrayList<>();
    private ArrayList<SimilarMovies> similarMovies = new ArrayList<>();

    //TODO : Tv
    private ArrayList<TvAiringToday> tvAiringTodayArrayList = new ArrayList<>();


    //bad az anjam in kar nobat b create class mutable LiveData class ba instance az list<Movie> mirese
    private MutableLiveData<List<NowPlaying>> nowPlayingMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> listMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MoviesTopRate>> mutableTopRate = new MutableLiveData<>();
    private MutableLiveData<List<Genre>> genreMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<UpComing>> upComingMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailerMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> movieIdMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Cast>> castMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<SimilarMovies>> similarMutableLiveData = new MutableLiveData<>();
    //TODO : Tv mutable
    private MutableLiveData<List<TvAiringToday>> TvAiringMutableLiveData = new MutableLiveData<>();


    /*private MutableLiveData<Integer> mutableLiveDataMovieId = new MutableLiveData<>();*/
    //bad az in bayad be ezaye hamin listMutableLiveData tabe getter ro ijad konim

    private Application application;


    public MovieRepository(Application application)
    {
        this.application = application;
    }

    public MutableLiveData<List<Movie>> getListMutableLiveData()
    {

        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org", Toast.LENGTH_SHORT)
                        .show();
            }


            MovieDataService movieDataService = RetrofitInstance.getService();

            Call<MovieDBResponse> call = movieDataService.getPopularMovies(MainActivity.Api_Key/*application.getApplicationContext().getString(R.string.api_key)*/);

            call.enqueue(new Callback<MovieDBResponse>()
            {


                @Override
                public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response)
                {

                    MovieDBResponse movieDBResponse = response.body();


                    if (movieDBResponse != null && movieDBResponse.getResults() != null)
                    {

                        movieArrayList = (ArrayList<Movie>) movieDBResponse.getResults();


                        //dar in part bayad biaym ba uses setValues meqdare morde nazar ro set konim
                        listMutableLiveData.setValue(movieArrayList);


                        //setValues b sorat hamzaman
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


        return listMutableLiveData;
    }

    public MutableLiveData<List<MoviesTopRate>> getMutableTopRate()
    {

        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org", Toast.LENGTH_SHORT)
                        .show();
            }


            MovieDataService dataService = RetrofitInstance.getService();

            Call<TopRateDBResponse> call = dataService.getTopRateMovies(MainActivity.Api_Key/*application.getApplicationContext().getString(R.string.api_key)*/);

            call.enqueue(new Callback<TopRateDBResponse>()
            {
                @Override
                public void onResponse(Call<TopRateDBResponse> call, Response<TopRateDBResponse> response)
                {
                    TopRateDBResponse rateDBResponse = response.body();

                    if (rateDBResponse != null && rateDBResponse.getMoviesTopRates() != null)
                    {

                        topRateArrayList = (ArrayList<MoviesTopRate>) rateDBResponse.getMoviesTopRates();
                        mutableTopRate.setValue(topRateArrayList);


                    }

                }

                @Override
                public void onFailure(Call<TopRateDBResponse> call, Throwable t)
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
        return mutableTopRate;


    }

    public MutableLiveData<List<Genre>> getGenreMutableLiveData()
    {

        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org"
                        , Toast.LENGTH_SHORT).show();
            }

            MovieDataService dataService = RetrofitInstance.getService();
            Call<GenreDBResponse> call = dataService.getGenresIdMovies(MainActivity.Api_Key, MainActivity.Language);

            call.enqueue(new Callback<GenreDBResponse>()
            {
                @Override
                public void onResponse(Call<GenreDBResponse> call, Response<GenreDBResponse> response)
                {
                    GenreDBResponse dbResponse = response.body();

                    if (dbResponse != null && dbResponse.getGenres() != null)
                    {

                        genres = (ArrayList<Genre>) dbResponse.getGenres();
                        genreMutableLiveData.setValue(genres);

                    }
                }

                @Override
                public void onFailure(Call<GenreDBResponse> call, Throwable t)
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


        return genreMutableLiveData;
    }


    public MutableLiveData<List<UpComing>> getUpComingMutableLiveData()
    {

        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org"
                        , Toast.LENGTH_SHORT).show();
            }


            MovieDataService dataService = RetrofitInstance.getService();
            Call<UpComingDBResponse> call = dataService.getUpComingMovies(MainActivity.Api_Key);

            call.enqueue(new Callback<UpComingDBResponse>()
            {
                @Override
                public void onResponse(Call<UpComingDBResponse> call, Response<UpComingDBResponse> response)
                {
                    UpComingDBResponse dbResponse = response.body();

                    if (dbResponse != null && dbResponse.getResults() != null)
                    {
                        upComingArrayList = (ArrayList<UpComing>) dbResponse.getResults();
                        upComingMutableLiveData.setValue(upComingArrayList);
                    }
                }

                @Override
                public void onFailure(Call<UpComingDBResponse> call, Throwable t)
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


        return upComingMutableLiveData;
    }

    public MutableLiveData<List<NowPlaying>> getNowPlayingMutableLiveData()
    {

        try
        {
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org"
                        , Toast.LENGTH_SHORT).show();
            }


            MovieDataService dataService = RetrofitInstance.getService();
            Call<NowPlayingDBResponse> call = dataService.getNowPlayingMovies(MainActivity.Api_Key);

            call.enqueue(new Callback<NowPlayingDBResponse>()
            {
                @Override
                public void onResponse(Call<NowPlayingDBResponse> call, Response<NowPlayingDBResponse> response)
                {

                    NowPlayingDBResponse dbResponse = response.body();

                    if (dbResponse != null && dbResponse.getResults() != null)
                    {
                        nowPlayingArrayList = (ArrayList<NowPlaying>) dbResponse.getResults();
                        nowPlayingMutableLiveData.setValue(nowPlayingArrayList);
                    }


                }

                @Override
                public void onFailure(Call<NowPlayingDBResponse> call, Throwable t)
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


        return nowPlayingMutableLiveData;
    }

    public MutableLiveData<List<Trailer>> getTrailerMutableLiveData(int movieId/*MutableLiveData<Integer> movieID*/)
    {
        try
        {
            mMovieId = movieId;
            // movieIdMutableLiveData = movieID;
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org",
                        Toast.LENGTH_SHORT).show();
            }
            MovieDataService dataService = RetrofitInstance.getService();


            Call<TrailerDBResponse> call = dataService.getMoviesTrailer(mMovieId, MainActivity.Api_Key);

            call.enqueue(new Callback<TrailerDBResponse>()
            {
                @Override
                public void onResponse(Call<TrailerDBResponse> call, Response<TrailerDBResponse> response)
                {
                    TrailerDBResponse dbResponse = response.body();

                    if (dbResponse != null && dbResponse.getResults() != null)
                    {
                        trailerArrayList = (ArrayList<Trailer>) dbResponse.getResults();
                        trailerMutableLiveData.setValue(trailerArrayList);

                    }

                }

                @Override
                public void onFailure(Call<TrailerDBResponse> call, Throwable t)
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


        return trailerMutableLiveData;
    }

    public MutableLiveData<List<Cast>> getCastMutableLiveData(int movieId)
    {
        try
        {
            mMovieId = movieId;

            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org",
                        Toast.LENGTH_SHORT).show();
            }


            MovieDataService dataService = RetrofitInstance.getService();
            Call<CastDBResponse> call = dataService.getCastMovies(mMovieId, MainActivity.Api_Key);

            call.enqueue(new Callback<CastDBResponse>()
            {
                @Override
                public void onResponse(Call<CastDBResponse> call, Response<CastDBResponse> response)
                {

                    CastDBResponse dbResponse = response.body();
                    if (dbResponse != null && dbResponse.getCast() != null)
                    {
                        castArrayList = (ArrayList<Cast>) dbResponse.getCast();
                        castMutableLiveData.setValue(castArrayList);
                    }

                }

                @Override
                public void onFailure(Call<CastDBResponse> call, Throwable t)
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


        return castMutableLiveData;
    }

    public MutableLiveData<List<SimilarMovies>> getSimilarMutableLiveData(int movieId)
    {
        return similarMutableLiveData;
    }

    /* public MutableLiveData<Integer> getMovieIdMutableLiveData()
    {
        movieIdMutableLiveData.setValue(mMovieId);
        return movieIdMutableLiveData;
    }*/

    //TODO : Tv
    public MutableLiveData<List<TvAiringToday>> getTvAiringMutableLiveData()
    {
        try
        {

            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org", Toast.LENGTH_SHORT)
                        .show();
            }

            MovieDataService dataService = RetrofitInstance.getService();

            Call<TvAiringTodayDBResponse> call = dataService.getTvAiring(MainActivity.Api_Key);

            call.enqueue(new Callback<TvAiringTodayDBResponse>()
            {
                @Override
                public void onResponse(Call<TvAiringTodayDBResponse> call, Response<TvAiringTodayDBResponse> response)
                {
                    TvAiringTodayDBResponse dbResponse = response.body();

                    if (dbResponse != null && dbResponse.getResults() != null)
                    {
                        tvAiringTodayArrayList = (ArrayList<TvAiringToday>) dbResponse.getResults();
                        TvAiringMutableLiveData.setValue(tvAiringTodayArrayList);
                    }
                }

                @Override
                public void onFailure(Call<TvAiringTodayDBResponse> call, Throwable t)
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


        return TvAiringMutableLiveData;
    }

    public MutableLiveData<List<Trailer>> getTrailerTvMutableLiveData(int tvId/*MutableLiveData<Integer> movieID*/)
    {
        try
        {
            mTvId = tvId;
            // movieIdMutableLiveData = movieID;
            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org",
                        Toast.LENGTH_SHORT).show();
            }
            MovieDataService dataService = RetrofitInstance.getService();


            Call<TrailerDBResponse> call = dataService.getTvAiringTrailer(mTvId, MainActivity.Api_Key);

            call.enqueue(new Callback<TrailerDBResponse>()
            {
                @Override
                public void onResponse(Call<TrailerDBResponse> call, Response<TrailerDBResponse> response)
                {
                    TrailerDBResponse dbResponse = response.body();

                    if (dbResponse != null && dbResponse.getResults() != null)
                    {
                        trailerArrayList = (ArrayList<Trailer>) dbResponse.getResults();
                        trailerMutableLiveData.setValue(trailerArrayList);

                    }

                }

                @Override
                public void onFailure(Call<TrailerDBResponse> call, Throwable t)
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


        return trailerMutableLiveData;
    }

    public MutableLiveData<List<Cast>> getCastTvAiringMutableLiveData(int tvId)
    {
        try
        {
            mTvId = tvId;

            if (BuildConfig.ApiKey.isEmpty())
            {
                Toast.makeText(application.getApplicationContext(), "Please obtain Api Key firstly from themoviedb.org",
                        Toast.LENGTH_SHORT).show();
            }


            MovieDataService dataService = RetrofitInstance.getService();
            Call<CastDBResponse> call = dataService.getCastTvAiring(mTvId, MainActivity.Api_Key);

            call.enqueue(new Callback<CastDBResponse>()
            {
                @Override
                public void onResponse(Call<CastDBResponse> call, Response<CastDBResponse> response)
                {

                    CastDBResponse dbResponse = response.body();
                    if (dbResponse != null && dbResponse.getCast() != null)
                    {
                        castArrayList = (ArrayList<Cast>) dbResponse.getCast();
                        castMutableLiveData.setValue(castArrayList);
                    }

                }

                @Override
                public void onFailure(Call<CastDBResponse> call, Throwable t)
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


        return castMutableLiveData;
    }

}

//========================class MovieRepository set shud==============================================