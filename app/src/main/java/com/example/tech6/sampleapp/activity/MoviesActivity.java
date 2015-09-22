package com.example.tech6.sampleapp.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.tech6.sampleapp.R;
import com.example.tech6.sampleapp.contentprovider.CinemalistingContentProvider;
import com.example.tech6.sampleapp.fragment.OfflineDataFragment;
import com.example.tech6.sampleapp.fragment.OnlineDataFragment;
import com.example.tech6.sampleapp.helpers.ImageFileHelper;

import java.util.concurrent.ExecutionException;

public class MoviesActivity extends AppCompatActivity{

    int id = 0;
    public static String CONNECTION_STATUS="";
    String TAG ="Message: ";


    /** Sync adapter code **/
    // Constants
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.example.tech6.sampleapp.contentprovider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "com.android.example.sampleapp";
    // The account name
    public static final String ACCOUNT = "dummyaccount1";
    // Instance fields
    Account mAccount;
    ContentResolver mResolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        // Set the menu icon instead of the launcher icon.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        /* Check If Internet connection exist or not **/
        /*if (ni == null) {
            CONNECTION_STATUS="offline";
            NavigateToMoviesSQLite();
            Log.d("msg", "Navigating to SQL DATA");
        }else{
            CONNECTION_STATUS="online";
            NavigateToMovies();
            Log.d("msg", "Navigating to ONLINE DATA");
        }*/

        mResolver = getContentResolver();
        CreateSyncAccount(this, mResolver);
        Log.d("Message: ", "After Calling the CreateSync");

    }


    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context, ContentResolver mResolver) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
    /*
     * Add the account and account type, no password or user data
     * If successful, return the Account object, otherwise report an error.
     */
        if (accountManager.addAccountExplicitly(newAccount, "password", null)) {
        /*
         * If you don't set android:syncable="true" in
         * in your <provider> element in the manifest,
         * then call context.setIsSyncable(account, AUTHORITY, 1)
         * here.
         */

            Bundle extras = new Bundle();
            extras.putString(AccountManager.KEY_ACCOUNT_NAME, newAccount.name);
            extras.putString(AccountManager.KEY_ACCOUNT_TYPE, newAccount.type);

            mResolver.setIsSyncable(newAccount, AUTHORITY, 1);
            mResolver.requestSync(newAccount, AUTHORITY, extras);
            mResolver.setSyncAutomatically(newAccount, AUTHORITY, true);


        } else {
        /*
         * The account exists or some other error occurred. Log this, report it,
         * or handle it internally.
         */


            Log.d("Message: ","Account Already Exist");
        }

        return newAccount;
    }

    /* Launch the OfflineData Fragment, so it can request data from SQLite and display it when Phone is offline */
    protected void NavigateToMoviesSQLite(){
        try {
            Fragment fragment = null;
            Class fragmentClass;
            fragmentClass = OfflineDataFragment.class;
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            Log.d("msg", "Navigating to fragment");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* Launch the OnlineData Fragment so it can request data and display it immediately */
    protected void NavigateToMovies(){
        try {
            Fragment fragment = null;
            Class fragmentClass;
            fragmentClass = OnlineDataFragment.class;
            fragment = (Fragment) fragmentClass.newInstance();
            FragmentManager fragmentManager = (FragmentManager) getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
            Log.d("msg", "Navigating to fragment");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    Call ImageFileHelper class to save image to user's phone
    Running this code here, so i can getApplicationContext()
    */
    public void saveImage(String url, String name){


        new AsyncTask<String, Void, Void>() {

            Bitmap theBitmap = null;
            ImageFileHelper im = new ImageFileHelper();

            // onCreate stuff ...

            @Override
            protected Void doInBackground(String... params) {
                Looper.prepare();
                try {
                    theBitmap = Glide.
                            with(MoviesActivity.this).
                            load(params[0]).
                            asBitmap().
                            into(-1, -1).
                            get();

                    String repaced= params[1].replace(' ', '_');

                    im.saveToInternalSorage(theBitmap, getApplicationContext(), repaced);


                } catch (final ExecutionException e) {
                    Log.e(TAG, e.getMessage());
                } catch (final InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void dummy) {
                if (null != theBitmap) {
                    // The full bitmap should be available here
                    //image.setImageBitmap(theBitmap);
                    Log.d(TAG, "Image loaded");
                }

            }
        }.execute(url,name);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
