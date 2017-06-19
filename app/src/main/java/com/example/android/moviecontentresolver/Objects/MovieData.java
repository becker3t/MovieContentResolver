package com.example.android.moviecontentresolver.Objects;

/**
 * Created by Android on 6/17/2017.
 */

public class MovieData {
    private String name;
    private String genre;
    private String date;
    private int genreId;

    public MovieData() {
        //empty constructor
    }

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

    public int getGenreId() {
        return genreId;
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

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}
