package com.example.mytmdbclient.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.RecyclerItemRow3Binding;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.model.NowPlaying;
import com.example.mytmdbclient.view.NowPlayingMovieActivity;

import java.util.ArrayList;
import java.util.List;

public class NowPlaying_RecyclerAdapter extends RecyclerView.Adapter<NowPlaying_RecyclerAdapter.moviesNowPlayingViewHolder>
{

    private List<NowPlaying> nowPlayingList;
    private List<Genre> genreList;
    private Context mContext;
    private NowPlaying nowPlayingTemp = null;
    private RecyclerItemRow3Binding recyclerItemRow3Binding;


    public NowPlaying_RecyclerAdapter(List<NowPlaying> nowPlayingList, Context mContext, List<Genre> genreAll)
    {
        this.nowPlayingList = nowPlayingList;
        this.mContext = mContext;
        this.genreList = genreAll;
    }

    @NonNull
    @Override
    public moviesNowPlayingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        RecyclerItemRow3Binding recyclerItemRow3Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.recycler_item_row_3, parent, false);


        return new moviesNowPlayingViewHolder(recyclerItemRow3Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull moviesNowPlayingViewHolder holder, int position)
    {

        nowPlayingTemp = nowPlayingList.get(position);
        holder.recyclerItemRow3Binding.setNowPlaying(nowPlayingTemp);
        holder.recyclerItemRow3Binding.setGenre(getGenres(nowPlayingTemp.getGenreIds()));

    }

    private String getGenres(List<Integer> genreIds)
    {
        List<String> movieGenres = new ArrayList<>();
        for (Integer genreId : genreIds)
        {
            for (Genre genre : genreList)
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

    @Override
    public int getItemCount()
    {
        if (nowPlayingList != null)
            return nowPlayingList.size();
        else
            return 0;
    }

    public class moviesNowPlayingViewHolder extends RecyclerView.ViewHolder
    {

        private RecyclerItemRow3Binding recyclerItemRow3Binding;

        public moviesNowPlayingViewHolder(@NonNull RecyclerItemRow3Binding recyclerItemRow3Binding)
        {
            super(recyclerItemRow3Binding.getRoot());
            this.recyclerItemRow3Binding = recyclerItemRow3Binding;

            recyclerItemRow3Binding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position;
                    position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {
                        nowPlayingTemp = nowPlayingList.get(position);

                        Intent intent = new Intent(mContext, NowPlayingMovieActivity.class);
                        intent.putExtra("movie_nowPlaying", nowPlayingTemp);


                        //TODO : lets create the animation
                        ImageView imageView = recyclerItemRow3Binding.imgSmallPic;
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                                imageView, mContext.getString(R.string.sharedElementName));

                        mContext.startActivity(intent, options.toBundle());

                    }
                }
            });
        }
    }

    public void setRecyclerItemRow3Binding(RecyclerItemRow3Binding recyclerItemRow3Binding)
    {
        this.recyclerItemRow3Binding = recyclerItemRow3Binding;
    }
}
