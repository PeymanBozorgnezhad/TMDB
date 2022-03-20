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
import com.example.mytmdbclient.databinding.MovieListItemBinding;
import com.example.mytmdbclient.model.Movie;
import com.example.mytmdbclient.view.MovieActivity;


public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.movieViewHolder>
        /*public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.movieViewHolder>*/
{


    private Context context;
    // private ArrayList<Movie> movieArrayList;
    private LayoutInflater inflater = null;
    private Movie TempValues = null;
    /*private MovieListItemBinding movieListItemBinding*/;


    public MovieAdapter(Context context/*, ArrayList<Movie> movieArrayList*/)
    {
        super(Movie.CALLBACK);
        this.context = context;
        //this.movieArrayList = movieArrayList;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public movieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        MovieListItemBinding movieListItemBinding = DataBindingUtil.inflate(inflater, R.layout.movie_list_item,
                viewGroup, false);

        return new movieViewHolder(movieListItemBinding);



       /* View view = inflater.inflate(R.layout.movie_list_item, viewGroup, false);
        return new movieViewHolder(view);*/

    }

    @Override
    public void onBindViewHolder(@NonNull movieViewHolder movieViewHolder, int i)
    {

        /*TempValues = movieArrayList.get(i);*/

        TempValues = getItem(i);//position current(feli) movie ma ro load mikone

        /*movieViewHolder.movieTitle.setText(movieArrayList.get(i).getOriginalTitle());
        movieViewHolder.rate.setText(Double.toString(movieArrayList.get(i).getVoteAverage()));*/

        /*String imagePath = "https://image.tmdb.org/t/p/w500" + TempValues.getPosterPath();//ma bra inke address url asli ro ham b poster path khudemon ezaf konim bayad hatman uses of setPosterPath

        TempValues.setPosterPath(imagePath);*/


        movieViewHolder.movieListItemBinding.setMovie(TempValues);




       /* Glide.with(context)
                .load(imagePath)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(movieViewHolder.movieImage);*/


    }


   /* @Override
    public int getItemCount()
    {
        if (movieArrayList != null)

            return movieArrayList.size();
        else
            return 0;
    }*/

    public class movieViewHolder extends RecyclerView.ViewHolder
    {

        private MovieListItemBinding movieListItemBinding;

        /*public TextView movieTitle, rate;
        public ImageView movieImage;*/

        public movieViewHolder(@NonNull MovieListItemBinding movieListItemBinding)
        {
            super(movieListItemBinding.getRoot());

            this.movieListItemBinding = movieListItemBinding;

            movieListItemBinding.getRoot().setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position;
                    position = getAdapterPosition();


                    if (position != RecyclerView.NO_POSITION)
                    {

                        /*TempValues = movieArrayList.get(position);*/
                        TempValues = getItem(position);


                        //Intent intent = new Intent(context, ScrollingActivity.class);
                        Intent intent = new Intent(context, MovieActivity.class);
                        intent.putExtra("movie", TempValues);


                        //TODO : lets create the animation
                        ImageView imageView = movieListItemBinding.ivMovie;

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                                imageView, context.getApplicationContext().getString(R.string.sharedElementName));





                        /*private String getGenres(List <Integer> genreIds) {
                        List<String> movieGenres = new ArrayList<>();
                        for (Integer genreId : genreIds) {
                            for (Genre genre : allGenres) {
                                if (genre.getId() == genreId) {
                                    movieGenres.add(genre.getName());
                                    break;
                                }
                            }
                        }
                        return TextUtils.join(", ", movieGenres);
                    }*/


                        context.startActivity(intent, options.toBundle());


                    }

                    /*if (itemClickListener != null && position != RecyclerView.NO_POSITION)
                    {


                        itemClickListener.onItemClicked(TempValues);
                    }*/
                }
            });

            /*movieImage = (ImageView) itemView.findViewById(R.id.ivMovie);
            rate = (TextView) itemView.findViewById(R.id.tvRating);
            movieTitle = (TextView) itemView.findViewById(R.id.tvTitle);*/

           /* itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {

                        Movie selectedMovie = movieArrayList.get(position);

                        Intent intent = new Intent(context, MovieActivity.class);
                        intent.putExtra("movie", selectedMovie);
                        context.startActivity(intent);


                    }


                }
            });*/

        }

    }

   /* public void setMovieListItemBinding(MovieListItemBinding movieListItemBinding)
    {
        this.movieListItemBinding = movieListItemBinding;
    }*/
}
