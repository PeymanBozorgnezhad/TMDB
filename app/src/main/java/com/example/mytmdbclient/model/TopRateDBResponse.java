package com.example.mytmdbclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopRateDBResponse implements Parcelable
{


    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalMoviesTopRates;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<MoviesTopRate> MoviesTopRates = null;
    public final static Creator<TopRateDBResponse> CREATOR = new Creator<TopRateDBResponse>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public TopRateDBResponse createFromParcel(Parcel in)
        {
            return new TopRateDBResponse(in);
        }

        public TopRateDBResponse[] newArray(int size)
        {
            return (new TopRateDBResponse[size]);
        }

    };

    protected TopRateDBResponse(Parcel in)
    {
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalMoviesTopRates = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.MoviesTopRates, (com.example.mytmdbclient.model.MoviesTopRate.class.getClassLoader()));
    }

    public TopRateDBResponse()
    {
    }

    public Integer getPage()
    {
        return page;
    }

    public void setPage(Integer page)
    {
        this.page = page;
    }

    public Integer getTotalMoviesTopRates()
    {
        return totalMoviesTopRates;
    }

    public void setTotalMoviesTopRates(Integer totalMoviesTopRates)
    {
        this.totalMoviesTopRates = totalMoviesTopRates;
    }

    public Integer getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages)
    {
        this.totalPages = totalPages;
    }

    public List<MoviesTopRate> getMoviesTopRates()
    {
        return MoviesTopRates;
    }

    public void setMoviesTopRates(List<MoviesTopRate> MoviesTopRates)
    {
        this.MoviesTopRates = MoviesTopRates;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(page);
        dest.writeValue(totalMoviesTopRates);
        dest.writeValue(totalPages);
        dest.writeList(MoviesTopRates);
    }

    public int describeContents()
    {
        return 0;
    }

}


