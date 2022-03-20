package com.example.mytmdbclient.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.RecyclerItemRow2Binding;
import com.example.mytmdbclient.databinding.RecyclerItemRowBinding;
import com.example.mytmdbclient.model.Movie;
import com.example.mytmdbclient.model.MoviesTopRate;
import com.example.mytmdbclient.view.MovieActivity;

import java.util.List;

public class MovieAdapter_recycler extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private List<Movie> movies;
    private List<MoviesTopRate> moviesTopRates;
    //private LayoutInflater inflater = null;
    private Movie TempValues = null;
    private MoviesTopRate TempValuesOne = null;
    private RecyclerItemRowBinding recyclerItemRowBinding;
    private RecyclerItemRow2Binding recyclerItemRow2Binding;

    private static final int ITEM_TYPE_ROW_1 = 0;
    private static final int ITEM_TYPE_ROW_2 = 1;
    /*private static final int ITEM_TYPE_ROW_3 = 2;*/


    public MovieAdapter_recycler(List<Movie> movies, List<MoviesTopRate> moviesTopRates)
    {
        this.movies = movies;
        this.moviesTopRates = moviesTopRates;
        //this.inflater = LayoutInflater.from(mContext);
        //this.moviesTopRates = moviesTopRates;
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (position)
        {
            case 0:
                return ITEM_TYPE_ROW_1;
            case 1:
                return ITEM_TYPE_ROW_2;
            default:
                return -1;

        }
        // throw new RuntimeException(String.format("unexpected position - %d", position));


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
      /*  switch (viewType)
        {
            case ITEM_TYPE_ROW_1:
                RecyclerItemRowBinding recyclerItemRowBinding = DataBindingUtil.inflate(inflater,
                        R.layout.recycler_item_row, parent, false);
                return new movieViewHolder(recyclerItemRowBinding);


            case ITEM_TYPE_ROW_2:
                RecyclerItemRow2Binding recyclerItemRow2Binding = DataBindingUtil.inflate(inflater
                        , R.layout.recycler_item_row_2, parent, false);

                return new MovieOnlyImageViewHolder(recyclerItemRow2Binding);

            default:
                return null;
        }*/

        if (viewType == ITEM_TYPE_ROW_1)
            if (moviesTopRates != null)
            {
                RecyclerItemRowBinding recyclerItemRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.recycler_item_row, parent, false);

                return new movieViewHolder(recyclerItemRowBinding);
            }

        RecyclerItemRow2Binding recyclerItemRow2Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.recycler_item_row_2, parent, false);

        return new MovieOnlyImageViewHolder(recyclerItemRow2Binding);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {

        if (holder instanceof movieViewHolder)
        {
            if (movies != null && movies.size() > 0)
            {

                movieViewHolder viewHolder = (movieViewHolder) holder;
                TempValues = movies.get(position);
                viewHolder.recyclerItemRowBinding.setMovie(TempValues);
            }

        } else if (holder instanceof MovieOnlyImageViewHolder)
        {
            if (moviesTopRates != null && moviesTopRates.size() > 0)
            {

                MovieOnlyImageViewHolder onlyImageViewHolder = (MovieOnlyImageViewHolder) holder;
                TempValuesOne = moviesTopRates.get(position);
                //onlyImageViewHolder.recyclerItemRow2Binding.setMovieTopRate(TempValuesOne);
            }

        }


    }


    @Override
    public int getItemCount()
    {
        if (movies != null)
            return movies.size();
        else if (moviesTopRates != null)
            return moviesTopRates.size();
        else
            return 0;
    }

    public class movieViewHolder extends RecyclerView.ViewHolder
    {
        private RecyclerItemRowBinding recyclerItemRowBinding;


        public movieViewHolder(@NonNull RecyclerItemRowBinding recyclerItemRowBinding)
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
                        TempValues = movies.get(position);

                        Intent intent = new Intent(v.getContext(), MovieActivity.class);
                        intent.putExtra("movie", TempValues);
                        v.getContext().startActivity(intent);

                    }
                }
            });


        }
    }


    public class MovieOnlyImageViewHolder extends RecyclerView.ViewHolder
    {
        private RecyclerItemRow2Binding recyclerItemRow2Binding;

        public MovieOnlyImageViewHolder(@NonNull RecyclerItemRow2Binding recyclerItemRow2Binding)
        {
            super(recyclerItemRow2Binding.getRoot());
            this.recyclerItemRow2Binding = recyclerItemRow2Binding;

            recyclerItemRow2Binding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                }
            });

        }

    }


    public void setRecyclerItemRowBinding(RecyclerItemRowBinding recyclerItemRowBinding)
    {
        this.recyclerItemRowBinding = recyclerItemRowBinding;
    }

    public void setRecyclerItemRow2Binding(RecyclerItemRow2Binding recyclerItemRow2Binding)
    {
        this.recyclerItemRow2Binding = recyclerItemRow2Binding;
    }
}
