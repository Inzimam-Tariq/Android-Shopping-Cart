package demasoft.alhabibshop.app.view.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import demasoft.alhabibshop.app.R;
import demasoft.alhabibshop.app.utils.AppConstants;
import demasoft.alhabibshop.app.utils.Preferences;
import demasoft.alhabibshop.app.utils.Utils;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class SearchActivity extends AppCompatActivity {
    
    private Utils utils;
    private SearchView searchView;
    private ImageView exitBtn;
    
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        String language = Preferences.getSharedPreferenceString(AppConstants.appContext,
                AppConstants.LANGUAGE_KEY, "ar");
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_search);
        this.utils = new Utils(this);
        
        initViews();
        setFinishOnTouchOutside(false);
        
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = MATCH_PARENT;
        lp.gravity = Gravity.TOP | Gravity.START;
        setupSearchView();
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        
    }
    
    private void initViews() {
        searchView = findViewById(R.id.search_view_);
        exitBtn = findViewById(R.id.exit_activity_btn);
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
