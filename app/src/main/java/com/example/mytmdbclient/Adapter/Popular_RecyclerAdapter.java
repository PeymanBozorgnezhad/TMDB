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
import com.example.mytmdbclient.databinding.RecyclerItemRowBinding;
import com.example.mytmdbclient.model.Movie;
import com.example.mytmdbclient.view.MovieActivity;

import java.util.List;

public class Popular_RecyclerAdapter extends RecyclerView.Adapter<Popular_RecyclerAdapter.popularMoviesViewHolder>
{

    private List<Movie> movieList;
    private Context mContext;
    private Movie TempValues = null;
    private RecyclerItemRowBinding recyclerItemRowBinding;


    public Popular_RecyclerAdapter(List<Movie> movieList, Context mContext)
    {
        this.movieList = movieList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public popularMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RecyclerItemRowBinding recyclerItemRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.recycler_item_row, parent, false);
        return new popularMoviesViewHolder(recyclerItemRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull popularMoviesViewHolder holder, int position)
    {

        TempValues = movieList.get(position);
        holder.recyclerItemRowBinding.setMovie(TempValues);

    }


    @Override
    public int getItemCount()
    {
        if (movieList != null)
            return movieList.size();
        else
            return 0;
    }

    public class popularMoviesViewHolder extends RecyclerView.ViewHolder
    {
        private RecyclerItemRowBinding recyclerItemRowBinding;

        public popularMoviesViewHolder(@NonNull RecyclerItemRowBinding recyclerItemRowBinding)
        {
            super(recyclerItemRowBinding.getRoot());

            this.recyclerItemRowBinding = recyclerItemRowBinding;

            recyclerItemRowBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position;
                    position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {
                        TempValues = movieList.get(position);


                        //Intent intent = new Intent(mContext, ScrollingActivity.class);
                        Intent intent = new Intent(mContext, MovieActivity.class);
                        intent.putExtra("movie", TempValues);
                        //intent.putExtra("movie_id", TempValues.getId());

                        //TODO : lets create the animation
                        ImageView imageView = recyclerItemRowBinding.itemPopularsImg;
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                                imageView, mContext.getString(R.string.sharedElementName));

                        mContext.startActivity(intent, options.toBundle());

                    }
                }
            });


        }
    }


    public Popular_RecyclerAdapter(RecyclerItemRowBinding recyclerItemRowBinding)
    {
        this.recyclerItemRowBinding = recyclerItemRowBinding;
    }
}
