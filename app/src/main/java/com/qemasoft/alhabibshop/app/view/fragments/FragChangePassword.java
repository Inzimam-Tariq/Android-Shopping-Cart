package com.qemasoft.alhabibshop.app.view.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;

import org.json.JSONObject;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_KEY;
import static com.qemasoft.alhabibshop.app.AppConstants.getApiCallUrl;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragChangePassword extends Fragment {

    private EditText pass, confirmPass;
    private Button changePassBtn;
    private Context context;
    private Utils utils;

    public FragChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_change_pass, container, false);
        this.utils = new Utils(getActivity());
        this.context = getActivity();
        initViews(view);


        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String passVal = pass.getText().toString().trim();
                String newPass = confirmPass.getText().toString().trim();

                if (!passVal.equals(newPass)) {
                    utils.showErrorDialog("Password Mis-match");
                    return;
                }

                if (passVal.length() > 0 && newPass.length() > 0) {
                    if (utils.isNetworkConnected()) {

                        AppConstants.setMidFixApi("register");

                        AndroidNetworking.post(getApiCallUrl())
                                .addBodyParameter("password", passVal)
                                .addBodyParameter("customer_id", AppConstants.GET_CUSTOMER_ID(CUSTOMER_KEY))
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        boolean success = response.optBoolean("success");
                                        if (success) {
                                            String userData = response.optString("userdata");
                                            if (userData.contains("Email Already Exist")) {
                                                utils.showAlertDialog(userData, "There is already an account with this email." +
                                                        "Please Login or use different email");
                                            } else {
                                                AlertDialog dialog = utils.showAlertDialogReturnDialog(
                                                        "Confirmation Message!", "Password Changed Successfully");
                                                dialog.setButton(BUTTON_POSITIVE, "OK",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog,
                                                                                int which) {
                                                                ((MainActivity) getActivity()).changeFragment(0);
                                                            }
                                                        });
                                                dialog.setButton(BUTTON_NEGATIVE, "Cancel",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {

                                                            }
                                                        });
                                                dialog.show();
                                            }
                                        } else {
                                            utils.showErrorDialog("Error Getting Data From Server");
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
                        utils.showInternetErrorDialog();
                    }
                } else {
                    // set empty field error message
                    utils.showErrorDialog("Some Fields are Empty");
                }
            }
        });

        return view;
    }


    private void initViews(View view) {

        pass = view.findViewById(R.id.pass_et);
        confirmPass = view.findViewById(R.id.new_pass_et);
        changePassBtn = view.findViewById(R.id.change_pass_btn);
    }

}
