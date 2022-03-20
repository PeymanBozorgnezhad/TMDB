package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.mytmdbclient.BR;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Crew extends BaseObservable implements Parcelable
{

    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("department")
    @Expose
    private String department;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("job")
    @Expose
    private String job;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    public final static Parcelable.Creator<Crew> CREATOR = new Creator<Crew>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public Crew createFromParcel(Parcel in)
        {
            return new Crew(in);
        }

        public Crew[] newArray(int size)
        {
            return (new Crew[size]);
        }

    };

    protected Crew(Parcel in)
    {
        this.creditId = ((String) in.readValue((String.class.getClassLoader())));
        this.department = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.job = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Crew()
    {
    }

    @Bindable
    public String getCreditId()
    {
        return creditId;
    }

    public void setCreditId(String creditId)
    {
        this.creditId = creditId;
        notifyPropertyChanged(BR.creditId);
    }

    @Bindable
    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
        notifyPropertyChanged(BR.department);
    }

    @Bindable
    public Integer getGender()
    {
        return gender;
    }

    public void setGender(Integer gender)
    {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getJob()
    {
        return job;
    }

    public void setJob(String job)
    {
        this.job = job;
        notifyPropertyChanged(BR.job);
    }

    @Bindable
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getProfilePath()
    {
        return profilePath;
    }

    public void setProfilePath(String profilePath)
    {
        this.profilePath = profilePath;
        notifyPropertyChanged(BR.profilePath);
    }

    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeValue(creditId);
        dest.writeValue(department);
        dest.writeValue(gender);
        dest.writeValue(id);
        dest.writeValue(job);
        dest.writeValue(name);
        dest.writeValue(profilePath);
    }

    public int describeContents()
    {
        return 0;
    }

}


