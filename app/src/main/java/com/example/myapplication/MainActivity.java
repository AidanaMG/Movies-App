package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;

    int ruler = 1;
    int currentPagePopular = 1;
    int currentPageUpcoming = 1;
    int currentPageTopRated = 1;

    int pageIntPop = 1;
    int pageIntUp = 1;
    int pageIntTop = 1;

    String pageString;
    ImageButton next, previous;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        next = (ImageButton) findViewById(R.id.next);
        previous = (ImageButton) findViewById(R.id.previous);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);




        switch(ruler)
        {
            case 1:
                popularMovies();break;
            case 2: topRated(); break;
            case 3: upcoming(); break;
        }

        next.setOnClickListener(this);
        previous.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_sort_up) {
            ruler = 3;
            upcoming();

            Toast.makeText(this, "Upcoming ", Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_sort_tr) {
            ruler = 2;
            topRated();

            Toast.makeText(this, "Top Rated ", Toast.LENGTH_LONG).show();

            return true;
        }

        if (id == R.id.action_main) {
            ruler = 1;
            popularMovies();
            Toast.makeText(this, "Popular " , Toast.LENGTH_LONG).show();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }


    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.next:

                switch (ruler)
                {

                    case 1:
                        currentPagePopular++;
                        pageIntPop = currentPagePopular;
                        popularMovies();
                        pageString = Integer.toString(pageIntPop);break;
                    case 2:
                        currentPageTopRated++;
                        pageIntTop = currentPageTopRated;
                        topRated();
                        pageString = Integer.toString(pageIntTop);break;
                    case 3:
                        currentPageUpcoming++;
                        pageIntUp = currentPageUpcoming;
                        upcoming();
                        pageString = Integer.toString(pageIntUp);break;
                    default: break;
                }


                previous.setEnabled(true);
                Toast.makeText(this, "page " + pageString, Toast.LENGTH_SHORT).show();
                break;


            case R.id.previous:

            {
                switch (ruler)
                {
                    case 1:
                        if (currentPagePopular<=1)
                        {
                            currentPagePopular = 1;
                            pageIntPop = currentPagePopular;
                            popularMovies();
                            pageString = Integer.toString(pageIntPop);
                        }
                        else
                        {
                            currentPagePopular--;
                            pageIntPop = currentPagePopular;
                            popularMovies();
                            pageString = Integer.toString(pageIntPop);
                        } break;


                    case 2:
                        if (currentPageTopRated<=1)
                        {
                            currentPageTopRated = 1;
                            pageIntTop = currentPageTopRated;
                            topRated();
                            pageString = Integer.toString(pageIntTop);
                        }
                        else
                        {
                            currentPageTopRated--;
                            pageIntTop = currentPageTopRated;
                            topRated();
                            pageString = Integer.toString(pageIntTop);
                        } break;


                    case 3:
                        if (currentPageUpcoming<=1)
                        {
                            currentPageUpcoming = 1;
                            pageIntUp = currentPageUpcoming;
                            upcoming();
                            pageString = Integer.toString(pageIntUp);
                        }
                        else
                        {
                            currentPageUpcoming--;
                            pageIntUp = currentPageUpcoming;
                            upcoming();
                            pageString = Integer.toString(pageIntUp);
                        } break;


                    default: break;
                }

                Toast.makeText(this, "page " + pageString, Toast.LENGTH_SHORT).show();
                break;
            }  }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public MovieViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

        }

    }

    private void popularMovies() {
       final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "c45933ca67d4b186d8da8873fdcb8198");
                        request.addEncodedQueryParam("&page", Integer.toString(pageIntPop));
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesApiService service = restAdapter.create(MoviesApiService.class);
        service.getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {

                mAdapter.setMovieList(movieResult.getResults());

            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });


    }

    public void topRated()
    {

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "c45933ca67d4b186d8da8873fdcb8198");
                        request.addEncodedQueryParam("&page", Integer.toString(pageIntTop));
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesApiService service = restAdapter.create(MoviesApiService.class);


        service.getTopRatedMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        }); }



    public void upcoming()
    {


        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", "c45933ca67d4b186d8da8873fdcb8198");
                        request.addEncodedQueryParam("&page", Integer.toString(pageIntUp));
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesApiService service = restAdapter.create(MoviesApiService.class);


        service.getUpcomingMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                mAdapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }  });   }



    public  class MoviesAdapter extends RecyclerView.Adapter<MainActivity.MovieViewHolder>
    {
        private List<Movie> mMovieList;
        private LayoutInflater mInflater;
        private Context mContext;

        public MoviesAdapter(Context context)
        {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(context);
            this.mMovieList = new ArrayList<>();
        }

        @Override
        public MainActivity.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = mInflater.inflate(R.layout.row_movie, parent, false);
            final MainActivity.MovieViewHolder viewHolder = new MainActivity.MovieViewHolder(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, mMovieList.get(position));
                    mContext.startActivity(intent); }  });


            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MainActivity.MovieViewHolder holder, int position)
        {
            Movie movie = mMovieList.get(position);

            // This is how we use Picasso to load images from the internet.
            Picasso.with(mContext)
                    .load(movie.getPoster())
                    .placeholder(R.color.white)
                    .into(holder.imageView);


        }

        @Override
        public int getItemCount()
        {
            return (mMovieList == null) ? 0 : mMovieList.size();
        }

        public void setMovieList(List<Movie> movieList)
        {
            this.mMovieList = new ArrayList<>();
            this.mMovieList.clear();
            this.mMovieList.addAll(movieList);
            // The adapter needs to know that the data has changed. If we don't call this, app will crash.
            notifyDataSetChanged();

        }
    }
}