package com.qemasoft.alhabibshop.app.view.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;

import org.json.JSONObject;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.qemasoft.alhabibshop.app.AppConstants.getApiCallUrl;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragRegister extends MyBaseFragment {

    private RadioGroup rgNewsletter;
    private RadioButton rbYes, rbNo;
    private EditText fName, lName, email, contact, pass, confirmPass;
    private CheckBox termsCB;
    private Button registerBtn;
    private TextView privacyPolicyTV, clickLoginTV;

    public FragRegister() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_register, container, false);
        initUtils();
        initViews(view);

        rgNewsletter.check(R.id.rbNo);
        privacyPolicyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(1);
//                utils.showAlertDialog("Privacy Policy", "Privacy Policy text here");
            }
        });
        clickLoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(101);
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fNameVal = fName.getText().toString().trim();
                String lNameVal = lName.getText().toString().trim();
                String emailVal = email.getText().toString().trim();
                String contactVal = contact.getText().toString().trim();
                String passVal = pass.getText().toString().trim();
                String rePassVal = confirmPass.getText().toString().trim();
                int isNewsLetterSubscribed = 0;
                if (rbYes.isChecked()) {
                    isNewsLetterSubscribed = 1;
                }
                utils.showToast("Radio Value " + isNewsLetterSubscribed);
                boolean hasReadPrivacyPolicy = privacyPolicyCheck();
                if (!passVal.equals(rePassVal)) {
                    utils.showErrorDialog("Password Mis-match.");
                    return;
                }
                if (!hasReadPrivacyPolicy) {
                    utils.showAlertDialog("Please Check Privacy Policy Checkbox",
                            "You Must Read the Privacy Policy to Register");
                    return;
                }

                if (fNameVal.length() > 0 && lNameVal.length() > 0 && emailVal.length() > 0
                        && contactVal.length() > 0 && passVal.length() > 0
                        && rePassVal.length() > 0) {
                    utils.printLog("InsideLoginClicked = ", "Inside if");
                    if (utils.isNetworkConnected()) {
                        utils.printLog("InsideLoginClicked = ", "isNetwork");
                        AppConstants.setMidFixApi("register");
                        utils.printLog("RegisterUrl = ", getApiCallUrl());
                        AndroidNetworking.post(getApiCallUrl())
                                .addBodyParameter("firstname", fNameVal)
                                .addBodyParameter("lastname", lNameVal)
                                .addBodyParameter("email", emailVal)
                                .addBodyParameter("telephone", contactVal)
                                .addBodyParameter("password", passVal)
                                .addBodyParameter("newsletter", String.valueOf(isNewsLetterSubscribed))
                                .setPriority(Priority.HIGH)
                                .build()
                                .getAsJSONObject(new JSONObjectRequestListener() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        boolean success = response.optBoolean("success");
                                        if (success) {
                                            String userData = response.optString("userdata");
                                            if (userData.contains("Email Alredy Exist")) {
                                                utils.showAlertDialog(userData, "There is already an account with this email." +
                                                        "Please Login or use different email");
                                            } else {
                                                AlertDialog dialog = utils.showAlertDialogReturnDialog(
                                                        "Registration Successful!", "Account Created Successfully" +
                                                                "Please Login to Place an Order"
                                                );
                                                dialog.setButton(BUTTON_POSITIVE, "OK",
                                                        new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog,
                                                                                int which) {
                                                                ((MainActivity) getActivity()).changeFragment(101);
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
                                        utils.showToast("ErrorGettingDataFromServer");
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

    private boolean privacyPolicyCheck() {
        return termsCB.isChecked();
    }

    private void initViews(View view) {
        rgNewsletter = view.findViewById(R.id.newsletterRadioGroup);
        privacyPolicyTV = view.findViewById(R.id.privacy_policy_tv);
        clickLoginTV = view.findViewById(R.id.login_tv_in_register);
        fName = view.findViewById(R.id.fNameET);
        lName = view.findViewById(R.id.lName);
        email = view.findViewById(R.id.emailET);
        contact = view.findViewById(R.id.contactET);
        pass = view.findViewById(R.id.passET);
        confirmPass = view.findViewById(R.id.rePassET);
        rbYes = view.findViewById(R.id.rbYes);
        rbNo = view.findViewById(R.id.rbNo);
        termsCB = view.findViewById(R.id.terms_cb);
        registerBtn = view.findViewById(R.id.registerBtn);
    }

}
