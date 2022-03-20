package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.mytmdbclient.BR;
import com.example.mytmdbclient.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import de.hdodenhof.circleimageview.CircleImageView;

public class Cast extends BaseObservable implements Parcelable
{

    @SerializedName("cast_id")
    @Expose
    private Integer castId;
    @SerializedName("character")
    @Expose
    private String character;
    @SerializedName("credit_id")
    @Expose
    private String creditId;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    @BindingAdapter({"profilePath"})
    public static void LoadImageCast(CircleImageView circleImageView, String imageURL)
    {
        String imagePath = "https://image.tmdb.org/t/p/w500" + imageURL;

        Glide.with(circleImageView.getContext())
                .load(imagePath)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(circleImageView);
    }


    public final static Parcelable.Creator<Cast> CREATOR = new Creator<Cast>()
    {


        @SuppressWarnings({
                "unchecked"
        })
        public Cast createFromParcel(Parcel in)
        {
            return new Cast(in);
        }

        public Cast[] newArray(int size)
        {
            return (new Cast[size]);
        }

    };

    protected Cast(Parcel in)
    {
        this.castId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.character = ((String) in.readValue((String.class.getClassLoader())));
        this.creditId = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.order = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Cast()
    {
    }

    @Bindable
    public Integer getCastId()
    {
        return castId;
    }

    public void setCastId(Integer castId)
    {
        this.castId = castId;
        notifyPropertyChanged(BR.castId);
    }

    @Bindable
    public String getCharacter()
    {
        return character;
    }

    public void setCharacter(String character)
    {
        this.character = character;
        notifyPropertyChanged(BR.character);
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
    public Integer getOrder()
    {
        return order;
    }

    public void setOrder(Integer order)
    {
        this.order = order;
        notifyPropertyChanged(BR.order);
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
        dest.writeValue(castId);
        dest.writeValue(character);
        dest.writeValue(creditId);
        dest.writeValue(gender);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(order);
        dest.writeValue(profilePath);
    }

    public int describeContents()
    {
        return 0;
    }

}

