package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.qemasoft.alhabibshop.app.AppConstants.CHANGE_PASS_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.CUSTOMER_EMAIL;
import static com.qemasoft.alhabibshop.app.AppConstants.DEFAULT_STRING_VAL;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragChangePassword extends MyBaseFragment {

    private EditText currentPass, pass, confirmPass;
    private Button changePassBtn;

    public FragChangePassword() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_change_pass, container, false);
        initViews(view);
        initUtils();

        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentPassVal = currentPass.getText().toString().trim();
                String newPassVal = pass.getText().toString().trim();
                String confirmPassVal = confirmPass.getText().toString().trim();

                if (!newPassVal.equals(confirmPassVal)) {
                    utils.showErrorDialog("Password Mis-match");
                    return;
                }
                if (!newPassVal.equals(confirmPassVal)) {
                    confirmPass.setError("Password Mis_match");
                    return;
                }
                if (currentPassVal.length() < 1) {
                    pass.setError("Required");
                } else if (newPassVal.length() < 1) {
                    pass.setError("Required");
                } else if (confirmPassVal.length() < 1) {
                    pass.setError("Required");
                } else {

                    AppConstants.setMidFixApi("editPassword");

                    Map<String, String> map = new HashMap<>();
                    map.put("email", Preferences.getSharedPreferenceString(appContext,
                            CUSTOMER_EMAIL, DEFAULT_STRING_VAL));
                    map.put("oldPassword", currentPassVal);
                    map.put("password", newPassVal);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("hasParameters", true);
                    bundle.putSerializable("parameters", (Serializable) map);
                    Intent intent = new Intent(getContext(), FetchData.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, CHANGE_PASS_REQUEST_CODE);
                }

            }
        });

        return view;
    }


    private void initViews(View view) {

        currentPass = view.findViewById(R.id.current_pass_et);
        pass = view.findViewById(R.id.pass_et);
        confirmPass = view.findViewById(R.id.new_pass_et);
        changePassBtn = view.findViewById(R.id.change_pass_btn);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHANGE_PASS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                AlertDialog dialog = utils.showAlertDialogReturnDialog(
                        "Confirmation Message!", "Password Changed Successfully!");
                dialog.setButton(BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getActivity().recreate();
                                    }
                                }, 10);
                            }
                        });
                dialog.show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String error = response.optString("message");
                    if (error.length() > 0) {
                        AlertDialog dialog = utils.showAlertDialogReturnDialog(
                                "Error Message!", error);
                        dialog.setButton(BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                });
                        dialog.show();
                    } else {
                        utils.showAlertDialog("Invalid Request!",
                                "Either the request is invalid or no relevant record found");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
