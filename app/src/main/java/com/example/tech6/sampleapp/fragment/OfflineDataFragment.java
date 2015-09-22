package com.example.tech6.sampleapp.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import com.example.tech6.sampleapp.R;
import com.example.tech6.sampleapp.activity.OfflineDetailsActivity;
import com.example.tech6.sampleapp.adapter.OfflineMoviesAdapter;
import com.example.tech6.sampleapp.contentprovider.CinemalistingContentProvider;
import com.example.tech6.sampleapp.setters.Movies;
import com.example.tech6.sampleapp.tables.CinemalistingTable;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineDataFragment extends Fragment {

    String COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_IMAGEURL, COLUMN_CATEGORY, COLUMN_STARRING;
    private static ArrayList<Movies> MovieList = new ArrayList<Movies>();
    private GridView gridview;
    String TAG ="Exception Message: ";


    public OfflineDataFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_movies, container, false);
        getData(CinemalistingContentProvider.CONTENT_URI);

        gridview = (GridView) view.findViewById(R.id.gridview1);
        return view;
    }

    private void getData(Uri uri){


        //New Async task to show dialoag process.
        new AsyncTask<Uri, Void, Boolean>() {

            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new ProgressDialog(getActivity(),R.style.MyTheme);
                dialog.setCancelable(true);
                dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                dialog.show();
            }

            @Override
            protected Boolean doInBackground(Uri... uri) {

                String[] projection = {
                        CinemalistingTable.COLUMN_ID,
                        CinemalistingTable.COLUMN_TITLE,
                        CinemalistingTable.COLUMN_DESCRIPTION,
                        CinemalistingTable.COLUMN_IMAGEURL,
                        CinemalistingTable.COLUMN_CATEGORY,
                        CinemalistingTable.COLUMN_STARRING
                };
                Cursor cursor = getActivity().getContentResolver().query(uri[0], projection, null, null,
                        null);


                if (cursor != null) {
                    //cursor.moveToFirst();
                    while (cursor.moveToNext()) {

                        COLUMN_ID = cursor.getString(cursor
                                .getColumnIndexOrThrow(CinemalistingTable.COLUMN_ID));
                        COLUMN_TITLE = cursor.getString(cursor
                                .getColumnIndexOrThrow(CinemalistingTable.COLUMN_TITLE));
                        COLUMN_DESCRIPTION = cursor.getString(cursor
                                .getColumnIndexOrThrow(CinemalistingTable.COLUMN_DESCRIPTION));
                        COLUMN_IMAGEURL = cursor.getString(cursor
                                .getColumnIndexOrThrow(CinemalistingTable.COLUMN_IMAGEURL));
                        COLUMN_CATEGORY = cursor.getString(cursor
                                .getColumnIndexOrThrow(CinemalistingTable.COLUMN_CATEGORY));
                        COLUMN_STARRING = cursor.getString(cursor
                                .getColumnIndexOrThrow(CinemalistingTable.COLUMN_STARRING));

                        Movies genres = new Movies();

                        //MovieList.add(object.getString("id"));
                        genres.setMovieid(COLUMN_ID);
                        genres.setMoviename(COLUMN_TITLE);
                        genres.setMoviestarring(COLUMN_STARRING);
                        genres.setImage(COLUMN_IMAGEURL);
                        genres.setDescription(COLUMN_DESCRIPTION);
                        genres.setCategory(COLUMN_CATEGORY);

                        MovieList.add(genres);

                        Log.d("Image URL: ", COLUMN_IMAGEURL);

                    }
                    // always close the cursor
                    cursor.close();
                    return true;
                }

               return false;
            }

            protected void onPostExecute(Boolean result){
                dialog.cancel();

                gridview.setAdapter(new OfflineMoviesAdapter(getActivity()));
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {

                        Intent i = new Intent(getActivity(), OfflineDetailsActivity.class);
                        i.putExtra("id", position);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);

                    }
                });

                if(result == false){
                    Toast.makeText(getActivity(), "No Data Available at this time, Please Connect to the internet", Toast.LENGTH_LONG).show();
                }

            }
           }.execute(uri);

    }


    public static ArrayList<Movies> getList() {
        // TODO Auto-generated method stub
        return MovieList;
    }


}
