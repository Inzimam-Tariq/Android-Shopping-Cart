package com.qemasoft.alhabibshop.app.view;

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

import static com.qemasoft.alhabibshop.app.AppConstants.getApiCallUrl;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragForgotPass extends Fragment {

    private Button passResetBtn;
    private EditText email;
    private Context context;
    private Utils utils;

    public FragForgotPass() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_forgot_pass, container, false);
        initViews(view);
        this.context = getActivity();
        utils = new Utils(getActivity());

        passResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (utils.isNetworkConnected()) {

                    String emailVal = email.getText().toString().trim();
                    AppConstants.setMidFixApi("editCustomer");
                    if (emailVal.length() > 0) {
                        AndroidNetworking.post(getApiCallUrl())
                                .addBodyParameter("email", emailVal)
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        boolean success = response.optBoolean("success");
                                        String userdata = "";
                                        if (success) {
                                            userdata = response.optString("userdata");
                                            if (userdata.contains("success")) {
                                                utils.showAlertDialog("Confirmation Message!",
                                                        userdata);
                                            }
                                        } else {
                                            utils.showAlertDialog("Invalid Entry!",
                                                    userdata);
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
        passResetBtn = view.findViewById(R.id.passResetBtn);
        email = view.findViewById(R.id.emailET);
    }

}