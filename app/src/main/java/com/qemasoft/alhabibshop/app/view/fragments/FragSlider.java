package com.qemasoft.alhabibshop.app.view.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.MyPagerAdapter;
import com.qemasoft.alhabibshop.app.model.Slideshow;

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


    private ViewPager mPager;
    private int currentPage = 0;
    private ArrayList<Slideshow> slideshowArrayList;
    private CircleIndicator indicator;

    public FragSlider() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        slideshowArrayList = new ArrayList<>();
        String responseStr = AppConstants.getSlideshowExtra();

        if (!responseStr.isEmpty()) {
            utils.printLog("ResponseInSliderFrag", responseStr);
            try {
                JSONArray slideShowArray = new JSONArray(responseStr);

                for (int i = 0; i < slideShowArray.length(); i++) {
                    try {
                        JSONObject sliderObj = slideShowArray.getJSONObject(i);
                        slideshowArrayList.add(new Slideshow(sliderObj.optString("image"),
                                sliderObj.optString("id"), sliderObj.optString("banertype")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                utils.printLog("JSONObjEx_SliderFrag", responseStr);
            }
        } else {
            utils.printLog("ResponseExSliderFrag", responseStr);
        }

        MyPagerAdapter adapter = new MyPagerAdapter(context, slideshowArrayList, false);
        mPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        indicator.setViewPager(mPager);
        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == slideshowArrayList.size()) {
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
        }, 3000, 3000);
    }

}