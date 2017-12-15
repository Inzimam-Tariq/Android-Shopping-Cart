package com.qemasoft.alhabibshop.app.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.WindowManager;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private Context context;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.utils = new Utils(this);
        utils.changeLanguage("en");
        setContentView(R.layout.activity_search);
        this.context = this;
        initViews();
        setFinishOnTouchOutside(false);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = MATCH_PARENT;
        lp.gravity = Gravity.TOP | Gravity.START;
        setupSearchView();

    }

    private void initViews() {
        searchView = findViewById(R.id.search_view_);
    }

    private void setupSearchView() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "" + query);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                utils.showToast(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(context, newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

}
