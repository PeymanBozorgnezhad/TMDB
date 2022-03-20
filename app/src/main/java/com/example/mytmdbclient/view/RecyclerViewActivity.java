package com.example.mytmdbclient.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mytmdbclient.Adapter.MovieAdapter;
import com.example.mytmdbclient.Adapter.NowPlaying_RecyclerAdapter;
import com.example.mytmdbclient.Adapter.Popular_RecyclerAdapter;
import com.example.mytmdbclient.Adapter.SearchPopularAdapter;
import com.example.mytmdbclient.Adapter.SliderAdapter;
import com.example.mytmdbclient.Adapter.TopRate_RecyclerAdapter;
import com.example.mytmdbclient.Adapter.TvAiring_RecyclerAdapter;
import com.example.mytmdbclient.Adapter.UpComing_RecyclerAdapter;
import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ActivityRecyclerViewBinding;
import com.example.mytmdbclient.databinding.SearchLayoutItemBinding;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.model.MainActivityViewModel;
import com.example.mytmdbclient.model.Movie;
import com.example.mytmdbclient.model.MovieDBResponse;
import com.example.mytmdbclient.model.MoviesTopRate;
import com.example.mytmdbclient.model.NowPlaying;
import com.example.mytmdbclient.model.TvAiringToday;
import com.example.mytmdbclient.model.UpComing;
import com.example.mytmdbclient.service.MovieDataService;
import com.example.mytmdbclient.service.RetrofitInstance;
import com.example.mytmdbclient.util.NetworkChangeReceiver;
import com.example.mytmdbclient.util.OnNetworkListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewActivity extends AppCompatActivity
        implements SearchPopularAdapter.SearchAdapterOnClickHandler, OnNetworkListener, NavigationView.OnNavigationItemSelectedListener

