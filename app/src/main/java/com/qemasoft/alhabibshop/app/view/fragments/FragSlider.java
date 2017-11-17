package com.qemasoft.alhabibshop.app.view.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.MyPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Inzimam on 07-Nov-17.
 */

public class FragSlider extends MyBaseFragment {

    public static final String SLIDER_EXTRA = "com.qemasoft.alhabibshop.app" + "getSliderExtra";
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
        initUtils();
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
        String responseStr = "";
        if (getActivity().getIntent().hasExtra(SLIDER_EXTRA)) {
            responseStr = getActivity().getIntent().getStringExtra(SLIDER_EXTRA);
            Log.e("ResponseInSliderFrag", responseStr);
            try {
                JSONArray slideShowArray = new JSONArray(responseStr);

                for (int i = 0; i < slideShowArray.length(); i++) {
                    try {
                        JSONObject sliderObj = slideShowArray.getJSONObject(i);
                        sliderImagesUrl.add(sliderObj.optString("image"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("JSONObjEx_SliderFrag", responseStr);
            }
        } else {
            Log.e("ResponseExSliderFrag", responseStr);
            throw new IllegalArgumentException("Cannot find  extras " + SLIDER_EXTRA);
        }

        mPager.setAdapter(new MyPagerAdapter(context, sliderImagesUrl));
        indicator.setViewPager(mPager);
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == sliderImagesUrl.size()) {
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