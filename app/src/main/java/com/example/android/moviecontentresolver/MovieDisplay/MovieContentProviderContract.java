package com.example.android.moviecontentresolver.MovieDisplay;

import com.example.android.moviecontentresolver.Objects.MovieData;

import java.util.ArrayList;

/**
 * Created by Android on 6/17/2017.
 */

public interface MovieContentProviderContract {
    interface View {
        void updateMovieDataView(ArrayList<MovieData> data);

        void setErrorMessage();
    }

    interface Presenter {
        void getMovieContentData();
    }
}
