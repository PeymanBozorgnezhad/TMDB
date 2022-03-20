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
import com.example.mytmdbclient.databinding.RvItemRowTvAiringBinding;
import com.example.mytmdbclient.model.TvAiringToday;
import com.example.mytmdbclient.view.TvAiringMovieActivity;

import java.util.List;

public class TvAiring_RecyclerAdapter extends RecyclerView.Adapter<TvAiring_RecyclerAdapter.tvAiringViewHolder>
{

    private List<TvAiringToday> tvAiringTodays;
    private Context mContext;
    private TvAiringToday TempValue = null;

    private RvItemRowTvAiringBinding rvItemRowTvAiringBinding;

    public TvAiring_RecyclerAdapter(List<TvAiringToday> tvAiringTodays, Context mContext)
    {
        this.tvAiringTodays = tvAiringTodays;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public tvAiringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RvItemRowTvAiringBinding rvItemRowTvAiringBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.rv_item_row_tv_airing, parent, false);

        return new tvAiringViewHolder(rvItemRowTvAiringBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull tvAiringViewHolder holder, int position)
    {
        TempValue = tvAiringTodays.get(position);
        holder.rvItemRowTvAiringBinding.setTvAiring(TempValue);

    }

    @Override
    public int getItemCount()
    {
        if (tvAiringTodays != null)
            return tvAiringTodays.size();
        else
            return 0;
    }

    public class tvAiringViewHolder extends RecyclerView.ViewHolder
    {
        private RvItemRowTvAiringBinding rvItemRowTvAiringBinding;

        public tvAiringViewHolder(@NonNull RvItemRowTvAiringBinding rvItemRowTvAiringBinding)
        {
            super(rvItemRowTvAiringBinding.getRoot());
            this.rvItemRowTvAiringBinding = rvItemRowTvAiringBinding;

            rvItemRowTvAiringBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position;
                    position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {
                        TempValue = tvAiringTodays.get(position);

                        Intent intent = new Intent(mContext, TvAiringMovieActivity.class);
                        intent.putExtra("TvAiring", TempValue);


                        //TODO : lets create the animation
                        ImageView imageView = rvItemRowTvAiringBinding.itemTvAiringImg;
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                                imageView, mContext.getString(R.string.sharedElementName));

                        mContext.startActivity(intent, options.toBundle());

                    }
                }
            });
        }
    }

    public void setRvItemRowTvAiringBinding(RvItemRowTvAiringBinding rvItemRowTvAiringBinding)
    {
        this.rvItemRowTvAiringBinding = rvItemRowTvAiringBinding;
    }
}
