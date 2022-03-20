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
import com.example.mytmdbclient.databinding.ItemRowMainTvAiringBinding;
import com.example.mytmdbclient.model.TvAiringToday;
import com.example.mytmdbclient.view.TvAiringMovieActivity;

public class TvAiringMainAdapter extends PagedListAdapter<TvAiringToday, TvAiringMainAdapter.tvAiringMainViewHolder>
{

    private Context mContext;
    private TvAiringToday TempValues = null;
    private ItemRowMainTvAiringBinding itemRowMainTvAiringBinding;

    public TvAiringMainAdapter(Context mContext)
    {
        super(TvAiringToday.CALLBACK);
        this.mContext = mContext;

    }


    @NonNull
    @Override
    public tvAiringMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        ItemRowMainTvAiringBinding itemRowMainTvAiringBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.item_row_main_tv_airing, parent, false);
        return new tvAiringMainViewHolder(itemRowMainTvAiringBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull tvAiringMainViewHolder holder, int position)
    {
        TempValues = getItem(position);
        holder.itemRowMainTvAiringBinding.setTvAiring(TempValues);

    }

    public class tvAiringMainViewHolder extends RecyclerView.ViewHolder
    {
        private ItemRowMainTvAiringBinding itemRowMainTvAiringBinding;

        public tvAiringMainViewHolder(@NonNull ItemRowMainTvAiringBinding itemRowMainTvAiringBinding)
        {
            super(itemRowMainTvAiringBinding.getRoot());

            this.itemRowMainTvAiringBinding = itemRowMainTvAiringBinding;
            itemRowMainTvAiringBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position;
                    position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {

                        TempValues = getItem(position);

                        Intent intent = new Intent(mContext, TvAiringMovieActivity.class);
                        intent.putExtra("TvAiring", TempValues);


                        //TODO : lets create the animation
                        ImageView imageView = itemRowMainTvAiringBinding.ivMovie;
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                                imageView, mContext.getApplicationContext().getString(R.string.sharedElementName));

                        mContext.startActivity(intent, options.toBundle());


                    }
                }
            });
        }
    }

    public void setItemRowMainTvAiringBinding(ItemRowMainTvAiringBinding itemRowMainTvAiringBinding)
    {
        this.itemRowMainTvAiringBinding = itemRowMainTvAiringBinding;
    }
}
