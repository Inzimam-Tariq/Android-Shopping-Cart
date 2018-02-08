package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.Slideshow;
import com.qemasoft.alhabibshop.app.view.fragments.FragProduct;
import com.qemasoft.alhabibshop.app.view.fragments.FragProductDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Inzimam on 23-Oct-17.
 */

public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<Slideshow> slideshowArrayList;
    private LayoutInflater inflater;
    private Utils utils;
    private boolean isClickListenerSet;


    public MyPagerAdapter(Context context, ArrayList<Slideshow> slideshowArrayList,
                          boolean isClickListenerSet) {

        this.slideshowArrayList = slideshowArrayList;
        this.isClickListenerSet = isClickListenerSet;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position,
                            @NonNull Object object) {

        container.removeView((View) object);
        utils.printLog("Destroying MPagerView");
    }

    @Override
    public int getCount() {

        return slideshowArrayList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup view, int position) {

        View myImageLayout = inflater.inflate(R.layout.slide, view, false);

        final ImageView myImage = myImageLayout.findViewById(R.id.image);
        final ProgressBar progressBar = myImageLayout.findViewById(R.id.progress_bar);

        final Slideshow slideshow = slideshowArrayList.get(position);
        utils = new Utils(view.getContext());

        if (!slideshow.getImage().isEmpty())
            Picasso.with(view.getContext())
                    .load(slideshow.getImage())
                    .resize(400, 200)
                    .into(myImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            progressBar.setVisibility(View.GONE);
                            myImage.setImageResource(R.drawable.ic_close_black);
                        }
                    });
        view.addView(myImageLayout, 0);

        if (isClickListenerSet) {

            myImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String id = slideshow.getId();
                    String type = slideshow.getBannerType();
                    Bundle bundle = new Bundle();
                    switch (type) {
                        case "0":
                            bundle.putString("id", id);
                            utils.switchFragment(new FragProduct(), bundle);
                            break;
                        case "2":
                            bundle.putString("id", id);
                            bundle.putString("from", "mainActivity");
                            utils.switchFragment(new FragProductDetail(), bundle);
                            break;
                        default:
                            bundle.putString("id", id);
                            utils.switchFragment(new FragProductDetail(), bundle);
                            break;
                    }
                }
            });
        }

        return myImageLayout;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        // Causes adapter to reload all Fragments when
        // notifyDataSetChanged is called
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view.equals(object);
    }
}