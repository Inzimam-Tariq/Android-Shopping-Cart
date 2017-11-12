package com.qemasoft.alhabibshop.app.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;

import org.json.JSONObject;

import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.GET_CUSTOMER_ID;
import static com.qemasoft.alhabibshop.app.AppConstants.getApiCallUrl;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragEditAccount extends Fragment {

    Button editAccountBtn;
    private Utils utils;
    private Context context;
    private EditText fName, lName, email, contact;

    public FragEditAccount() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_edit_account, container, false);
        this.utils = new Utils(getActivity());
        this.context = getActivity();
        initViews(view);


        editAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.isNetworkConnected()) {
                    String fNameVal = fName.getText().toString().trim();
                    String lNameVal = lName.getText().toString().trim();
                    String emailVal = email.getText().toString().trim();
                    String contactVal = contact.getText().toString().trim();
                    AppConstants.setMidFixApi("editCustomer");
                    if (fNameVal.length() > 0 && lNameVal.length() > 0 && emailVal.length() > 0
                            && contactVal.length() > 0) {
                        AndroidNetworking.post(getApiCallUrl())
                                .addBodyParameter("firstname", fNameVal)
                                .addBodyParameter("lastname", lNameVal)
                                .addBodyParameter("email", emailVal)
                                .addBodyParameter("telephone", contactVal)
                                .addBodyParameter("customer_id", GET_CUSTOMER_ID(CUSTOMER_KEY))
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        boolean success = response.optBoolean("success");
                                        if (success) {
                                            String userdata = response.optString("userdata");
                                            if (userdata.contains("success")) {
                                                utils.showAlertDialog("Information Saved!",
                                                        "Profile Updated Successfully!");
                                            }
                                        } else {
                                            utils.showErrorDialog("Invalid Entry");
                                        }
                                    }

                                    @Override
                                    public void onError(ANError anError) {
                                        anError.printStackTrace();
                                        utils.showErrorDialog("Error Getting Data From Server");
                                        Toast.makeText(context, "ErrorGettingDataFromServer", Toast.LENGTH_LONG).show();
                                    }
                                });
                    } else {
                        utils.showErrorDialog("Some Fields are Empty");
                    }
                } else {
                    utils.showInternetErrorDialog();
                }
            }
        });

        return view;
    }

    private void initViews(View view) {
        editAccountBtn = view.findViewById(R.id.edit_account_btn);
        fName = view.findViewById(R.id.fNameET);
        lName = view.findViewById(R.id.lName);
        email = view.findViewById(R.id.emailET);
        contact = view.findViewById(R.id.contactET);
    }

}
