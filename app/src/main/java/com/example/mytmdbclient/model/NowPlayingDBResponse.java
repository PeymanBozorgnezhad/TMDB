package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NowPlayingDBResponse implements Parcelable
{

    @SerializedName("results")
    @Expose
    private List<NowPlaying> results = null;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("dates")
    @Expose
    private DatesNowPlaying dates;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    public final static Parcelable.Creator<NowPlayingDBResponse> CREATOR = new Creator<NowPlayingDBResponse>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public NowPlayingDBResponse createFromParcel(Parcel in)
        {
            return new NowPlayingDBResponse(in);
        }

        public NowPlayingDBResponse[] newArray(int size)
        {
            return (new NowPlayingDBResponse[size]);
        }

    };

    protected NowPlayingDBResponse(Parcel in)
    {
        in.readList(this.results, (com.example.mytmdbclient.model.NowPlaying.class.getClassLoader()));
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.dates = ((DatesNowPlaying) in.readValue((DatesNowPlaying.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public NowPlayingDBResponse()
    {
    }

    public List<NowPlaying> getResults()
    {
        return results;
    }

    public void setResults(List<NowPlaying> results)
    {
        this.results = results;
    }

    public Integer getPage()
    {
        return page;
    }

    public void setPage(Integer page)
    {
        this.page = page;
    }

    public Integer getTotalResults()
    {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults)
    {
        this.totalResults = totalResults;
    }

    public DatesNowPlaying getDates()
    {
        return dates;
    }

    public void setDates(DatesNowPlaying dates)
    {
        this.dates = dates;
    }

    public Integer getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages)
    {
        this.totalPages = totalPages;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeList(results);
        dest.writeValue(page);
        dest.writeValue(totalResults);
        dest.writeValue(dates);
        dest.writeValue(totalPages);
    }

    public int describeContents()
    {
        return 0;
    }

}

