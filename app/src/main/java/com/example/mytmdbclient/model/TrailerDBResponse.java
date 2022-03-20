package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerDBResponse implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Trailer> results = null;
    public final static Parcelable.Creator<TrailerDBResponse> CREATOR = new Creator<TrailerDBResponse>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public TrailerDBResponse createFromParcel(Parcel in)
        {
            return new TrailerDBResponse(in);
        }

        public TrailerDBResponse[] newArray(int size)
        {
            return (new TrailerDBResponse[size]);
        }

    };

    protected TrailerDBResponse(Parcel in)
    {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, (com.example.mytmdbclient.model.Trailer.class.getClassLoader()));
    }

    public TrailerDBResponse()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public List<Trailer> getResults()
    {
        return results;
    }

    public void setResults(List<Trailer> results)
    {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(id);
        dest.writeList(results);
    }

    public int describeContents()
    {
        return 0;
    }

}

