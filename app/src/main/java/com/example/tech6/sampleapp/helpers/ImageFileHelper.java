package com.example.tech6.sampleapp.helpers;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.example.tech6.sampleapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by tech6 on 9/11/15.
 * Image file helper class is meant to Save images into data/data/moviecovers on Users Phone.
 */
public class ImageFileHelper {

    public String saveToInternalSorage(Bitmap bitmapImage, Context context,String name){


        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir

        String name_="moviecovers"; //Changed the name to name_ so that it would just store all into movie covers directoty
        File directory = cw.getDir(name_, Context.MODE_PRIVATE);

        // Create imageDir
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("absolutepath ", directory.getAbsolutePath());
        return directory.getAbsolutePath();
    }


    public Bitmap loadImageFromStorage(String path, String name)
    {
        Bitmap b;
        String name_="moviecovers";
        try {
            File f=new File(path, name_);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            //ImageView img=(ImageView)findViewById(R.id.imgId);
            //img.setImageBitmap(b);
            return b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
