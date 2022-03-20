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

import com.example.mytmdbclient.Adapter.TvAiringMainAdapter;
import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ActivityTvAiringMainBinding;
import com.example.mytmdbclient.model.TvAiringMainViewModel;
import com.example.mytmdbclient.model.TvAiringToday;

public class TvAiringMainActivity extends AppCompatActivity
{

    private PagedList<TvAiringToday> airingTodayPagedList;
    public static RecyclerView recyclerVwTvAiring;
    private TvAiringMainAdapter tvAiringMainAdapter;
    private TvAiringMainActivityClickHandlers handlers;
    private TvAiringMainViewModel tvAiringMainViewModel;
    public static SwipeRefreshLayout swipeTvAiringLayout;
    public static RelativeLayout rltvPullDownTvAiring;
    private ActivityTvAiringMainBinding activityTvAiringMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_airing_main);


        getSupportActionBar().setTitle("TMDB Tv Airing Movies Today");


        activityTvAiringMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_airing_main);

        handlers = new TvAiringMainActivityClickHandlers(this);
        activityTvAiringMainBinding.setClickHandlers(handlers);

        tvAiringMainViewModel = ViewModelProviders.of(this)
                .get(TvAiringMainViewModel.class);

        FindViews();

        rltvPullDownTvAiring.setVisibility(View.GONE);
        swipeTvAiringLayout.setRefreshing(true);

        getTvAiringMain();


        swipeTvAiringLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorRefresh);

        swipeTvAiringLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getTvAiringMain();
            }
        });


    }



    public class TvAiringMainActivityClickHandlers
    {
        private Context mContext;

        public TvAiringMainActivityClickHandlers(Context mContext)
        {
            this.mContext = mContext;
        }

        public void onRltvPullDownClicked(View view)
        {
            Toast.makeText(mContext, "Pull Down Is Pressed", Toast.LENGTH_LONG).show();
            getTvAiringMain();

        }

    }

    public Object getTvAiringMain()
    {

        tvAiringMainViewModel.getTvAiringPagedListLiveData().observe(this, new Observer<PagedList<TvAiringToday>>()
        {
            @Override
            public void onChanged(PagedList<TvAiringToday> tvAiringTodays)
            {
                airingTodayPagedList = tvAiringTodays;
                showOnRecyclerViewTvAiring();
            }
        });

        return airingTodayPagedList;
    }

    private void showOnRecyclerViewTvAiring()
    {
        recyclerVwTvAiring = activityTvAiringMainBinding.recyclerVwTvAiring;
        tvAiringMainAdapter = new TvAiringMainAdapter(this);
        tvAiringMainAdapter.submitList(airingTodayPagedList);
        recyclerVwTvAiring.setHasFixedSize(true);


        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {

            recyclerVwTvAiring.setLayoutManager(new GridLayoutManager(TvAiringMainActivity.this, 2));
        } else
        {
            recyclerVwTvAiring.setLayoutManager(new GridLayoutManager(TvAiringMainActivity.this, 4));
        }


        recyclerVwTvAiring.setItemAnimator(new DefaultItemAnimator());
        recyclerVwTvAiring.setAdapter(tvAiringMainAdapter);
        tvAiringMainAdapter.notifyDataSetChanged();
    }

    private void FindViews()
    {
        //TODO : RecyclerView
        recyclerVwTvAiring = activityTvAiringMainBinding.recyclerVwTvAiring;

        //TODO : SwipeRefreshLayout
        swipeTvAiringLayout = activityTvAiringMainBinding.swipeTvAiringLayout;

        //TODO : RelativeLayout
        rltvPullDownTvAiring = activityTvAiringMainBinding.rltvPullDownTvAiring;
    }

}
