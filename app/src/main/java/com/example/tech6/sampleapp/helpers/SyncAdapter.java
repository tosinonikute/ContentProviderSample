package com.example.tech6.sampleapp.helpers;

import android.accounts.Account;



import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.tech6.sampleapp.activity.MoviesActivity;
import com.example.tech6.sampleapp.contentprovider.CinemalistingContentProvider;
import com.example.tech6.sampleapp.tables.CinemalistingTable;

import java.io.IOException;
import java.net.SocketTimeoutException;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;

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


public class SyncAdapter extends AbstractThreadedSyncAdapter {
    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    String TAG ="Exception Message: ";

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();

    }

    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {
    /*
     * Put the data transfer code here.
     */
        Log.d("Message: ", "Perform Sync Call");
        /* Calling a remote Api to return Json data */
        new JSONAsyncTask().execute("http://onikutemathew.com/cinema/api/all.php?category=1");



    }



    class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {

        String COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_IMAGEURL, COLUMN_CATEGORY, COLUMN_STARRING;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                    ContentValues values = new ContentValues();
                    MoviesActivity ma = new MoviesActivity();

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        /* Inserting to CinemaListing table */
                        values.put(CinemalistingTable.COLUMN_ID, object.getString("id"));
                        values.put(CinemalistingTable.COLUMN_TITLE, object.getString("title"));
                        values.put(CinemalistingTable.COLUMN_DESCRIPTION, object.getString("description"));
                        values.put(CinemalistingTable.COLUMN_IMAGEURL, object.getString("imageurl"));
                        values.put(CinemalistingTable.COLUMN_CATEGORY, object.getString("category"));
                        values.put(CinemalistingTable.COLUMN_STARRING, object.getString("starring"));

                        /* Calling the SaveImage In MoviesActivity to save images from the web, using ImageFilehelper Class */
                        ma.saveImage(object.getString("imageurl"), object.getString("title"));
                    }


                    return true;
                }

                //------------------>>

            } catch (ConnectTimeoutException e) {
                Log.e("Timeout Exception: ", e.toString());
            } catch (SocketTimeoutException ste) {
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


            Log.d("Status", "In the onPostExecute");

            if (result == false) {
                Log.d("ISSUE: ", "No Data Available at this time, Please Connect to the internet");
            }

        }
    }





}