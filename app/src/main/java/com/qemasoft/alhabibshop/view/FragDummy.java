package com.qemasoft.alhabibshop.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hostflippa.com.opencart_android.R;
import me.relex.circleindicator.CircleIndicator;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragDummy extends Fragment {

    private ViewPager mPager;
    private int currentPage = 0;
    private final Integer[] XMEN = {R.drawable.jacket1,
            R.drawable.jacket2, R.drawable.jacket3, R.drawable.jacket4, R.drawable.jacket5};
    private ArrayList<Integer> XMENArray = new ArrayList<>();

    private CircleIndicator indicator;
    private Context context;

    public FragDummy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_product_detail, container, false);
        initViews(view);
        this.context = getContext();

//        setupSlider();

        return view;
    }

    private void changeFragment(int frag) {
        ((MainActivity)getActivity()).changeFragment(frag);
    }

    private void initViews(View view) {
        mPager = view.findViewById(R.id.pager);
        indicator = view.findViewById(R.id.indicator);

    }

//    private void setupSlider() {
//        Collections.addAll(XMENArray, XMEN);
//
//
//        mPager.setAdapter(new MyPagerAdapter(context, XMENArray));
//
//        indicator.setViewPager(mPager);
//
//        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == XMEN.length) {
//                    currentPage = 0;
//                }
//                mPager.setCurrentItem(currentPage++, true);
//            }
//        };
//        Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 2500, 2500);
//    }

}
