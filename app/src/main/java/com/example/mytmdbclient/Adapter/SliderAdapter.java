package com.example.mytmdbclient.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ImageSliderLayoutItemBinding;
import com.example.mytmdbclient.model.Movie;
import com.example.mytmdbclient.view.MovieActivity;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.sliderAdapterVH>
{
    private Context mContext;
    private List<Movie> movieList = new ArrayList<>();
    private ImageSliderLayoutItemBinding imageSliderLayoutItemBinding;
    private Movie TempValues = null;

    public SliderAdapter(Context mContext, List<Movie> movieList)
    {
        this.mContext = mContext;
        this.movieList = movieList;

    }

    /*public void renewItems(List<Movie> movies)
    {
        this.movieList = movies;
        notifyDataSetChanged();
    }

    public void deleteItem(int position)
    {
        this.movieList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Movie movie)
    {
        this.movieList.add(movie);
        notifyDataSetChanged();
    }
*/

    @Override
    public sliderAdapterVH onCreateViewHolder(ViewGroup parent)
    {
        ImageSliderLayoutItemBinding imageSliderLayoutItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.image_slider_layout_item
                , parent, false);

        return new sliderAdapterVH(imageSliderLayoutItemBinding);
    }

    @Override
    public void onBindViewHolder(sliderAdapterVH viewHolder, int position)
    {


        TempValues = movieList.get(position);

        viewHolder.imageSliderLayoutItemBinding.setMovie(TempValues);

        viewHolder.imageSliderLayoutItemBinding.getRoot().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(mContext, MovieActivity.class);
                intent.putExtra("movie", movieList.get(position));
                mContext.startActivity(intent);

                Toast.makeText(mContext.getApplicationContext(), "This is item in position" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
       /*imageSliderLayoutItemBinding.getRoot().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(mContext.getApplicationContext(), "This is item in position" + position, Toast.LENGTH_SHORT).show();
            }
        });
*/


    @Override
    public int getCount()
    {
        if (movieList != null)
            return movieList.size();
        else
            return 0;
    }

    public class sliderAdapterVH extends SliderViewAdapter.ViewHolder
    {
        private ImageSliderLayoutItemBinding imageSliderLayoutItemBinding;


        public sliderAdapterVH(ImageSliderLayoutItemBinding imageSliderLayoutItemBinding)
        {
            super(imageSliderLayoutItemBinding.getRoot());
            this.imageSliderLayoutItemBinding = imageSliderLayoutItemBinding;
        }
    }

    public void setImageSliderLayoutItemBinding(ImageSliderLayoutItemBinding imageSliderLayoutItemBinding)
    {
        this.imageSliderLayoutItemBinding = imageSliderLayoutItemBinding;
    }
}
