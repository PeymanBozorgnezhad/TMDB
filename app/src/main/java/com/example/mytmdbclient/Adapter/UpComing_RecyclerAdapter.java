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
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.RvItemRowUpcomingBinding;
import com.example.mytmdbclient.model.UpComing;
import com.example.mytmdbclient.view.UpComingMovieActivity;

import java.util.List;

public class UpComing_RecyclerAdapter extends RecyclerView.Adapter<UpComing_RecyclerAdapter.upComingMoviesViewHolder>
{

    private List<UpComing> upComingList;
    private Context mContext;
    private UpComing upComingTemp = null;
    private RvItemRowUpcomingBinding rvItemRowUpcomingBinding;


    public UpComing_RecyclerAdapter(List<UpComing> upComingList, Context mContext)
    {
        this.upComingList = upComingList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public upComingMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        RvItemRowUpcomingBinding rvItemRowUpcomingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_item_row_upcoming, parent, false);

        return new upComingMoviesViewHolder(rvItemRowUpcomingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull upComingMoviesViewHolder holder, int position)
    {
        upComingTemp = upComingList.get(position);
        holder.rvItemRowUpcomingBinding.setUpComing(upComingTemp);

    }

    @Override
    public int getItemCount()
    {
        if (upComingList != null)
            return upComingList.size();
        else
            return 0;
    }

    public class upComingMoviesViewHolder extends RecyclerView.ViewHolder
    {

        private RvItemRowUpcomingBinding rvItemRowUpcomingBinding;

        public upComingMoviesViewHolder(@NonNull RvItemRowUpcomingBinding rvItemRowUpcomingBinding)
        {
            super(rvItemRowUpcomingBinding.getRoot());
            this.rvItemRowUpcomingBinding = rvItemRowUpcomingBinding;

            rvItemRowUpcomingBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    int position;
                    position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {
                        upComingTemp = upComingList.get(position);

                        Intent intent = new Intent(mContext, UpComingMovieActivity.class);
                        intent.putExtra("movie_upComing", upComingTemp);


                        //TODO : lets create the animation
                        ImageView imageView = rvItemRowUpcomingBinding.itemUpComingImg;
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                                imageView, mContext.getString(R.string.sharedElementName));

                        mContext.startActivity(intent, options.toBundle());

                    }

                }
            });
        }
    }

    public void setRvItemRowUpcomingBinding(RvItemRowUpcomingBinding rvItemRowUpcomingBinding)
    {
        this.rvItemRowUpcomingBinding = rvItemRowUpcomingBinding;
    }
}
