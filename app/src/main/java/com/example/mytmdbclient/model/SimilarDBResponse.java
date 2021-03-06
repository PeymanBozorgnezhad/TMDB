package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimilarDBResponse implements Parcelable
{

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<SimilarMovies> results = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
    public final static Parcelable.Creator<SimilarDBResponse> CREATOR = new Creator<SimilarDBResponse>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public SimilarDBResponse createFromParcel(Parcel in)
        {
            return new SimilarDBResponse(in);
        }

        public SimilarDBResponse[] newArray(int size)
        {
            return (new SimilarDBResponse[size]);
        }

    };

    protected SimilarDBResponse(Parcel in)
    {
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, (com.example.mytmdbclient.model.SimilarMovies.class.getClassLoader()));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public SimilarDBResponse()
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

    public List<SimilarMovies> getResults()
    {
        return results;
    }

    public void setResults(List<SimilarMovies> results)
    {
        this.results = results;
    }

    public Integer getTotalPages()
    {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages)
    {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults()
    {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults)
    {
        this.totalResults = totalResults;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(page);
        dest.writeList(results);
        dest.writeValue(totalPages);
        dest.writeValue(totalResults);
    }

    public int describeContents()
    {
        return 0;
    }


}
