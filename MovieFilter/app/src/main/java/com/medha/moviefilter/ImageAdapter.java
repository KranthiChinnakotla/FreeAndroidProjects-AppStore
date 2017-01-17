package com.medha.moviefilter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Prathyusha on 1/8/17.
 */

public class ImageAdapter extends BaseAdapter  {
    private Context mContext;
    private Integer[] images = { R.drawable.sample_0,R.drawable.sample_1,R.drawable.sample_2,R.drawable.sample_3,R.drawable.sample_4,
    R.drawable.sample_5,R.drawable.sample_5,R.drawable.sample_6,R.drawable.sample_7};
    private ArrayList<Movie> adapterMovies;


    public ImageAdapter(Context mContext,ArrayList<Movie> movies) {
        this.mContext = mContext;
        this.adapterMovies = movies;
    }

    public int getCount(){
        return adapterMovies.size();
    }

    public  Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;

        if(view == null){

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(500,700));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5,5,5,5);


        }else{
            imageView = (ImageView) view;
        }

        Picasso.with(mContext).load(adapterMovies.get(i).getPosterUrl()).into(imageView);
        return imageView;
    }



}
