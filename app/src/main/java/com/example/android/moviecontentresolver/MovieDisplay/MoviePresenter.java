package com.example.android.moviecontentresolver.MovieDisplay;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
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
    private ContentProviderClient providerClient;

    public MoviePresenter(MovieContentProviderContract.View view, Context context) {
        this.view = view;
        this.context = context;
        providerClient = context.getContentResolver().acquireContentProviderClient(CONTENT_AUTHORITY);
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
        //query movie data first
        Cursor movieCursor = queryMovieData();

        ArrayList<MovieData> movieData = new ArrayList<>();

        if(movieCursor != null) {
            //do stuff
            while(movieCursor.moveToNext()) {
                MovieData movie = new MovieData();
                movie.setName(movieCursor.getString(movieCursor.getColumnIndex(MovieUriContract.MovieEntry.COLUMN_NAME)));
                movie.setDate(movieCursor.getString(movieCursor.getColumnIndex(MovieUriContract.MovieEntry.COLUMN_RELEASE_DATE)));
                movie.setGenreId(movieCursor.getInt(movieCursor.getColumnIndex(MovieUriContract.MovieEntry.COLUMN_GENRE)));
            }
            movieCursor.close();
        }

        //query genre data next, using the GenreId in Movie Data
        for (MovieData movie : movieData) {
            Cursor genreCursor = queryGenreData(movie.getGenreId());

            if(genreCursor != null) {
                //do stuff
                if (genreCursor.moveToFirst()) {
                    movie.setGenre(movieCursor.getString(movieCursor.getColumnIndex(MovieUriContract.GenreEntry.COLUMN_NAME)));
                }
                genreCursor.close();
            }
        }

        view.updateMovieDataView(movieData);
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
        try {
            providerClient.update(
                    MovieUriContract.GenreEntry.CONTENT_URI,
                    genreValues,
                    null,
                    null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void putMovieData(String movieName, String movieDate, String genre) {
        //get _ID of genre string
        String[] columns = {MovieUriContract.GenreEntry._ID, MovieUriContract.GenreEntry.COLUMN_NAME};

        try{
            Cursor cursor = providerClient.query(
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
                providerClient.update(
                        MovieUriContract.GenreEntry.CONTENT_URI,
                        movieValues,
                        null,
                        null);
            }
            else {
                view.setErrorMessage();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Cursor queryGenreData(int index) {
        String[] columns = {MovieUriContract.GenreEntry.COLUMN_NAME};

        Cursor returnCursor = null;

        try {
            returnCursor = providerClient.query(
                    MovieUriContract.GenreEntry.CONTENT_URI,
                    columns,
                    "WHERE _ID == " + index,
                    null,
                    null
            );
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return returnCursor;
    }

    public Cursor queryMovieData() {
        String[] columns = {MovieUriContract.MovieEntry.COLUMN_NAME, MovieUriContract.MovieEntry.COLUMN_GENRE, MovieUriContract.MovieEntry.COLUMN_RELEASE_DATE};

        Cursor returnCursor = null;

        try {
            returnCursor = providerClient.query(
                    MovieUriContract.MovieEntry.CONTENT_URI,
                    columns,
                    null,
                    null,
                    null
            );
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return returnCursor;
    }
}
