package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastDBResponse implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;
    @SerializedName("crew")
    @Expose
    private List<Crew> crew = null;
    public final static Parcelable.Creator<CastDBResponse> CREATOR = new Creator<CastDBResponse>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public CastDBResponse createFromParcel(Parcel in)
        {
            return new CastDBResponse(in);
        }

        public CastDBResponse[] newArray(int size)
        {
            return (new CastDBResponse[size]);
        }

    };

    protected CastDBResponse(Parcel in)
    {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.cast, (com.example.mytmdbclient.model.Cast.class.getClassLoader()));
        in.readList(this.crew, (com.example.mytmdbclient.model.Crew.class.getClassLoader()));
    }

    public CastDBResponse()
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

    public List<Cast> getCast()
    {
        return cast;
    }

    public void setCast(List<Cast> cast)
    {
        this.cast = cast;
    }

    public List<Crew> getCrew()
    {
        return crew;
    }

    public void setCrew(List<Crew> crew)
    {
        this.crew = crew;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(id);
        dest.writeList(cast);
        dest.writeList(crew);
    }

    public int describeContents()
    {
        return 0;
    }

}


