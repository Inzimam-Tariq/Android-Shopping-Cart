package hostflippa.com.opencart_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RegisterActivity extends AppCompatActivity {

    Context context;
    RadioGroup rgNewsletter;
    RadioButton rbYes, rbNo;
//    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.context = this;
        initViews();
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Register Activity");

    }

    private void initViews() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        rgNewsletter = (RadioGroup)findViewById(R.id.newsletterRadioGroup);
        rgNewsletter.check(R.id.rbYes);
    }

    public void onClickHaveAccount(View view) {
        startActivity(new Intent(context, LoginActivity.class));
        finish();
    }
}
