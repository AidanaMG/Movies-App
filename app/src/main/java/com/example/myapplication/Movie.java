package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Movie implements Parcelable {

    public static final String  TMDB_IMAGE_PATH = "https://image.tmdb.org/t/p/w500";
    public static final String qw = "https://www.themoviedb.org/movie/";
    private String link;

    private String title;

    @SerializedName("id")
    public int id;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("overview")
    private String description;

    @SerializedName("backdrop_path")
    private String backdrop;

    @SerializedName("original_language")
    private String language;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("genre_ids")
    private ArrayList<String> genreIds = new ArrayList<String>();

    public Movie() { }

    public ArrayList <String> getGenreIds()
    {
        ListIterator<String> iterator = genreIds.listIterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            switch(next)
            {
                case "28": iterator.set("Action");break;
                case "12": iterator.set("Adventure");break;
                case "16": iterator.set("Animation");break;
                case "35": iterator.set("Comedy");break;
                case "80": iterator.set("Crime");break;
                case "99": iterator.set("Documentary");break;
                case "18": iterator.set("Drama");break;
                case "10751": iterator.set("Family");break;
                case "14": iterator.set("Fantasy");break;
                case "36": iterator.set("History");break;
                case "27": iterator.set("Horror");break;
                case "10402": iterator.set("Music");break;
                case "9648": iterator.set("Mystery");break;
                case "10749": iterator.set("Romance");break;
                case "878": iterator.set("Science Fiction");break;
                case "10770": iterator.set("TV Movie");break;
                case "53": iterator.set("Thriller");break;
                case "10752": iterator.set("War");break;
                case "37": iterator.set("Western");break;
            } } return genreIds;}


   protected Movie(Parcel in) {
        title = in.readString();
        poster = in.readString();
        description = in.readString();
        backdrop = in.readString();
       language = in.readString();
       releaseDate = in.readString();
       voteAverage = in.readDouble();
        in.readStringList(genreIds);
        id = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int  getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        String result = TMDB_IMAGE_PATH + poster;
        return result;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdrop() {
        return TMDB_IMAGE_PATH  + backdrop;
    }
    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String  getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    public String  getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double  getVoteAverage() { return voteAverage; }
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String  getLink() { return qw + getId(); }

    public void setLink(String link) {
        this.link = link;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(description);
        parcel.writeString(backdrop);
        parcel.writeString(language);
        parcel.writeString(releaseDate);
        parcel.writeDouble(voteAverage);
        parcel.writeStringList(genreIds);
        parcel.writeInt(id);
    }


    public static class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {

            return results;
        }
    }


}
