package sangamsagar.signingin.Recordings;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import sangamsagar.signingin.Login;
import sangamsagar.signingin.MainActivity;
import sangamsagar.signingin.R;

public class StartingActivity extends AppCompatActivity {

    Typeface typeface;
    TextView login,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        login =(TextView) findViewById(R.id.login);
        signup=(TextView) findViewById(R.id.signup);

        typeface = Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((TextView)findViewById(R.id.login)).setTypeface(typeface);

        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((TextView)findViewById(R.id.signup)).setTypeface(typeface);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartingActivity.this, Login.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartingActivity.this, MainActivity.class));
            }
        });
    }
}
