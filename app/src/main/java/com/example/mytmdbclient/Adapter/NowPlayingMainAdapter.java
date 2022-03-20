package com.example.mytmdbclient.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ItemRowMainNowPlayingBinding;
import com.example.mytmdbclient.model.NowPlaying;
import com.example.mytmdbclient.view.NowPlayingMovieActivity;

public class NowPlayingMainAdapter extends PagedListAdapter<NowPlaying, NowPlayingMainAdapter.nowPlayingMainViewHolder>
{

    //private ArrayList<MoviesTopRate> moviesTopRates;
    private Context mContext;
    private NowPlaying nowPlayingTemp = null;
    private ItemRowMainNowPlayingBinding itemRowMainNowPlayingBinding;

    public NowPlayingMainAdapter(Context mContext)
    {
        super(NowPlaying.CALLBACK);
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public nowPlayingMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        ItemRowMainNowPlayingBinding itemRowMainNowPlayingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_row_main_now_playing, parent, false);
        return new nowPlayingMainViewHolder(itemRowMainNowPlayingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull nowPlayingMainViewHolder holder, int position)
    {

        nowPlayingTemp = getItem(position);
        holder.itemRowMainNowPlayingBinding.setMovieNowPlaying(nowPlayingTemp);

    }

    public class nowPlayingMainViewHolder extends RecyclerView.ViewHolder
    {
        private ItemRowMainNowPlayingBinding itemRowMainNowPlayingBinding;

        public nowPlayingMainViewHolder(@NonNull ItemRowMainNowPlayingBinding itemRowMainNowPlayingBinding)
        {
            super(itemRowMainNowPlayingBinding.getRoot());
            this.itemRowMainNowPlayingBinding = itemRowMainNowPlayingBinding;

            itemRowMainNowPlayingBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position;
                    position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {

                        nowPlayingTemp = getItem(position);

                        Intent intent = new Intent(mContext, NowPlayingMovieActivity.class);
                        intent.putExtra("movie_nowPlaying", nowPlayingTemp);


                        //TODO : lets create the animation
                        ImageView imageView = itemRowMainNowPlayingBinding.ivMovie;
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                                imageView, mContext.getApplicationContext().getString(R.string.sharedElementName));

                        mContext.startActivity(intent, options.toBundle());


                    }
                }
            });
        }
    }

    public void setItemRowMainNowPlayingBinding(ItemRowMainNowPlayingBinding itemRowMainNowPlayingBinding)
    {
        this.itemRowMainNowPlayingBinding = itemRowMainNowPlayingBinding;
    }
}
