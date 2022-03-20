package com.example.mytmdbclient.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.util.MovieDataSource;
import com.example.mytmdbclient.util.MovieDataSourceFactory;
import com.example.mytmdbclient.util.MovieRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends AndroidViewModel
{
    private MovieRepository movieRepository;
    private LiveData<List<NowPlaying>> NowPlayingLiveData;
    private LiveData<List<Movie>> listLiveData;
    private LiveData<List<MoviesTopRate>> TopRateLiveData;
    private LiveData<List<UpComing>> UpComingLiveData;
    private LiveData<List<Genre>> genreLiveData;
    private LiveData<List<Trailer>> trailerLiveData;
    private LiveData<List<Cast>> castListLiveData;

    //TODO : Tv
    private LiveData<List<TvAiringToday>> TvAiringLiveData;
    private int tvId;

    //private final SnackbarMessage mSnackbarText = new SnackbarMessage();

    //private LiveData<Integer> movieIdLiveData;

    private LiveData<MovieDataSource> dataSourceLiveData;

    private int movieId;


    private Executor executor;
    private LiveData<PagedList<Movie>> pagedListLiveData;


    public MainActivityViewModel(@NonNull Application application)
    {
        super(application);

        movieRepository = new MovieRepository(application);

        NowPlayingLiveData = movieRepository.getNowPlayingMutableLiveData();

        listLiveData = movieRepository.getListMutableLiveData();

        TopRateLiveData = movieRepository.getMutableTopRate();

        genreLiveData = movieRepository.getGenreMutableLiveData();

        UpComingLiveData = movieRepository.getUpComingMutableLiveData();

        //movieIdLiveData = movieRepository.getMovieIdMutableLiveData();

        //TODO : Tv
        TvAiringLiveData = movieRepository.getTvAiringMutableLiveData();


        MovieDataService movieDataService = RetrofitInstance.getService();
        MovieDataSourceFactory factory = new MovieDataSourceFactory(movieDataService, application);

        dataSourceLiveData = factory.getMutableLiveData();

        //TODO : create And Configuration PagedList

        //hala dar inja nobat b create pagedList mirese,ama qabl az inke bekhaym create konim ma bayad set configuration ro anjam bedim

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(10)
                .setPageSize(20)//by pagedList detect mishe..tedad itemhaie k dar other pages qarar migiran
                .setPrefetchDistance(4)
                .build();

        //dar in part bra PagedList khudemon ye instance az type Executor define mikonim

        executor = Executors.newFixedThreadPool(5);

        //aknon dar inja ma ye instance az MovieDataSourceFactory(==>>factory),ye  instance az config , ye instance az Executor darim
        // hala bayad az PagedList Khodemon ye LiveData az type Object Movie Create konim..., dar nahayat kol data ro ba uses of
        //pagedListLiveData build mikonim

        pagedListLiveData = (new LivePagedListBuilder<Long, Movie>(factory, config))
                .setFetchExecutor(executor)
                .build();


    }

    //TODO : PopularMovies
    public LiveData<List<Movie>> getListLiveData()
    {
        return listLiveData;
    }

    public LiveData<PagedList<Movie>> getPagedListLiveData()
    {
        return pagedListLiveData;
    }

    //TODO : TopRateMovies

    public LiveData<List<MoviesTopRate>> getTopRateLiveData()
    {
        return TopRateLiveData;
    }

    public LiveData<List<Genre>> getGenreLiveData()
    {
        return genreLiveData;
    }


    //TODO : UpComingMovies
    public LiveData<List<UpComing>> getUpComingLiveData()
    {
        return UpComingLiveData;
    }

    public LiveData<List<NowPlaying>> getNowPlayingLiveData()
    {
        return NowPlayingLiveData;
    }

    public LiveData<List<Trailer>> getTrailerLiveData()
    {
        return trailerLiveData;
    }

    public LiveData<List<Cast>> getCastListLiveData()
    {
        return castListLiveData;
    }


    public int getMovieId()
    {
        return movieId;
    }

    public void setMovieId(int movieId)
    {
        this.movieId = movieId;
        trailerLiveData = movieRepository.getTrailerMutableLiveData(movieId);
        castListLiveData = movieRepository.getCastMutableLiveData(movieId);

    }

    public int getTvId()
    {
        return tvId;
    }

    public void setTvId(int tvId)
    {
        this.tvId = tvId;
        trailerLiveData = movieRepository.getTrailerTvMutableLiveData(tvId);
        castListLiveData = movieRepository.getCastTvAiringMutableLiveData(tvId);
    }


    /*public SnackbarMessage getSnackbarMessage()
    {
        return mSnackbarText;
    }*/


    /*public void retry(int movieId)
    {
        setMovieId(movieId);
    }*/

    /*private void showSnackbarMessage(Integer message) {
        mSnackbarText.setValue(message);
    }*/

    //TODO : Tv

    public LiveData<List<TvAiringToday>> getTvAiringLiveData()
    {
        return TvAiringLiveData;
    }
















    /* public LiveData<Integer> getMovieIdLiveData()
    {
        return movieIdLiveData;
    }

    public void setMovieIdLiveData(LiveData<Integer> movieIdLiveData)
    {
        this.movieIdLiveData = movieIdLiveData;
        trailerLiveData = movieRepository.getTrailerMutableLiveData(movieId);
    }*/


}
