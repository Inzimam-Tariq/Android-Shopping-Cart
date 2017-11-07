package com.qemasoft.alhabibshop.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qemasoft.alhabibshop.Utils;

import hostflippa.com.opencart_android.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends Fragment implements View.OnClickListener {

    private Context context;
    private Utils utils;
    private TextView editAccount, changePassword, addressBook, orderHistory,
            transactions, newsletter, rewardPoints, returnHistory;

    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initViews(view);
        this.context = getActivity();

        editAccount.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        addressBook.setOnClickListener(this);
        orderHistory.setOnClickListener(this);
        transactions.setOnClickListener(this);
        newsletter.setOnClickListener(this);
        rewardPoints.setOnClickListener(this);
        returnHistory.setOnClickListener(this);


        return view;
    }

    private void initViews(View view) {
        editAccount = view.findViewById(R.id.edit_account_tv);
        changePassword = view.findViewById(R.id.change_pass_tv);
        addressBook = view.findViewById(R.id.address_book_tv);
        orderHistory = view.findViewById(R.id.order_history_tv);
        transactions = view.findViewById(R.id.transactions_tv);
        newsletter = view.findViewById(R.id.newsletter_tv);
        rewardPoints = view.findViewById(R.id.reward_points_tv);
        returnHistory = view.findViewById(R.id.return_history_tv);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.edit_account_tv) {
            ((MainActivity) getActivity()).changeFragment(104);
        } else if (id == R.id.change_pass_tv) {
//            ((MainActivity) getActivity()).changeFragment(103);
        } else if (id == R.id.address_book_tv) {
//            ((MainActivity) getActivity()).changeFragment(103);
        } else if (id == R.id.order_history_tv) {
            ((MainActivity) getActivity()).changeFragment(106);
        } else if (id == R.id.transactions_tv) {
//            ((MainActivity) getActivity()).changeFragment(103);
        } else if (id == R.id.newsletter_tv) {
//            ((MainActivity) getActivity()).changeFragment(103);
        } else if (id == R.id.reward_points_tv) {
//            ((MainActivity) getActivity()).changeFragment(103);
        } else if (id == R.id.return_history_tv) {
            ((MainActivity) getActivity()).changeFragment(106);
        }
    }
}
