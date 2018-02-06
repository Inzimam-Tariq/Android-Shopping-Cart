package com.qemasoft.alhabibshop.app.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.MainFragmentAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static com.qemasoft.alhabibshop.app.AppConstants.getHomeExtra;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFrag extends MyBaseFragment {

    private ViewPager mPager;
    private CircleIndicator indicator;
    private ProgressBar pb;

    public MainFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_main, container, false);
        initUtils();
        initViews(view);
        utils.setupSlider(mPager, indicator, pb, true, true);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity()
                        , LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        List<String> keysList = prepareData();
        if (keysList.size() > 0 && !keysList.isEmpty()) {
            mRecyclerView.setAdapter(new MainFragmentAdapter(keysList));
        }

        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.parent_recycler_view);
        mPager = view.findViewById(R.id.pager);
        indicator = view.findViewById(R.id.indicator);
        pb = view.findViewById(R.id.loading);
    }

    private List<String> prepareData() {
        String responseStr = getHomeExtra();
        List<String> keysStr = new ArrayList<>();
        try {
            JSONObject responseObject = new JSONObject(responseStr);
            utils.printLog("JSON_Response", "" + responseObject);
            boolean success = responseObject.optBoolean("success");
            if (success) {
                JSONObject homeObject = responseObject.optJSONObject("home");
                JSONObject modules = homeObject.optJSONObject("modules");

                Iterator<?> keys = modules.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    keysStr.add(key);
                    utils.printLog("KeyStr", key);
                }
                utils.printLog("ModuleSize", modules.toString());
            } else {
                utils.printLog("SuccessFalse", "Within getCategories");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            utils.printLog("JSONEx_MainFragTest", responseStr);
        }

        return keysStr;
    }

}
