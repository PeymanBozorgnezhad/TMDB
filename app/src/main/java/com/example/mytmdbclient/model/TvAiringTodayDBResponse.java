package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvAiringTodayDBResponse implements Parcelable
{

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<TvAiringToday> results = null;
    public final static Parcelable.Creator<TvAiringTodayDBResponse> CREATOR = new Creator<TvAiringTodayDBResponse>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public TvAiringTodayDBResponse createFromParcel(Parcel in)
        {
            return new TvAiringTodayDBResponse(in);
        }

        public TvAiringTodayDBResponse[] newArray(int size)
        {
            return (new TvAiringTodayDBResponse[size]);
        }

    };

    protected TvAiringTodayDBResponse(Parcel in)
    {
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, (com.example.mytmdbclient.model.TvAiringToday.class.getClassLoader()));
    }

    public TvAiringTodayDBResponse()
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

    public Integer getTotalResults()
    {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults)
    {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages)
    {
        this.totalPages = totalPages;
    }

    public List<TvAiringToday> getResults()
    {
        return results;
    }

    public void setResults(List<TvAiringToday> results)
    {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(page);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
        dest.writeList(results);
    }

    public int describeContents()
    {
        return 0;
    }

}




