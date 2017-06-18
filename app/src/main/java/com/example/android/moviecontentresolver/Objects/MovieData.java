package com.example.android.moviecontentresolver.Objects;

/**
 * Created by Android on 6/17/2017.
 */

public class MovieData {
    private String name;
    private String genre;
    private String date;

    public MovieData(String name, String genre, String date) {
        this.name = name;
        this.genre = genre;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
