package com.example.mytmdbclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class TvAppendDBResponse implements Parcelable
{

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;


    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;


    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;


    @SerializedName("id")
    @Expose
    private Integer id;


    @SerializedName("name")
    @Expose
    private String name;


        /*@SerializedName("networks")
        @Expose
        private List<Network> networks = null;*/


    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("original_name")
    @Expose
    private String originalName;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("popularity")
    @Expose
    private Double popularity;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

        /*@SerializedName("production_companies")
        @Expose
        private List<ProductionCompany> productionCompanies = null;*/

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    @SerializedName("videos")
    @Expose
    private TrailerDBResponse videos;

    @SerializedName("credits")
    @Expose
    private CastDBResponse credits;
    public final static Parcelable.Creator<TvAppendDBResponse> CREATOR = new Creator<TvAppendDBResponse>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public TvAppendDBResponse createFromParcel(Parcel in)
        {
            return new TvAppendDBResponse(in);
        }

        public TvAppendDBResponse[] newArray(int size)
        {
            return (new TvAppendDBResponse[size]);
        }

    };

    protected TvAppendDBResponse(Parcel in)
    {
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        this.firstAirDate = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.genres, (com.example.mytmdbclient.model.Genre.class.getClassLoader()));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
//            in.readList(this.networks, (com.example.Network.class.getClassLoader()));
        this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.originalName = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        this.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.videos = ((TrailerDBResponse) in.readValue((TrailerDBResponse.class.getClassLoader())));
        this.credits = ((CastDBResponse) in.readValue((CastDBResponse.class.getClassLoader())));
    }

    public TvAppendDBResponse()
    {
    }

    public String getBackdropPath()
    {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath)
    {
        this.backdropPath = backdropPath;
    }


    public String getFirstAirDate()
    {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate)
    {
        this.firstAirDate = firstAirDate;
    }

    public List<Genre> getGenres()
    {
        return genres;
    }

    public void setGenres(List<Genre> genres)
    {
        this.genres = genres;
    }


    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


        /*public List<Network> getNetworks()
        {
            return networks;
        }

        public void setNetworks(List<Network> networks)
        {
            this.networks = networks;
        }*/


    public String getOriginalLanguage()
    {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage)
    {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalName()
    {
        return originalName;
    }

    public void setOriginalName(String originalName)
    {
        this.originalName = originalName;
    }

    public String getOverview()
    {
        return overview;
    }

    public void setOverview(String overview)
    {
        this.overview = overview;
    }

    public Double getPopularity()
    {
        return popularity;
    }

    public void setPopularity(Double popularity)
    {
        this.popularity = popularity;
    }

    public String getPosterPath()
    {
        return posterPath;
    }

    public void setPosterPath(String posterPath)
    {
        this.posterPath = posterPath;
    }


    public Double getVoteAverage()
    {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage)
    {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount()
    {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount)
    {
        this.voteCount = voteCount;
    }

    public TrailerDBResponse getVideos()
    {
        return videos;
    }

    public void setVideos(TrailerDBResponse videos)
    {
        this.videos = videos;
    }

    public CastDBResponse getCredits()
    {
        return credits;
    }

    public void setCredits(CastDBResponse credits)
    {
        this.credits = credits;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(backdropPath);
        dest.writeValue(firstAirDate);
        dest.writeList(genres);

        dest.writeValue(id);

        dest.writeValue(name);

        dest.writeValue(originalLanguage);
        dest.writeValue(originalName);
        dest.writeValue(overview);
        dest.writeValue(popularity);
        dest.writeValue(posterPath);

        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
        dest.writeValue(videos);
        dest.writeValue(credits);
    }

    public int describeContents()
    {
        return 0;
    }

}

