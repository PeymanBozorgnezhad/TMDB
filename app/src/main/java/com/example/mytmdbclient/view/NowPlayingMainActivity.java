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

import com.example.mytmdbclient.Adapter.NowPlayingMainAdapter;
import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ActivityNowPlayingMainBinding;
import com.example.mytmdbclient.model.NowPlaying;
import com.example.mytmdbclient.model.NowPlayingMainViewModel;

public class NowPlayingMainActivity extends AppCompatActivity
{

    private PagedList<NowPlaying> nowPlayingPagedList;
    private NowPlayingMainActivityClickHandlers handlers;
    private ActivityNowPlayingMainBinding activityNowPlayingMainBinding;
    public static SwipeRefreshLayout swipeNowPlayingLayout;
    public static RelativeLayout rltvPullDownNowPlaying;
    public static RecyclerView recyclerVwNowPlaying;
    private NowPlayingMainAdapter nowPlayingMainAdapter;
    private NowPlayingMainViewModel nowPlayingMainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing_main);

        getSupportActionBar().setTitle("TMDB Now Playing Movies Today");

        activityNowPlayingMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_now_playing_main);

        handlers = new NowPlayingMainActivityClickHandlers(this);
        activityNowPlayingMainBinding.setClickHandlers(handlers);

        nowPlayingMainViewModel = ViewModelProviders.of(this)
                .get(NowPlayingMainViewModel.class);

        FindViews();

        rltvPullDownNowPlaying.setVisibility(View.GONE);
        swipeNowPlayingLayout.setRefreshing(true);

        getNowPlayingMainMovies();

        swipeNowPlayingLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorRefresh);

        swipeNowPlayingLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getNowPlayingMainMovies();
            }
        });

    }

    public Object getNowPlayingMainMovies()
    {

        nowPlayingMainViewModel.getNowPlayingPagedListLiveData().observe(this, new Observer<PagedList<NowPlaying>>()
        {
            @Override
            public void onChanged(PagedList<NowPlaying> nowPlayings)
            {
                nowPlayingPagedList = nowPlayings;
                showOnRecyclerViewNowPlaying();
            }
        });

        return nowPlayingPagedList;
    }

    private void showOnRecyclerViewNowPlaying()
    {
        recyclerVwNowPlaying = activityNowPlayingMainBinding.recyclerVwNowPlaying;
        nowPlayingMainAdapter = new NowPlayingMainAdapter(this);
        nowPlayingMainAdapter.submitList(nowPlayingPagedList);
        recyclerVwNowPlaying.setHasFixedSize(true);


        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {

            recyclerVwNowPlaying.setLayoutManager(new GridLayoutManager(NowPlayingMainActivity.this, 2));
        } else
        {
            recyclerVwNowPlaying.setLayoutManager(new GridLayoutManager(NowPlayingMainActivity.this, 4));
        }


        recyclerVwNowPlaying.setItemAnimator(new DefaultItemAnimator());
        recyclerVwNowPlaying.setAdapter(nowPlayingMainAdapter);
        nowPlayingMainAdapter.notifyDataSetChanged();
    }


    public class NowPlayingMainActivityClickHandlers
    {
        private Context mContext;

        public NowPlayingMainActivityClickHandlers(Context mContext)
        {
            this.mContext = mContext;
        }

        public void onRltvNowPlayingPullDownClicked(View view)
        {
            Toast.makeText(mContext, "Pull Down Is Pressed", Toast.LENGTH_LONG).show();
            getNowPlayingMainMovies();
        }
    }

    private void FindViews()
    {
        //TODO : RecyclerView
        recyclerVwNowPlaying = activityNowPlayingMainBinding.recyclerVwNowPlaying;

        //TODO : SwipeRefreshLayout
        swipeNowPlayingLayout = activityNowPlayingMainBinding.swipeNowPlayingLayout;

        //TODO : RelativeLayout
        rltvPullDownNowPlaying = activityNowPlayingMainBinding.rltvPullDownNowPlaying;

    }

}