{
    private Popular_RecyclerAdapter popularRecyclerAdapter;
    private TopRate_RecyclerAdapter topRateRecyclerAdapter;
    private UpComing_RecyclerAdapter upComingRecyclerAdapter;
    private NowPlaying_RecyclerAdapter nowPlayingRecyclerAdapter;
    private ArrayList<NowPlaying> nowPlayingArrayList = new ArrayList<>();
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private ArrayList<MoviesTopRate> topRateArrayList = new ArrayList<>();
    private ArrayList<UpComing> upComingArrayList = new ArrayList<>();
    private ArrayList<Genre> genreArrayList = new ArrayList<>();
    private ActivityRecyclerViewBinding recyclerViewBinding;
    private MainActivityViewModel activityViewModel;
    private RecyclerViewActivityClickHandlers handlers;
    private RecyclerViewActivityTopRateClickHandlers handlerTopRate;
    private RecyclerViewActivityNowPlayingClickHandlers handlerNowPlaying;
    private RecyclerViewActivityUpComingClickHandlers handlerUpComing;
    private RecyclerViewActivityTvAiringClickHandlers handlerTvAiring;
    // private ImageSlider imageSlider;
    //TODO : Tv
    private TvAiring_RecyclerAdapter tvAiringRecyclerAdapter;
    private ArrayList<TvAiringToday> tvAiringTodayArrayList = new ArrayList<>();


    //TODO : NEW
    private Snackbar snack;

    public static Dialog progressDialog;
    private MovieAdapter movieAdapter;
    List<Movie> searchedMovieList;
    public RecyclerView recyclerView;
    private TextView no_movies_found;
    private SearchPopularAdapter searchAdapter;
    private SearchLayoutItemBinding searchLayoutItemBinding;
    private NetworkChangeReceiver mNetworkReceiver;
    private SwipeRefreshLayout swipeRvActivity;

    private static final long BACK_PRESS_DELAY = 1000;

    private boolean mBackPressCancelled = false;
    private long mBackPressTimestamp;
    private Toast mBackPressToast;

    //TODO : Auto Image Slider
    SliderView sliderView;
    private SliderAdapter adapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);


        recyclerViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view);

        progressDialog = createProgressDialog(RecyclerViewActivity.this);

        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);

        //swipeRvActivity = recyclerViewBinding.swipeRvAct;

        activityViewModel = ViewModelProviders.of(this)
                .get(MainActivityViewModel.class);


       // FindViewSNavDrawer();


        getPopularMovies();
        getTopRateMovies();
        getUpComingMovies();
        getNowPlayingMovies();

        /*swipeRvActivity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getPopularMovies();
                getTopRateMovies();
                getUpComingMovies();
                getNowPlayingMovies();
                Toast.makeText(RecyclerViewActivity.this, "onCreate refresh", Toast.LENGTH_SHORT).show();

            }
        });*/


        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);


        handlers = new RecyclerViewActivityClickHandlers(this);
        recyclerViewBinding.setClickHandlers(handlers);

        handlerTopRate = new RecyclerViewActivityTopRateClickHandlers(this);
        recyclerViewBinding.setClickHandlersTwo(handlerTopRate);

        handlerNowPlaying = new RecyclerViewActivityNowPlayingClickHandlers(this);
        recyclerViewBinding.setClickHandlersThree(handlerNowPlaying);

        handlerUpComing = new RecyclerViewActivityUpComingClickHandlers(this);
        recyclerViewBinding.setClickHandlersFour(handlerUpComing);

        //TODO : Tv
        handlerTvAiring = new RecyclerViewActivityTvAiringClickHandlers(this);
        recyclerViewBinding.setClickHandlersFive(handlerTvAiring);


    }

    /*private void FindViewSNavDrawer()
    {
        //TODO : Drawer Layout
        drawerLayout = recyclerViewBinding.drawerLayout;
        //TODO : Navigation View
        navigationView = recyclerViewBinding.navView;
        //TODO : Tool Bar
        //toolbar = recyclerViewBinding.toolbarMainRv;

        //TODO : Configure
        //setSupportActionBar(toolbar);

        *//*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);*//*

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


    }
*/

    private void setViews()
    {
        searchLayoutItemBinding = DataBindingUtil.setContentView(this, R.layout.search_layout_item);
        recyclerView = searchLayoutItemBinding.movieList;


        //recyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 3 : 4));
        recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
        recyclerView.setHasFixedSize(true);
        no_movies_found = searchLayoutItemBinding.noMoviesFound;


        //movieAdapter = new MovieAdapter(this);
    }


    public Object getNowPlayingMovies()
    {
        if (isNetworkConnected())
        {
            activityViewModel.getNowPlayingLiveData().observe(this, new Observer<List<NowPlaying>>()
            {
                @Override
                public void onChanged(List<NowPlaying> nowPlayings)
                {
                    nowPlayingArrayList = (ArrayList<NowPlaying>) nowPlayings;
                    showOnRecyclerViewNowPlaying();

                    if (nowPlayings != null && !nowPlayings.isEmpty())
                    {
                        progressDialog.dismiss();
                    }
                }
            });
        }


        return nowPlayingArrayList;
    }


    /*public void getSearchAllMovies()
    {
        if (isNetworkConnected())
        {
            // Observe the moviePagedList from ViewModel

            activityViewModel.getPagedListLiveData().observe(this, new Observer<PagedList<Movie>>()
            {
                @Override
                public void onChanged(PagedList<Movie> movies)
                {
                    // In case of any changes, submitting the movies to adapter
                    movieAdapter.submitList(movies);

                    // when screen is rotated
                    if (movies != null && !movies.isEmpty())
                    {
                        progressDialog.dismiss();
                    }
                }
            });
        }
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

    }*/

    private void registerNetworkBroadcastForNougat()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {

            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        }

    }

    @Override

    protected void onStart()
    {

        super.onStart();

        registerNetworkBroadcastForNougat();


    }


    @Override

    protected void onStop()
    {

        super.onStop();

        unregisterReceiver(mNetworkReceiver);

    }


    @Override
    public void onClick(Movie movie)
    {
        Intent intent = new Intent(RecyclerViewActivity.this, MovieActivity.class);

        // Pass an object of movie class

        intent.putExtra("movie", (movie));

        startActivity(intent);
    }

    public Object getImageSlider()
    {

        //sliderView = findViewById(R.id.imageSlider);
        sliderView = recyclerViewBinding.imageSlider;
        adapter = new SliderAdapter(this, movieArrayList);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener()
        {
            @Override
            public void onIndicatorClicked(int position)
            {
                Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
        return movieArrayList;
    }

    public Object getTvAiringToday()
    {

        if (isNetworkConnected())
        {
            activityViewModel.getTvAiringLiveData().observe(this, new Observer<List<TvAiringToday>>()
            {
                @Override
                public void onChanged(List<TvAiringToday> tvAiringTodays)
                {
                    tvAiringTodayArrayList = (ArrayList<TvAiringToday>) tvAiringTodays;
                    showOnRecyclerViewTvAiring();

                    if (tvAiringTodays != null && !tvAiringTodays.isEmpty())
                    {
                        progressDialog.dismiss();
                    }
                }
            });
        }

        return tvAiringTodayArrayList;
    }

    private void showOnRecyclerViewTvAiring()
    {
        RecyclerView recyclerViewTvAiring = recyclerViewBinding.RvTvAiring;


        recyclerViewTvAiring.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this
                , LinearLayoutManager.HORIZONTAL, false));

        recyclerViewTvAiring.setHasFixedSize(true);

        tvAiringRecyclerAdapter = new TvAiring_RecyclerAdapter(tvAiringTodayArrayList, RecyclerViewActivity.this);
        recyclerViewTvAiring.setAdapter(tvAiringRecyclerAdapter);
        tvAiringRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        return true;
    }


    public class RecyclerViewActivityClickHandlers
    {
        private Context mContext;

        public RecyclerViewActivityClickHandlers(Context mContext)
        {
            this.mContext = mContext;
        }

        public void onLnrContainerClicked(View view)
        {
            Intent intent = new Intent(RecyclerViewActivity.this, MainActivity.class);
            mContext.startActivity(intent);
        }


    }

    public class RecyclerViewActivityTopRateClickHandlers
    {
        private Context context;

        public RecyclerViewActivityTopRateClickHandlers(Context context)
        {
            this.context = context;
        }

        public void onButtonRvTopRateClicked(View view)
        {
            Intent intent = new Intent(RecyclerViewActivity.this, TopRateMainActivity.class);
            context.startActivity(intent);
        }
    }

    public class RecyclerViewActivityNowPlayingClickHandlers
    {
        private Context context;

        public RecyclerViewActivityNowPlayingClickHandlers(Context context)
        {
            this.context = context;
        }

        public void onButtonRvNowPlayingClicked(View view)
        {
            Intent intent = new Intent(RecyclerViewActivity.this, NowPlayingMainActivity.class);
            context.startActivity(intent);
        }
    }

    public class RecyclerViewActivityUpComingClickHandlers
    {
        private Context context;

        public RecyclerViewActivityUpComingClickHandlers(Context context)
        {
            this.context = context;
        }

        public void onButtonRvUpComingClicked(View view)
        {
            Intent intent = new Intent(RecyclerViewActivity.this, UpComingMainActivity.class);
            context.startActivity(intent);
        }
    }

    //TODO : Tv
    public class RecyclerViewActivityTvAiringClickHandlers
    {
        private Context context;

        public RecyclerViewActivityTvAiringClickHandlers(Context context)
        {
            this.context = context;
        }

        public void onButtonRvTvAiringClicked(View view)
        {
            Intent intent = new Intent(RecyclerViewActivity.this, TvAiringMainActivity.class);
            context.startActivity(intent);
        }
    }


    public Object getPopularMovies()
    {
        if (isNetworkConnected())
        {

            activityViewModel.getListLiveData().observe(this, new Observer<List<Movie>>()
            {
                @Override
                public void onChanged(List<Movie> movies)
                {
                    movieArrayList = (ArrayList<Movie>) movies;

                    showOnRecyclerView();


             /*we create Image Slider By Using Two Types ::
             1==>> View Pager
             2==>>Using Third Party Library Which is Easy To Use and Implement
             ImageSlider imageSlider = (ImageSlider) findViewById(R.id.image_slider);*/


                    if (movies != null && !movies.isEmpty())
                    {
                        progressDialog.dismiss();
                    }

                    /*List<SlideModel> slideModels = new ArrayList<>();
                    slideModels.add(new SlideModel("https://image.tmdb.org/t/p/w500/c8Ass7acuOe4za6DhSattE359gr.jpg", getString(R.string.Slide_Title)));
                    slideModels.add(new SlideModel("https://image.tmdb.org/t/p/w500/avl9MEQhtvokNnzoWepkmHBZ2ss.jpg", getString(R.string.Slide_Title)));
                    slideModels.add(new SlideModel("https://image.tmdb.org/t/p/w500/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg", getString(R.string.Slide_Title)));
                    slideModels.add(new SlideModel("https://image.tmdb.org/t/p/w500/8Q2kDDyswBB8SPUJWfNlqvxdDFX.jpg", getString(R.string.Slide_Title)));
                    slideModels.add(new SlideModel("https://image.tmdb.org/t/p/w500/kizBwSEfvJFEOuYfwbOGtWZit3v.jpg", getString(R.string.Slide_Title)));



                    imageSlider.setImageList(slideModels, false);*/

                    getImageSlider();


                }
            });
        }

        return movieArrayList;
    }

    public Object getTopRateMovies()
    {
        if (isNetworkConnected())
        {

            activityViewModel.getTopRateLiveData().observe(this, new Observer<List<MoviesTopRate>>()
            {
                @Override
                public void onChanged(List<MoviesTopRate> topRates)
                {
                    topRateArrayList = (ArrayList<MoviesTopRate>) topRates;

                    showOnRecyclerViewTopRate();
                    //lnrAllContainer.setVisibility(View.VISIBLE);

                    if (topRates != null && !topRates.isEmpty())
                    {
                        progressDialog.dismiss();
                    }

                }
            });
        }
        return topRateArrayList;
    }

    public Object getUpComingMovies()
    {
        if (isNetworkConnected())
        {

            activityViewModel.getUpComingLiveData().observe(this, new Observer<List<UpComing>>()
            {
                @Override
                public void onChanged(List<UpComing> upComings)
                {
                    upComingArrayList = (ArrayList<UpComing>) upComings;
                    showOnRecyclerViewUpComing();

                    if (upComings != null && !upComings.isEmpty())
                    {
                        progressDialog.dismiss();
                    }
                }
            });
        }


        return upComingArrayList;
    }

    private void showOnRecyclerView()
    {

        RecyclerView recyclerViewPopulars = recyclerViewBinding.RvPopulars;
        //imageSlider = recyclerViewBinding.imageSlider;
        sliderView = recyclerViewBinding.imageSlider;

        recyclerViewPopulars.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this
                , LinearLayoutManager.HORIZONTAL, false));

        recyclerViewPopulars.setHasFixedSize(true);

        popularRecyclerAdapter = new Popular_RecyclerAdapter(movieArrayList, RecyclerViewActivity.this);
        recyclerViewPopulars.setAdapter(popularRecyclerAdapter);
        popularRecyclerAdapter.notifyDataSetChanged();

    }

    private void showOnRecyclerViewTopRate()
    {
        RecyclerView recyclerViewTopRate = recyclerViewBinding.RvTopRate;


        recyclerViewTopRate.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this
                , LinearLayoutManager.HORIZONTAL, false));

        recyclerViewTopRate.setHasFixedSize(true);

        topRateRecyclerAdapter = new TopRate_RecyclerAdapter(topRateArrayList, RecyclerViewActivity.this);
        recyclerViewTopRate.setAdapter(topRateRecyclerAdapter);
        topRateRecyclerAdapter.notifyDataSetChanged();

    }


    private void showOnRecyclerViewUpComing()
    {
        RecyclerView recyclerViewUpComing = recyclerViewBinding.RvUpComing;


        recyclerViewUpComing.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        recyclerViewUpComing.setHasFixedSize(true);
        upComingRecyclerAdapter = new UpComing_RecyclerAdapter(upComingArrayList, RecyclerViewActivity.this);
        recyclerViewUpComing.setAdapter(upComingRecyclerAdapter);
        upComingRecyclerAdapter.notifyDataSetChanged();

    }


    private void showOnRecyclerViewNowPlaying()
    {

        RecyclerView recyclerViewNowPlaying = recyclerViewBinding.RvNowPlaying;


        recyclerViewNowPlaying.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this, LinearLayoutManager.HORIZONTAL
                , false));

        getGenreHolder();

        recyclerViewNowPlaying.setHasFixedSize(true);
        nowPlayingRecyclerAdapter = new NowPlaying_RecyclerAdapter(nowPlayingArrayList, RecyclerViewActivity.this, genreArrayList);
        recyclerViewNowPlaying.setAdapter(nowPlayingRecyclerAdapter);
        nowPlayingRecyclerAdapter.notifyDataSetChanged();


    }


    public Object getGenreHolder()
    {
        if (isNetworkConnected())
        {
            activityViewModel.getGenreLiveData().observe(this, new Observer<List<Genre>>()
            {
                @Override
                public void onChanged(List<Genre> genreList)
                {

                    genreArrayList = (ArrayList<Genre>) genreList;
                    if (genreList != null && !genreList.isEmpty())
                    {
                        progressDialog.dismiss();
                    }
                }
            });
        }

        return genreArrayList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_for_movies));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                if (isNetworkConnected())
                {
                    Search(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                if (isNetworkConnected())
                {
                    Search(newText);


                }
                return false;
            }
        });

        /*searchView.setOnSearchClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setItemsVisibility(menu,searchViewItem,false);
            }
        });*/


        searchView.setOnCloseListener(new SearchView.OnCloseListener()
        {
            @Override
            public boolean onClose()
            {
                if (searchedMovieList != null)
                {
                    Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
                    searchAdapter.clear();

                    getPopularMovies();
                    getTopRateMovies();
                    getUpComingMovies();
                    getNowPlayingMovies();
                    // setItemsVisibility(menu,searchViewItem,true);
                    //TODO : Tv
                    getTvAiringToday();

                }

                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchViewItem, new MenuItemCompat.OnActionExpandListener()
        {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item)
            {
                //Do whatever you want

                //     Toast.makeText(RecyclerViewActivity.this, "onMenuItemActionExpand", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item)
            {
                //Do whatever you want
                startActivity(new Intent(RecyclerViewActivity.this, RecyclerViewActivity.class));
                finish();
                //       Toast.makeText(RecyclerViewActivity.this, "onMenuItemActionCollapse", Toast.LENGTH_SHORT).show();

                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);


    }


/*private void setItemsVisibility(final Menu menu,final MenuItem exception,final boolean visible)
{
    for (int i = 0 ; i < menu.size() ; i++)
    {
        MenuItem item = menu.getItem(i);
        if (item !=exception)
            item.setVisible(visible);
    }
}*/

    @Override
    public void onBackPressed()
    {


        /*if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }*/


        // Do nothing if the back button is disabled.
        if (!mBackPressCancelled)
        {
            // Pop fragment if the back stack is not empty.
            if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            {
                super.onBackPressed();
            } else
            {
                if (mBackPressToast != null)
                {
                    mBackPressToast.cancel();
                }

                long currentTimestamp = System.currentTimeMillis();

                if (currentTimestamp < mBackPressTimestamp + BACK_PRESS_DELAY)
                {

                    super.onBackPressed();
                } else
                {
                    mBackPressTimestamp = currentTimestamp;

                    mBackPressToast = Toast.makeText(this, getString(R.string.warning_exit), Toast.LENGTH_SHORT);
                    mBackPressToast.show();
                }
            }
        }

    }

    /**
     * Search for movies.
     */
    private String Search(String query)
    {
        setViews();

        MovieDataService dataService = RetrofitInstance.getService();
        Call<MovieDBResponse> call = dataService.searchForMovies(query, MainActivity.Api_Key);


        call.enqueue(new Callback<MovieDBResponse>()
        {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response)
            {
                MovieDBResponse dbResponse = response.body();

                if (dbResponse != null && dbResponse.getResults() != null)
                {
                    searchedMovieList = dbResponse.getResults();

                    if (searchedMovieList.isEmpty())
                    {
                        getNoResult();

                    }

                    Log.v("onResponse", searchedMovieList.size() + " Movies");
                    searchAdapter = new SearchPopularAdapter(searchedMovieList, getApplicationContext(), genreArrayList, new SearchPopularAdapter.SearchAdapterOnClickHandler()
                    {
                        @Override
                        public void onClick(Movie movie)
                        {

                            Intent intent = new Intent(RecyclerViewActivity.this, MovieActivity.class);

                            // Pass an object of movie class
                            intent.putExtra("movie", (movie));
                            startActivity(intent);

                        }
                    });
                }

                recyclerView.setAdapter(searchAdapter);


            }

            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t)
            {

                Log.v("onFailure", " Failed to get movies");
            }
        });

        return query;
    }

    /**
     * Get no result for search.
     */
    private void getNoResult()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage(getResources().getString(R.string.no_match));

        // Create and show the AlertDialog
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            public void run()
            {
                alertDialog.dismiss();
                timer.cancel();
            }
        }, 2000);
    }


    private boolean isNetworkConnected()
    {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
        {
            return true;

        } else
        {

            progressDialog.dismiss();
            showSnackBar();
            return false;
        }
    }

    public void showSnackBar()
    {
        snack.setAction("CLOSE", new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                snack.dismiss();
            }
        });
        snack.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snack.show();
    }


    public static Dialog createProgressDialog(Context context)
    {
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        return progressDialog;
    }


    @Override
    public void onNetworkConnected()
    {

        snack.dismiss();

        progressDialog.show();


        getPopularMovies();
        getTopRateMovies();
        getUpComingMovies();
        getNowPlayingMovies();

        //TODO : Tv
        getTvAiringToday();

        //Toast.makeText(RecyclerViewActivity.this, "onNetworkConnected", Toast.LENGTH_LONG).show();


    }


    @Override
    public void onNetworkDisconnected()
    {


        progressDialog.dismiss();
        showSnackBar();


    }
}



