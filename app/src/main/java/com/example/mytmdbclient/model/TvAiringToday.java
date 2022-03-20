package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.example.mytmdbclient.BR;
import com.example.mytmdbclient.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TvAiringToday extends BaseObservable implements Parcelable
{

    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = new ArrayList<>();
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("origin_country")
    @Expose
    private List<String> originCountry = new ArrayList<>();
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @BindingAdapter({"backdropPath"})
    public static void loadingTvAiring(ImageView imageView, String imgURL)
    {
        String imagePath = "https://image.tmdb.org/t/p/w500" + imgURL;

        Glide.with(imageView.getContext())
                .load(imagePath)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
/*
                .transform(new GlideBlurTransformation(imageView.getContext()))
*/
                .into(imageView);

    }

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @BindingAdapter({"posterPath"})
    public static void LoadImageTvAiring(ImageView imageView, String imageURL)
    {
        String ImagePath = "https://image.tmdb.org/t/p/w500" + imageURL;

        Glide.with(imageView.getContext())
                .load(ImagePath)
                .placeholder(R.drawable.loading)

                /* .apply(new RequestOptions().transforms(new CenterCrop(),

                         new RoundedCorners((int) UiUtils.dipToPixels(imageView.getContext(), 8))))*/
                /*.override(700,350)*/

                //.transform(new GlideBlurTransformation(imageView.getContext()))
                .error(R.drawable.error)
                .into(imageView);
    }


    public final static Parcelable.Creator<TvAiringToday> CREATOR = new Creator<TvAiringToday>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public TvAiringToday createFromParcel(Parcel in)
        {
            return new TvAiringToday(in);
        }

        public TvAiringToday[] newArray(int size)
        {
            return (new TvAiringToday[size]);
        }

    };

    protected TvAiringToday(Parcel in)
    {
        this.originalName = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.genreIds, (java.lang.Integer.class.getClassLoader()));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
        in.readList(this.originCountry, (java.lang.String.class.getClassLoader()));
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
    }

    public TvAiringToday()
    {
    }

    @Bindable
    public String getOriginalName()
    {
        return originalName;
    }

    public void setOriginalName(String originalName)
    {
        this.originalName = originalName;
        notifyPropertyChanged(BR.originalName);
    }

    @Bindable
    public List<Integer> getGenreIds()
    {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds)
    {
        this.genreIds = genreIds;
        notifyPropertyChanged(BR.genreIds);
    }

    @Bindable
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public Double getPopularity()
    {
        return popularity;
    }

    public void setPopularity(Double popularity)
    {
        this.popularity = popularity;
        notifyPropertyChanged(BR.popularity);
    }

    @Bindable
    public List<String> getOriginCountry()
    {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry)
    {
        this.originCountry = originCountry;
        notifyPropertyChanged(BR.originCountry);
    }

    @Bindable
    public Integer getVoteCount()
    {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount)
    {
        this.voteCount = voteCount;
        notifyPropertyChanged(BR.voteCount);
    }

    @Bindable
    public String getFirstAirDate()
    {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate)
    {
        this.firstAirDate = firstAirDate;
        notifyPropertyChanged(BR.firstAirDate);
    }

    @Bindable
    public String getBackdropPath()
    {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath)
    {
        this.backdropPath = backdropPath;
        notifyPropertyChanged(BR.backdropPath);
    }

    @Bindable
    public String getOriginalLanguage()
    {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage)
    {
        this.originalLanguage = originalLanguage;
        notifyPropertyChanged(BR.originalLanguage);
    }

    @Bindable
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public Double getVoteAverage()
    {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage)
    {
        this.voteAverage = voteAverage;
        notifyPropertyChanged(BR.voteAverage);
    }

    @Bindable
    public String getOverview()
    {
        return overview;
    }

    public void setOverview(String overview)
    {
        this.overview = overview;
        notifyPropertyChanged(BR.overview);
    }

    @Bindable
    public String getPosterPath()
    {
        return posterPath;
    }

    public void setPosterPath(String posterPath)
    {
        this.posterPath = posterPath;
        notifyPropertyChanged(BR.posterPath);
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(originalName);
        dest.writeList(genreIds);
        dest.writeValue(name);
        dest.writeValue(popularity);
        dest.writeList(originCountry);
        dest.writeValue(voteCount);
        dest.writeValue(firstAirDate);
        dest.writeValue(backdropPath);
        dest.writeValue(originalLanguage);
        dest.writeValue(id);
        dest.writeValue(voteAverage);
        dest.writeValue(overview);
        dest.writeValue(posterPath);
    }

    public int describeContents()
    {
        return 0;
    }

    public static final DiffUtil.ItemCallback<TvAiringToday> CALLBACK = new DiffUtil.ItemCallback<TvAiringToday>()
    {
        @Override
        public boolean areItemsTheSame(@NonNull TvAiringToday oldItem, @NonNull TvAiringToday newItem)
        {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull TvAiringToday oldItem, @NonNull TvAiringToday newItem)
        {
            return true;
        }
    };


}




