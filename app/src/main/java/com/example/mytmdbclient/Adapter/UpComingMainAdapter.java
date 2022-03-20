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
import com.example.mytmdbclient.databinding.ItemRowMainUpcomingBinding;
import com.example.mytmdbclient.model.UpComing;
import com.example.mytmdbclient.view.UpComingMovieActivity;

public class UpComingMainAdapter extends PagedListAdapter<UpComing, UpComingMainAdapter.upComingMainViewHolder>
{

    //private ArrayList<MoviesTopRate> moviesTopRates;
    private Context mContext;
    private UpComing TempValues = null;
    private ItemRowMainUpcomingBinding itemRowMainUpcomingBinding;

    public UpComingMainAdapter(Context mContext)
    {
        super(UpComing.CALLBACK);
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public upComingMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        ItemRowMainUpcomingBinding itemRowMainUpcomingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_row_main_upcoming, parent, false);
        return new upComingMainViewHolder(itemRowMainUpcomingBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull upComingMainViewHolder holder, int position)
    {

        TempValues = getItem(position);
        holder.itemRowMainUpcomingBinding.setUpComing(TempValues);

    }


    public class upComingMainViewHolder extends RecyclerView.ViewHolder
    {
        private ItemRowMainUpcomingBinding itemRowMainUpcomingBinding;

        public upComingMainViewHolder(@NonNull ItemRowMainUpcomingBinding itemRowMainUpcomingBinding)
        {
            super(itemRowMainUpcomingBinding.getRoot());
            this.itemRowMainUpcomingBinding = itemRowMainUpcomingBinding;

            itemRowMainUpcomingBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position;
                    position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {

                        TempValues = getItem(position);

                        Intent intent = new Intent(mContext, UpComingMovieActivity.class);
                        intent.putExtra("movie_upComing", TempValues);


                        //TODO : lets create the animation
                        ImageView imageView = itemRowMainUpcomingBinding.ivMovie;
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                                imageView, mContext.getApplicationContext().getString(R.string.sharedElementName));

                        mContext.startActivity(intent, options.toBundle());


                    }
                }
            });
        }
    }

    public void setItemRowMainUpcomingBinding(ItemRowMainUpcomingBinding itemRowMainUpcomingBinding)
    {
        this.itemRowMainUpcomingBinding = itemRowMainUpcomingBinding;
    }
}
