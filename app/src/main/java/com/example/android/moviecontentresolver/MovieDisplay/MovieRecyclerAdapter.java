package com.example.android.moviecontentresolver.MovieDisplay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.moviecontentresolver.Objects.MovieData;
import com.example.android.moviecontentresolver.R;

import java.util.ArrayList;

/**
 * Created by Android on 6/17/2017.
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private ArrayList<MovieData> movieList;

    public MovieRecyclerAdapter (ArrayList<MovieData> movieList) {
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.movieNameTv.setText(movieList.get(position).getName());
        holder.movieGenreTv.setText(movieList.get(position).getGenre());
        holder.movieDateTv.setText(movieList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieNameTv;
        TextView movieGenreTv;
        TextView movieDateTv;

        public ViewHolder(View v) {
            super(v);
            movieNameTv = (TextView) v.findViewById(R.id.movieNameRecycleTv);
            movieGenreTv = (TextView) v.findViewById(R.id.movieGenreRecycleTv);
            movieDateTv = (TextView) v.findViewById(R.id.movieDateRecycleTv);
        }
    }
}
