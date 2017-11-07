package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qemasoft.alhabibshop.app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Inzimam on 23-Oct-17.
 */

public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<String> images;
    private LayoutInflater inflater;


    public MyPagerAdapter(Context context, ArrayList<String> images) {
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        ImageView myImage = myImageLayout.findViewById(R.id.image);
//        myImage.setImageResource(images.get(position));
        Picasso.with(view.getContext()).load(images.get(position)).into(myImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}