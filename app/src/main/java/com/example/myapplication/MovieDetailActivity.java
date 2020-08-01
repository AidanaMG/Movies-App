package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {
   public static final String EXTRA_MOVIE = "movie";

    private Movie mMovie;
    ImageView backdrop;
    ImageView poster;
    TextView title;
    TextView description;
    TextView language;
    TextView releaseDate;
    TextView genres;
    TextView vote_average;
    Button watch;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }


        backdrop = (ImageView) findViewById(R.id.backdrop);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_description);
        poster = (ImageView) findViewById(R.id.movie_poster);
        language = (TextView) findViewById(R.id.movie_language);
        releaseDate = (TextView) findViewById(R.id.movie_releaseDate);
        genres = (TextView) findViewById(R.id.movie_genre);
        vote_average = (TextView) findViewById(R.id.movie_voteAverage);
        watch = (Button) findViewById(R.id.watch);

        title.setText(mMovie.getTitle());

        description.setText("Description: "+"\n"+ mMovie.getDescription());
        language.setText("Original language: " + mMovie.getLanguage());
        releaseDate.setText("Release date: " + mMovie.getReleaseDate());
        genres.setText("Genre: " + mMovie.getGenreIds());
        vote_average.setText("Vote average: " + mMovie.getVoteAverage());

        Picasso.with(this)
                .load(mMovie.getPoster())
                .into(poster);
        Picasso.with(this)
                .load(mMovie.getBackdrop())
                .into(backdrop);
        watch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.watch) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(mMovie.getLink())));
            startActivity(browserIntent);
        }

    }
}
