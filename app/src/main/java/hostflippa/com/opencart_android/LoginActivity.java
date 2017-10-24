package hostflippa.com.opencart_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Utils utils;
    private Context context;
    private CheckBox showPassCB;
    EditText emailET, passET;
//    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.context = this;
        this.utils = new Utils(context);
        initViews();
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("Login Activity");


        showPassCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                passET.setInputType(InputType.TYPE_CLASS_TEXT |
//                InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                utils.showToast("Checked = "+isChecked);
                if(isChecked) {
                    passET.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    passET.setInputType(129);
                }
                passET.setSelection(passET.getText().length());
            }
        });
    }

    private void initViews() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        emailET = (EditText) findViewById(R.id.emailET);
        passET = (EditText) findViewById(R.id.passwordET);
        showPassCB = (CheckBox) findViewById(R.id.showPassCB);
    }

    public void onClickSignup(View view) {
        startActivity(new Intent(context, RegisterActivity.class));
        finish();
    }
}
