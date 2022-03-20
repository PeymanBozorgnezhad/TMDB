package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DatesNowPlaying implements Parcelable
{

    @SerializedName("maximum")
    @Expose
    private String maximum;
    @SerializedName("minimum")
    @Expose
    private String minimum;
    public final static Parcelable.Creator<DatesNowPlaying> CREATOR = new Creator<DatesNowPlaying>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public DatesNowPlaying createFromParcel(Parcel in)
        {
            return new DatesNowPlaying(in);
        }

        public DatesNowPlaying[] newArray(int size)
        {
            return (new DatesNowPlaying[size]);
        }

    };

    protected DatesNowPlaying(Parcel in)
    {
        this.maximum = ((String) in.readValue((String.class.getClassLoader())));
        this.minimum = ((String) in.readValue((String.class.getClassLoader())));
    }

    public DatesNowPlaying()
    {
    }

    public String getMaximum()
    {
        return maximum;
    }

    public void setMaximum(String maximum)
    {
        this.maximum = maximum;
    }

    public String getMinimum()
    {
        return minimum;
    }

    public void setMinimum(String minimum)
    {
        this.minimum = minimum;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(maximum);
        dest.writeValue(minimum);
    }

    public int describeContents()
    {
        return 0;
    }

}


