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
import com.example.mytmdbclient.databinding.ItemRowMainTopRateBinding;
import com.example.mytmdbclient.model.MoviesTopRate;
import com.example.mytmdbclient.view.TopRateMovieActivity;

public class TopRateMainAdapter extends PagedListAdapter<MoviesTopRate, TopRateMainAdapter.topRateMainViewHolder>
{

    //private ArrayList<MoviesTopRate> moviesTopRates;
    private Context mContext;
    private MoviesTopRate TempValues = null;
    private ItemRowMainTopRateBinding itemRowMainTopRateBinding;

    public TopRateMainAdapter(/*ArrayList<MoviesTopRate> moviesTopRates,*/ Context mContext)
    {
        super(MoviesTopRate.CALLBACK);
        // this.moviesTopRates = moviesTopRates;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public topRateMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        ItemRowMainTopRateBinding itemRowMainTopRateBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_row_main_top_rate, parent, false);
        return new topRateMainViewHolder(itemRowMainTopRateBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull topRateMainViewHolder holder, int position)
    {

        TempValues = getItem(position);
        holder.itemRowMainTopRateBinding.setMovieTopRate(TempValues);

    }

   /* @Override
    public int getItemCount()
    {
        if (moviesTopRates != null)
            return moviesTopRates.size();
        else
            return 0;
    }*/

    public class topRateMainViewHolder extends RecyclerView.ViewHolder
    {
        private ItemRowMainTopRateBinding itemRowMainTopRateBinding;

        public topRateMainViewHolder(@NonNull ItemRowMainTopRateBinding itemRowMainTopRateBinding)
        {
            super(itemRowMainTopRateBinding.getRoot());
            this.itemRowMainTopRateBinding = itemRowMainTopRateBinding;

            itemRowMainTopRateBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position;
                    position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {

                        TempValues = getItem(position);

                        Intent intent = new Intent(mContext, TopRateMovieActivity.class);
                        intent.putExtra("movie_topRate", TempValues);


                        //TODO : lets create the animation
                        ImageView imageView = itemRowMainTopRateBinding.ivMovie;
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                                imageView, mContext.getApplicationContext().getString(R.string.sharedElementName));

                        mContext.startActivity(intent, options.toBundle());


                    }
                }
            });
        }
    }

    public void setItemRowMainTopRateBinding(ItemRowMainTopRateBinding itemRowMainTopRateBinding)
    {
        this.itemRowMainTopRateBinding = itemRowMainTopRateBinding;
    }
}
