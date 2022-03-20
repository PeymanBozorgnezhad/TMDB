package com.example.mytmdbclient.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreDBResponse implements Parcelable
    {

        @SerializedName("genres")
        @Expose
        private List<Genre> genres = null;
        public final static Parcelable.Creator<GenreDBResponse> CREATOR = new Creator<GenreDBResponse>() {


            @SuppressWarnings({
                    "unchecked"
            })
            public GenreDBResponse createFromParcel(Parcel in) {
                return new GenreDBResponse(in);
            }

            public GenreDBResponse[] newArray(int size) {
                return (new GenreDBResponse[size]);
            }

        }
                ;

        protected GenreDBResponse(Parcel in) {
            in.readList(this.genres, (com.example.mytmdbclient.model.Genre.class.getClassLoader()));
        }

        public GenreDBResponse() {
        }

        public List<Genre> getGenres() {
            return genres;
        }

        public void setGenres(List<Genre> genres) {
            this.genres = genres;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(genres);
        }

        public int describeContents() {
            return 0;
        }

    }


