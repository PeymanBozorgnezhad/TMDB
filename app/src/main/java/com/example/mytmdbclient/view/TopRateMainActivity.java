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

import com.example.mytmdbclient.Adapter.TopRateMainAdapter;
import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ActivityTopRateMainBinding;
import com.example.mytmdbclient.model.MoviesTopRate;
import com.example.mytmdbclient.model.TopRateMainViewModel;

public class TopRateMainActivity extends AppCompatActivity
{

    private PagedList<MoviesTopRate> pagedListTopRates;
    public static RecyclerView recyclerVwTopRate;
    private TopRateMainAdapter topRateMainAdapter;
    private TopRateMainActivityClickHandlers handlers;
    private TopRateMainViewModel topRateMainViewModel;
    public static SwipeRefreshLayout swipeTopRateLayout;
    public static RelativeLayout rltvPullDownTopRate;
    private ActivityTopRateMainBinding activityTopRateMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rate_main);


        getSupportActionBar().setTitle("TMDB Top Rate Movies Today");


        activityTopRateMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_top_rate_main);

        handlers = new TopRateMainActivityClickHandlers(this);
        activityTopRateMainBinding.setClickHandlers(handlers);

        topRateMainViewModel = ViewModelProviders.of(this)
                .get(TopRateMainViewModel.class);

        FindViews();

        rltvPullDownTopRate.setVisibility(View.GONE);
        swipeTopRateLayout.setRefreshing(true);

        getTopRateMainMovies();


        swipeTopRateLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorRefresh);

        swipeTopRateLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getTopRateMainMovies();
            }
        });

    }


    public class TopRateMainActivityClickHandlers
    {
        private Context mContext;

        public TopRateMainActivityClickHandlers(Context mContext)
        {
            this.mContext = mContext;
        }

        public void onRltvPullDownClicked(View view)
        {
            Toast.makeText(mContext, "Pull Down Is Pressed", Toast.LENGTH_LONG).show();
            getTopRateMainMovies();

        }

    }

    public Object getTopRateMainMovies()
    {

        topRateMainViewModel.getTpRatePagedListLiveData().observe(this, new Observer<PagedList<MoviesTopRate>>()
        {
            @Override
            public void onChanged(PagedList<MoviesTopRate> moviesTopRates)
            {
                pagedListTopRates = moviesTopRates;
                showOnRecyclerViewTopRate();
            }
        });


        return pagedListTopRates;
    }


    private void showOnRecyclerViewTopRate()
    {

        recyclerVwTopRate = activityTopRateMainBinding.recyclerVwTopRate;
        topRateMainAdapter = new TopRateMainAdapter(this);
        topRateMainAdapter.submitList(pagedListTopRates);
        recyclerVwTopRate.setHasFixedSize(true);


        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {

            recyclerVwTopRate.setLayoutManager(new GridLayoutManager(TopRateMainActivity.this, 2));
        } else
        {
            recyclerVwTopRate.setLayoutManager(new GridLayoutManager(TopRateMainActivity.this, 4));
        }


        recyclerVwTopRate.setItemAnimator(new DefaultItemAnimator());
        recyclerVwTopRate.setAdapter(topRateMainAdapter);
        topRateMainAdapter.notifyDataSetChanged();

    }

    private void FindViews()
    {
        //TODO : RecyclerView
        recyclerVwTopRate = activityTopRateMainBinding.recyclerVwTopRate;

        //TODO : SwipeRefreshLayout
        swipeTopRateLayout = activityTopRateMainBinding.swipeTopRateLayout;

        //TODO : RelativeLayout
        rltvPullDownTopRate = activityTopRateMainBinding.rltvPullDownTopRate;

    }
}
