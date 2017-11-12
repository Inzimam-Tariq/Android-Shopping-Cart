package com.qemasoft.alhabibshop.app.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.controller.CategoryAdapterTest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.qemasoft.alhabibshop.app.AppConstants.getHomeExtra;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragTest extends Fragment {

    private Utils utils;

    public MainFragTest() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_main_test, container, false);
        this.utils = new Utils(getActivity());

        RecyclerView recyclerView = view.findViewById(R.id.all_recycler_view);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity()
                        , LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        List<String> list = prepareData();
        recyclerView.setAdapter(new CategoryAdapterTest(list));

        return view;
    }

    private List<String> prepareData() {
        String responseStr = getHomeExtra();
        List<String> keysStr = new ArrayList<>();
        try {
            JSONObject responseObject = new JSONObject(responseStr);
            Log.e("JSON_Response", "" + responseObject);
            boolean success = responseObject.optBoolean("success");
            if (success) {
                JSONObject homeObject = responseObject.optJSONObject("home");
                JSONObject modules = homeObject.optJSONObject("modules");

                Iterator<?> keys = modules.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    keysStr.add(key);
                }
            } else {
                Log.e("SuccessFalse", "Within getCategories");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONObjEx_CatAdptrTest", responseStr);
        }

        return keysStr;
    }

}
