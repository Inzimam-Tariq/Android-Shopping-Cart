package com.qemasoft.alhabibshop.app.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.qemasoft.alhabibshop.app.R;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.context = this;
        initViews();

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = MATCH_PARENT;
        lp.gravity = Gravity.TOP | Gravity.START;
        setupSearchView();

    }

    private void initViews() {
        searchView = (SearchView) findViewById(R.id.search_view_);
    }

    private void setupSearchView() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(context, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(context, newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

}
