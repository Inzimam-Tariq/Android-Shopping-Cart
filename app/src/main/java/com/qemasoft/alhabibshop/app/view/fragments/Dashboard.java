package com.qemasoft.alhabibshop.app.view.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;

import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_FIRST_NAME;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_LAST_NAME;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;
import static com.qemasoft.alhabibshop.app.AppConstants.findStringByName;


/**
 * A simple {@link Fragment} subclass.
 */
public class Dashboard extends MyBaseFragment implements View.OnClickListener {
    
    private TextView userNameTV, editAccountTV, changePasswordTV, addressBookTV, orderHistoryTV,
            transactionsTV, newsletterTV, rewardPointsTV, returnHistoryTV;
    
    public Dashboard() {
        // Required empty public constructor
    }
    
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initViews(view);
        initUtils();
        setupIcons();
        
        userNameTV.setText(findStringByName("welcome_text").concat(" ")
                .concat(Preferences.getSharedPreferenceString(appContext,
                        CUSTOMER_FIRST_NAME, DEFAULT_STRING_VAL))
                .concat(" ").concat(Preferences.getSharedPreferenceString(appContext,
                        CUSTOMER_LAST_NAME, DEFAULT_STRING_VAL)));
        editAccountTV.setOnClickListener(this);
        changePasswordTV.setOnClickListener(this);
        addressBookTV.setOnClickListener(this);
        orderHistoryTV.setOnClickListener(this);
        transactionsTV.setOnClickListener(this);
//        newsletter.setOnClickListener(this);
//        rewardPoints.setOnClickListener(this);
        returnHistoryTV.setOnClickListener(this);
        
        
        return view;
    }
    
    private void setupIcons() {
        utils.setCompoundDrawable(editAccountTV, "top", R.drawable.ic_edit_black);
        utils.setCompoundDrawable(changePasswordTV, "top", R.drawable.ic_vpn_key_black);
        utils.setCompoundDrawable(addressBookTV, "top", R.drawable.ic_folder_shared_black);
        utils.setCompoundDrawable(orderHistoryTV, "top", R.drawable.ic_shopping_cart_black);
        utils.setCompoundDrawable(transactionsTV, "top", R.drawable.ic_account_balance_black);
        utils.setCompoundDrawable(returnHistoryTV, "top", R.drawable.ic_remove_shopping_cart_black);
    }
    
    private void initViews(View view) {
        userNameTV = view.findViewById(R.id.user_name_tv);
        editAccountTV = view.findViewById(R.id.edit_account_tv);
        changePasswordTV = view.findViewById(R.id.change_pass_tv);
        addressBookTV = view.findViewById(R.id.address_book_tv);
        orderHistoryTV = view.findViewById(R.id.order_history_tv);
        transactionsTV = view.findViewById(R.id.transactions_tv);
//        newsletter = view.findViewById(R.id.newsletter_tv);
//        rewardPoints = view.findViewById(R.id.reward_points_tv);
        returnHistoryTV = view.findViewById(R.id.return_history_tv);
    }
    
    @Override
    public void onClick(View v) {
        int id = v.getId();
        
        if (id == R.id.edit_account_tv) {
            utils.switchFragment(new FragEditAccount());
        } else if (id == R.id.change_pass_tv) {
            utils.switchFragment(new FragChangePassword());
        } else if (id == R.id.address_book_tv) {
            utils.switchFragment(new AddressBook());
        } else if (id == R.id.order_history_tv) {
            utils.switchFragment(new FragOrderHistory());
        } else if (id == R.id.return_history_tv) {
            utils.switchFragment(new FragOrderHistory());
        }
    }
}
