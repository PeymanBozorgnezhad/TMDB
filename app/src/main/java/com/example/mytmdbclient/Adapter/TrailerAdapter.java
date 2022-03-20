package com.example.mytmdbclient.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ItemRowTrailerBinding;
import com.example.mytmdbclient.model.Trailer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>
{

    private List<Trailer> trailerList;
    private Context mContext;
    private Trailer trailerTemp = null;
    private ItemRowTrailerBinding itemRowTrailerBinding;

    public TrailerAdapter(List<Trailer> trailerList, Context mContext)
    {
        this.trailerList = trailerList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemRowTrailerBinding itemRowTrailerBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_row_trailer, parent, false);
        return new TrailerViewHolder(itemRowTrailerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position)
    {

        trailerTemp = trailerList.get(position);

        if (trailerList.size()>0)
        holder.itemRowTrailerBinding.setTrailer(trailerTemp);
        // Get key of the trailer
        String key = trailerTemp.getKey();


        // Get url of the trailer
        String url = mContext.getString(R.string.youtube_url) + key;
        // Get id of the thumbnails
        String imageId = getYouTubeId(url);
        Log.d("imageId", imageId);
        // Load the thumbnails into ImageView
        Glide.with(mContext)
                .load(mContext.getString(R.string.thumbnail_firstPart) + imageId + mContext.getString(R.string.thumbnail_secondPart))
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imgBackDropTrailer);


    }

    @Override
    public int getItemCount()
    {
        if (trailerList != null)
            return trailerList.size();
        else
            return 0;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder
    {
        private ItemRowTrailerBinding itemRowTrailerBinding;
        private ImageView imgBackDropTrailer;


        public TrailerViewHolder(@NonNull ItemRowTrailerBinding itemRowTrailerBinding)
        {
            super(itemRowTrailerBinding.getRoot());
            this.itemRowTrailerBinding = itemRowTrailerBinding;

            imgBackDropTrailer = itemRowTrailerBinding.imgBackDropTrailer;

            itemRowTrailerBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    int position;
                    position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {
                        trailerTemp = trailerList.get(position);

                        String video_id = trailerList.get(position).getKey();
                        String url = v.getContext().getString(R.string.youtube_url) + video_id;

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));//==>> "vnd.youtube:" + video_id
                        intent.putExtra("VIDEO_ID", video_id);

                        if (intent.resolveActivity(v.getContext().getPackageManager()) != null)
                        {
                            v.getContext().startActivity(intent);
                            Toast.makeText(mContext, "You Clicked" + trailerTemp.getName(), Toast.LENGTH_SHORT).show();
                        }


                    }
                }
            });
        }
    }

    private String getYouTubeId(String youTubeUrl)
    {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find())
        {
            return matcher.group();
        } else
        {
            return "error";
        }
    }

}
