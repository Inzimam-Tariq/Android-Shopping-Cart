package com.qemasoft.alhabibshop.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.Utils;
import com.qemasoft.alhabibshop.controller.MyPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import hostflippa.com.opencart_android.R;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Inzimam on 07-Nov-17.
 */

public class FragSlider extends Fragment {

    private Utils utils;
    private Context context;

    private ViewPager mPager;
    private int currentPage = 0;
    private ArrayList<String> sliderImagesUrl;// = new ArrayList<>();
    private CircleIndicator indicator;

    public FragSlider() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.slider_layout, container, false);
        this.context = getActivity();
        this.utils = new Utils(context);
        initViews(view);

        setupSlider();


        return view;
    }

    private void initViews(View view) {
        mPager = view.findViewById(R.id.pager);
        indicator = view.findViewById(R.id.indicator);
    }

    private void setupSlider() {
        sliderImagesUrl = new ArrayList<>();
        final String[] urls = {"http://www.opencartgulf.com/image/cache/catalog/demo/banners/iPhone6-1140x380.jpg"
                , "http://www.opencartgulf.com/image/cache/catalog/demo/banners/MacBookAir-1140x380.jpg"};
        Collections.addAll(sliderImagesUrl, urls);


        mPager.setAdapter(new MyPagerAdapter(context, sliderImagesUrl));

        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == urls.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

}