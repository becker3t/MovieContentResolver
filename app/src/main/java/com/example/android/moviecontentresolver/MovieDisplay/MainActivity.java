package com.example.android.moviecontentresolver.MovieDisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.android.moviecontentresolver.Objects.MovieData;
import com.example.android.moviecontentresolver.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieContentProviderContract.View {

    RecyclerView movieRecyclerView;
    MoviePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieRecyclerView = (RecyclerView) findViewById(R.id.movieRecyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter = new MoviePresenter(this, getApplicationContext());
        presenter.populateTables();
    }

    @Override
    public void updateMovieDataView(ArrayList<MovieData> data) {
        //create recycler adapter and set it
        MovieRecyclerAdapter adapter = new MovieRecyclerAdapter(data);
        movieRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setErrorMessage() {

    }
}
