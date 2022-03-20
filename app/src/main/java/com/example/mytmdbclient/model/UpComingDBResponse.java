package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpComingDBResponse implements Parcelable
{

    @SerializedName("results")
    @Expose
    private List<UpComing> results = null;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    @SerializedName("dates")
    @Expose
    private DatesUpComing dates;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    public final static Parcelable.Creator<UpComingDBResponse> CREATOR = new Creator<UpComingDBResponse>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public UpComingDBResponse createFromParcel(Parcel in)
        {
            return new UpComingDBResponse(in);
        }

        public UpComingDBResponse[] newArray(int size)
        {
            return (new UpComingDBResponse[size]);
        }

    };

    protected UpComingDBResponse(Parcel in)
    {
        in.readList(this.results, (com.example.mytmdbclient.model.UpComing.class.getClassLoader()));
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.dates = ((DatesUpComing) in.readValue((DatesUpComing.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public UpComingDBResponse()
    {
    }

    public List<UpComing> getResults()
    {
        return results;
    }

    public void setResults(List<UpComing> results)
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

    public DatesUpComing getDates()
    {
        return dates;
    }

    public void setDates(DatesUpComing dates)
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

