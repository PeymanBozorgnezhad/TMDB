package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LanguagesDBResponse implements Parcelable
{

    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("english_name")
    @Expose
    private String englishName;
    @SerializedName("name")
    @Expose
    private String name;
    public final static Parcelable.Creator<LanguagesDBResponse> CREATOR = new Creator<LanguagesDBResponse>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public LanguagesDBResponse createFromParcel(Parcel in)
        {
            return new LanguagesDBResponse(in);
        }

        public LanguagesDBResponse[] newArray(int size)
        {
            return (new LanguagesDBResponse[size]);
        }

    };

    protected LanguagesDBResponse(Parcel in)
    {
        this.iso6391 = ((String) in.readValue((String.class.getClassLoader())));
        this.englishName = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
    }

    public LanguagesDBResponse()
    {
    }

    public String getIso6391()
    {
        return iso6391;
    }

    public void setIso6391(String iso6391)
    {
        this.iso6391 = iso6391;
    }

    public String getEnglishName()
    {
        return englishName;
    }

    public void setEnglishName(String englishName)
    {
        this.englishName = englishName;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(iso6391);
        dest.writeValue(englishName);
        dest.writeValue(name);
    }

    public int describeContents()
    {
        return 0;
    }

}


