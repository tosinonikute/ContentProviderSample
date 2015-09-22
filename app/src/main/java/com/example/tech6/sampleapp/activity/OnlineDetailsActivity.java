package com.example.tech6.sampleapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tech6.sampleapp.R;
import com.example.tech6.sampleapp.fragment.OnlineDataFragment;
import com.example.tech6.sampleapp.setters.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OnlineDetailsActivity extends AppCompatActivity {

    TextView MovieName;
    TextView MovieStarring;
    TextView MovieCategory;
    TextView MovieDescription;
    ImageView movieBigImage;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        OnlineDataFragment mov = new OnlineDataFragment();
        ArrayList<Movies> list = mov.getList();


        MovieName = (TextView)findViewById(R.id.moviename);
        MovieCategory = (TextView)findViewById(R.id.moviecategory);
        MovieStarring = (TextView)findViewById(R.id.starring);
        MovieDescription = (TextView)findViewById(R.id.moviedescription);
        movieBigImage = (ImageView)findViewById(R.id.moviebigimage);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
        }


        if(id != 0) {
            MovieName.setText(list.get(id).getMoviename());
            MovieStarring.setText(list.get(id).getMoviestarring());
            MovieCategory.setText("Drama | " + list.get(id).getCategory());
            MovieDescription.setText(list.get(id).getDescription());
            Picasso.with(getApplicationContext()).load(list.get(id).getImage()).into(movieBigImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
