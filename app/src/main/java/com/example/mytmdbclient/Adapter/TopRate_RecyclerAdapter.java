package com.example.mytmdbclient.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.RecyclerItemRow2Binding;
import com.example.mytmdbclient.model.MoviesTopRate;
import com.example.mytmdbclient.view.TopRateMovieActivity;

import java.util.List;

public class TopRate_RecyclerAdapter extends RecyclerView.Adapter<TopRate_RecyclerAdapter.topRateMoviesViewHolder>
{

    private List<MoviesTopRate> moviesTopRates;
    private Context mContext;
    private MoviesTopRate TempValue = null;
    private RecyclerItemRow2Binding recyclerItemRow2Binding;

    public TopRate_RecyclerAdapter(List<MoviesTopRate> moviesTopRates, Context mContext)
    {
        this.moviesTopRates = moviesTopRates;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public topRateMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        RecyclerItemRow2Binding recyclerItemRow2Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.recycler_item_row_2, parent, false);

        return new topRateMoviesViewHolder(recyclerItemRow2Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull topRateMoviesViewHolder holder, int position)
    {
        TempValue = moviesTopRates.get(position);
        holder.recyclerItemRow2Binding.setTopRate(TempValue);

    }

    @Override
    public int getItemCount()
    {
        if (moviesTopRates != null)
            return moviesTopRates.size();
        else
            return 0;
    }

    public class topRateMoviesViewHolder extends RecyclerView.ViewHolder
    {
        private RecyclerItemRow2Binding recyclerItemRow2Binding;

        public topRateMoviesViewHolder(@NonNull RecyclerItemRow2Binding recyclerItemRow2Binding)
        {
            super(recyclerItemRow2Binding.getRoot());
            this.recyclerItemRow2Binding = recyclerItemRow2Binding;

            recyclerItemRow2Binding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    int position;
                    position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {
                        TempValue = moviesTopRates.get(position);

                        Intent intent = new Intent(mContext, TopRateMovieActivity.class);
                        intent.putExtra("movie_topRate", TempValue);
                        mContext.startActivity(intent);

                    }

                }
            });
        }
    }

    public void setRecyclerItemRow2Binding(RecyclerItemRow2Binding recyclerItemRow2Binding)
    {
        this.recyclerItemRow2Binding = recyclerItemRow2Binding;
    }
}
