package hostflippa.com.opencart_android;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        this.context = this;
    }

    public void onCLickContactSubmit(View view) {
        Toast.makeText(context,"Your Request Submitted Successfully!\n" +
                "We will Contact you shortly",Toast.LENGTH_SHORT).show();
    }
}
