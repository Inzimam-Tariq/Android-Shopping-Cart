package com.qemasoft.alhabibshop.app.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.qemasoft.alhabibshop.app.R;

import java.util.ArrayList;
import java.util.List;

public class FragCheckout extends MyBaseFragment implements View.OnClickListener {

    private StateProgressBar stateProgressBar;
    private Button selectAddressDelivery, backBtn, nextBtn;
    private CheckBox termsCB;
    private LinearLayout step1, step2, step3, step4, step5;
    private List<String> list;

    public FragCheckout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_checkout, container, false);
        initViews(view);
        initUtils();

        String[] descriptionData = {"Delivery", "Shipping", "Payment", "Confirm"};
        stateProgressBar.setStateDescriptionData(descriptionData);
        backBtn.setOnClickListener(this);
        selectAddressDelivery.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        return view;
    }

    private void initViews(View view) {
        stateProgressBar = view.findViewById(R.id.state_progress_bar);
        backBtn = view.findViewById(R.id.back_btn);
        selectAddressDelivery = view.findViewById(R.id.select_delivery_address_btn);
        step1 = view.findViewById(R.id.step1);
        step2 = view.findViewById(R.id.step2);
        step3 = view.findViewById(R.id.step3);
        step4 = view.findViewById(R.id.step4);

        termsCB = view.findViewById(R.id.terms_cb);
        nextBtn = view.findViewById(R.id.next_btn);
    }

    @Override
    public void onClick(View v) {
        list = new ArrayList<>();
        list.add("Muhammad Shafiq 70BB Pak Arab Lahore, Pakistan");
        switch (v.getId()) {
            case R.id.select_delivery_address_btn:
                utils.showRadioAlertDialog(selectAddressDelivery, "Select Address", list);
                break;
            case R.id.next_btn:
                if (step1.getVisibility() == View.VISIBLE) {
                    step1.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                } else if (step2.getVisibility() == View.VISIBLE) {
                    step2.setVisibility(View.GONE);
                    step3.setVisibility(View.VISIBLE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                } else if (step3.getVisibility() == View.VISIBLE) {
                    if (isTermsCBChecked()) {
                        step3.setVisibility(View.GONE);
                        step4.setVisibility(View.VISIBLE);
                        nextBtn.setText("Confirm");
                        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                    } else {
                        utils.showAlertDialog("Read Terms First!", "You have to Accept Terms and Condition to Continue");
                    }
                }else if (step4.getVisibility() == View.VISIBLE){
                    stateProgressBar.setAllStatesCompleted(true);
                }
                break;

            case R.id.back_btn:
                if (step4.getVisibility() == View.VISIBLE) {
                    step4.setVisibility(View.GONE);
                    step3.setVisibility(View.VISIBLE);
                    nextBtn.setText("Next");
                    stateProgressBar.setAllStatesCompleted(false);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                } else if (step3.getVisibility() == View.VISIBLE) {
                    step3.setVisibility(View.GONE);
                    step2.setVisibility(View.VISIBLE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                } else if (step2.getVisibility() == View.VISIBLE) {
                    step2.setVisibility(View.GONE);
                    step1.setVisibility(View.VISIBLE);
                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                }
                break;

        }
    }

    private boolean isTermsCBChecked() {
        return termsCB.isChecked();
    }
}
