package com.example.mytmdbclient.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytmdbclient.R;
import com.example.mytmdbclient.databinding.ItemRowSearchPopularBinding;
import com.example.mytmdbclient.model.Genre;
import com.example.mytmdbclient.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class SearchPopularAdapter extends RecyclerView.Adapter<SearchPopularAdapter.searchPopularViewHolder> /*implements Filterable*/
{

    private Context context;
    private Movie movieTemp;
    private List<Genre> genresAll;
    private ItemRowSearchPopularBinding itemRowSearchPopularBinding;

    // Declare an arrayList for movies
    private List<Movie> movieList;

    // Create a final private MovieAdapterOnClickHandler called mClickHandler
    private SearchAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface SearchAdapterOnClickHandler
    {
        void onClick(Movie movie);
    }

    public SearchPopularAdapter(List<Movie> searchedMovieList, Context context, List<Genre> genreList, SearchAdapterOnClickHandler clickHandler)
    {
        this.context = context;
        this.genresAll = genreList;
        this.clickHandler = clickHandler;
        this.movieList = searchedMovieList;
    }

    @NonNull
    @Override
    public searchPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ItemRowSearchPopularBinding itemRowSearchPopularBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_row_search_popular, parent, false);
        return new searchPopularViewHolder(itemRowSearchPopularBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull searchPopularViewHolder holder, int position)
    {

        movieTemp = movieList.get(position);
        holder.itemRowSearchPopularBinding.setMovie(movieTemp);
        holder.itemRowSearchPopularBinding.setGenre(getGenres(movieTemp.getGenreIds()));

    }

    @Override
    public int getItemCount()
    {
        if (movieList != null)
            return movieList.size();
        else
            return 0;
    }

    public void clear()
    {
        int size = movieList.size();
        movieList.clear();
        notifyItemRangeRemoved(0, size);
    }

    private String getGenres(List<Integer> genreIds)
    {
        List<String> movieGenres = new ArrayList<>();
        for (Integer genreId : genreIds)
        {
            for (Genre genre : genresAll)
            {
                if (genre.getId().equals(genreId))
                {
                    movieGenres.add(genre.getName());
                    break;
                }
            }
        }
        return TextUtils.join(", ", movieGenres);
    }

    /*@Override
    public Filter getFilter()
    {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {

            String query = constraint.toString();
            PagedList<Movie> filtered = null;

            if (query.isEmpty())
            {
                filtered = getCurrentList();
            } else
            {
                String filterPattern = query.toLowerCase().trim();
                for (Movie movie : filtered)
                {
                    if (movie.getTitle().toLowerCase().contains(filterPattern))
                    {
                        filtered.add(movie);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.count = filtered.size();
            results.values = filtered;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            submitList((PagedList<Movie>) results.values);
            notifyDataSetChanged();
        }
    };*/

    public class searchPopularViewHolder extends RecyclerView.ViewHolder
    {
        private ItemRowSearchPopularBinding itemRowSearchPopularBinding;

        public searchPopularViewHolder(@NonNull ItemRowSearchPopularBinding itemRowSearchPopularBinding)
        {
            super(itemRowSearchPopularBinding.getRoot());
            this.itemRowSearchPopularBinding = itemRowSearchPopularBinding;

            itemRowSearchPopularBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    int position;
                    position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {
                        movieTemp = movieList.get(position);
                        // Send movie through click
                        clickHandler.onClick(movieTemp);
                    }

                }
            });
        }
    }

    public void setItemRowSearchPopularBinding(ItemRowSearchPopularBinding itemRowSearchPopularBinding)
    {
        this.itemRowSearchPopularBinding = itemRowSearchPopularBinding;
    }
}
