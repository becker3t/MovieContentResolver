package com.example.android.moviecontentresolver.MovieDisplay;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.UserDictionary;
import android.util.Log;

import com.example.android.moviecontentresolver.Objects.MovieData;
import com.example.android.moviecontentresolver.Objects.MovieUriContract;

import java.util.ArrayList;

/**
 * Created by Android on 6/17/2017.
 */

public class MoviePresenter implements MovieContentProviderContract.Presenter {

    public static final String TAG = MoviePresenter.class.getSimpleName() + "_TAG";

    public static final String CONTENT_AUTHORITY = "com.mac.training.movieapp";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_GENRE = "genre";

    private MovieContentProviderContract.View view;
    private Context context;
    private ArrayList<MovieData> movieList;
    private ContentResolver contentResolver;

    public MoviePresenter(MovieContentProviderContract.View view, Context context) {
        this.view = view;
        this.context = context;
        contentResolver = context.getContentResolver();
    }

    public void populateTables() {
        putGenreData("Action");
        putGenreData("Horror");
        putGenreData("Comedy");
        putGenreData("Mystery");

        putMovieData("Titanic", "April 1, 1998", "Comedy");
        putMovieData("50 First Dates", "April 1, 2006", "Horror");
        putMovieData("Dodgeball", "April 1, 2004", "Mystery");
        putMovieData("The Notebook", "April 1, 2003", "Action");
    }

    @Override
    public void getMovieContentData() {
        Log.d(TAG, "getMovieContentData: ");

        //query movie data first
        Cursor movieCursor = queryMovieData();
        int genreID = -1;

        movieCursor.close();

        //query genre data next, using the GenreId in Movie Data
        Cursor genreCursor = queryGenreData(genreID);

        genreCursor.close();
    }

//    public void createGenreTable() {
//
//    }
//
//    public void createMovieTable() {
//        contentResolver.
//    }

    public void putGenreData(String genreName) {
        ContentValues genreValues = new ContentValues();
        genreValues.put(MovieUriContract.GenreEntry.COLUMN_NAME, genreName);
        contentResolver.update(
                MovieUriContract.GenreEntry.CONTENT_URI,
                genreValues,
                null,
                null);
    }

    public void putMovieData(String movieName, String movieDate, String genre) {
        //get _ID of genre string
        String[] columns = {MovieUriContract.GenreEntry._ID, MovieUriContract.GenreEntry.COLUMN_NAME};
        Cursor cursor = contentResolver.query(
                MovieUriContract.GenreEntry.CONTENT_URI,
                columns,
                null,
                null,
                null
        );

        int genreId = -1;
        if(cursor.moveToFirst()) {
            genreId = cursor.getColumnIndex(genre);
        }
        cursor.close();

        if(genreId >= 0) {
            ContentValues movieValues = new ContentValues();
            movieValues.put(MovieUriContract.MovieEntry.COLUMN_NAME, movieName);
            movieValues.put(MovieUriContract.MovieEntry.COLUMN_RELEASE_DATE, movieDate);
            movieValues.put(MovieUriContract.MovieEntry.COLUMN_GENRE, genreId);
            contentResolver.update(
                    MovieUriContract.GenreEntry.CONTENT_URI,
                    movieValues,
                    null,
                    null);
        }
        else {
            view.setErrorMessage();
        }
    }

    public Cursor queryGenreData(int index) {
        String[] columns = {MovieUriContract.GenreEntry.COLUMN_NAME};
        return contentResolver.query(
                MovieUriContract.GenreEntry.CONTENT_URI,
                columns,
                "WHERE _ID == " + index,
                null,
                null
        );
    }

    public Cursor queryMovieData() {
        String[] columns = {MovieUriContract.MovieEntry.COLUMN_NAME, MovieUriContract.MovieEntry.COLUMN_GENRE, MovieUriContract.MovieEntry.COLUMN_RELEASE_DATE};

        return contentResolver.query(
                MovieUriContract.MovieEntry.CONTENT_URI,
                columns,
                null,
                null,
                null
        );
    }
}
