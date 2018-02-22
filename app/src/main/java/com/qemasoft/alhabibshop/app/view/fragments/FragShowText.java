package com.qemasoft.alhabibshop.app.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONException;
import org.json.JSONObject;

import static com.qemasoft.alhabibshop.app.AppConstants.RIGHT_MENU_REQUEST_CODE;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragShowText extends MyBaseFragment {
    
    private TextView titleTV, contentTV;
    
    public FragShowText() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_show_text_data, container, false);
        initUtils();
        initViews(view);
        
        Bundle bundle = getArguments();
        if (bundle != null) {
            requestData(bundle.getString("id"));
        }
        
        return view;
    }
    
    private void requestData(String id) {
        AppConstants.setMidFixApi("getInformation/information_id/" + id);
        
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), FetchData.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, RIGHT_MENU_REQUEST_CODE);
    }
    
    private void initViews(View view) {
        titleTV = view.findViewById(R.id.title_tv);
        contentTV = view.findViewById(R.id.content_tv);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RIGHT_MENU_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String title = response.optString("title");
                    String description = response.optString("description");
                    utils.printLog("Categories", title);
                    titleTV.setText(title);
                    contentTV.setText(description);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == AppConstants.FORCE_CANCELED) {
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String msg = response.optString("message");
                    if (!msg.isEmpty()) {
                        utils.showAlert(R.string.information_text, msg,
                                false,
                                R.string.ok, null,
                                R.string.cancel_text, null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                        false,
                        R.string.ok, null,
                        R.string.cancel_text, null);
            }
        }
    }
    
    
}