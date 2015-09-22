package com.example.tech6.sampleapp.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


import com.example.tech6.sampleapp.R;
import com.example.tech6.sampleapp.activity.OnlineDetailsActivity;
import com.example.tech6.sampleapp.adapter.OnlineMoviesAdapter;
import com.example.tech6.sampleapp.setters.Movies;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class OnlineDataFragment extends Fragment {

    private GridView gridview;
    private int Created = 0;
    private static ArrayList<Movies> MovieList = new ArrayList<Movies>();


    public OnlineDataFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_movies, container, false);

        if (Created == 0) {
            new JSONAsyncTask().execute("http://onikutemathew.com/cinema/api/all.php?category=1");
            Created = 1;
        }


        gridview = (GridView) view.findViewById(R.id.gridview1);
        return view;
    }


    /** Fetch Data methods **/

    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

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
        protected Boolean doInBackground(String... urls) {
            try {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
                HttpConnectionParams.setSoTimeout(httpParameters, 5000);

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient(httpParameters);
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);


                    JSONObject jsono = new JSONObject(data);
                    JSONArray jarray = jsono.getJSONArray("info");
                    MovieList.clear();

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        Movies genres = new Movies();

                        genres.setMovieid(object.getString("id"));
                        genres.setMoviename(object.getString("title"));
                        genres.setMoviestarring(object.getString("starring"));
                        genres.setImage(object.getString("imageurl"));
                        genres.setBigImage(object.getString("bigimageurl"));
                        genres.setDescription(object.getString("description"));
                        genres.setCategory(object.getString("category"));

                        MovieList.add(genres);
                    }

                    return true;
                }

                //------------------>>

            } catch(ConnectTimeoutException e){
                Log.e("Timeout Exception: ", e.toString());
            } catch(SocketTimeoutException ste){
                Log.e("Timeout Exception: ", ste.toString());
            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            dialog.cancel();

            gridview.setAdapter(new OnlineMoviesAdapter(getActivity()));
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {

                    Intent i = new Intent(getActivity(), OnlineDetailsActivity.class);
                    i.putExtra("id", position);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
            });

            Log.d("Status", "In the onPostExecute");
            if(result == false){
                Toast.makeText(getActivity(), "Unable to fetch data, Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
            }

        }
    }

    public static ArrayList<Movies> getList() {
        // TODO Auto-generated method stub
        return MovieList;
    }


}
