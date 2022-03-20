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

import com.example.mytmdbclient.Adapter.UpComingMainAdapter;
import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ActivityUpComingMainBinding;
import com.example.mytmdbclient.model.UpComing;
import com.example.mytmdbclient.model.UpComingMainViewModel;

public class UpComingMainActivity extends AppCompatActivity
{

    private PagedList<UpComing> upComingPagedList;
    private UpComingMainActivityClickHandlers handlers;
    private ActivityUpComingMainBinding activityUpComingMainBinding;
    public static SwipeRefreshLayout swipeUpcomingLayout;
    public static RelativeLayout rltvPullDownUpComing;
    public static RecyclerView recyclerVwUpComing;
    private UpComingMainAdapter upComingMainAdapter;
    private UpComingMainViewModel upComingMainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_coming_main);

        getSupportActionBar().setTitle("TMDB UpComing Movies Today");

        activityUpComingMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_up_coming_main);

        handlers = new UpComingMainActivityClickHandlers(this);
        activityUpComingMainBinding.setClickHandlers(handlers);

        upComingMainViewModel = ViewModelProviders.of(this)
                .get(UpComingMainViewModel.class);

        FindViews();

        rltvPullDownUpComing.setVisibility(View.GONE);
        swipeUpcomingLayout.setRefreshing(true);

        getUpComingMainMovies();

        swipeUpcomingLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorRefresh);

        swipeUpcomingLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getUpComingMainMovies();
            }
        });


    }

    public Object getUpComingMainMovies()
    {

        upComingMainViewModel.getUpComingPagedListLiveData().observe(this, new Observer<PagedList<UpComing>>()
        {
            @Override
            public void onChanged(PagedList<UpComing> upComings)
            {
                upComingPagedList = upComings;
                showOnRecyclerViewUpComing();
            }
        });

        return upComingPagedList;
    }

    private void showOnRecyclerViewUpComing()
    {
        recyclerVwUpComing = activityUpComingMainBinding.recyclerVwUpComing;
        upComingMainAdapter = new UpComingMainAdapter(this);
        upComingMainAdapter.submitList(upComingPagedList);
        recyclerVwUpComing.setHasFixedSize(true);


        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {

            recyclerVwUpComing.setLayoutManager(new GridLayoutManager(UpComingMainActivity.this, 2));
        } else
        {
            recyclerVwUpComing.setLayoutManager(new GridLayoutManager(UpComingMainActivity.this, 4));
        }


        recyclerVwUpComing.setItemAnimator(new DefaultItemAnimator());
        recyclerVwUpComing.setAdapter(upComingMainAdapter);
        upComingMainAdapter.notifyDataSetChanged();
    }


    public class UpComingMainActivityClickHandlers
    {
        private Context mContext;

        public UpComingMainActivityClickHandlers(Context mContext)
        {
            this.mContext = mContext;
        }

        public void onRltvUpComingPullDownClicked(View view)
        {
            Toast.makeText(mContext, "Pull Down Is Pressed", Toast.LENGTH_LONG).show();
            getUpComingMainMovies();
        }
    }

    private void FindViews()
    {
        //TODO : RecyclerView
        recyclerVwUpComing = activityUpComingMainBinding.recyclerVwUpComing;

        //TODO : SwipeRefreshLayout
        swipeUpcomingLayout = activityUpComingMainBinding.swipeUpComingLayout;

        //TODO : RelativeLayout
        rltvPullDownUpComing = activityUpComingMainBinding.rltvPullDownUpComing;

    }


}
