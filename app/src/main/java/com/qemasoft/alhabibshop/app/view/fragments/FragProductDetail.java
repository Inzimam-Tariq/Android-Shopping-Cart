package com.qemasoft.alhabibshop.app.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragProductDetail extends Fragment {

    private Context context;

    public FragProductDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_product_detail, container, false);
        initViews(view);
        this.context = getContext();

        return view;
    }

    private void changeFragment(int frag) {
        ((MainActivity)getActivity()).changeFragment(frag);
    }

    private void initViews(View view) {

    }
}
