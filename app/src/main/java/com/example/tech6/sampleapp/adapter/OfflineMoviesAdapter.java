package com.example.tech6.sampleapp.adapter;

/*
    OnlineMoviesAdapter Gets called if User is Offline.
 */
import android.content.Context;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;


import java.util.ArrayList;

import com.example.tech6.sampleapp.R;
import com.example.tech6.sampleapp.fragment.OfflineDataFragment;
import com.example.tech6.sampleapp.setters.Movies;
import com.squareup.picasso.Picasso;


public class OfflineMoviesAdapter extends BaseAdapter {

    OfflineDataFragment F1= new OfflineDataFragment();
    ArrayList<Movies> list = F1.getList();


    private Context mContext;

    public OfflineMoviesAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        //return mThumbIds.length;
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes

            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(320, 480));
            imageView.setLayoutParams(new GridView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(4, 4, 4, 4);
            imageView.setAdjustViewBounds(true);

        } else {
            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(mThumbIds[position]);
        Picasso.with(mContext).load(list.get(position).getImage()).placeholder(R.drawable.placeholder).into(imageView);

        return imageView;
    }


}