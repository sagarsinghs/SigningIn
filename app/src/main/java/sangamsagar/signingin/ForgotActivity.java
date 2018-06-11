package sangamsagar.signingin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    String EmailHolder,PasswordHolder;
    TextView reset,cancel,reset_textview2,reset_textview;
    EditText email_enter;
    Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

            email_enter=(EditText) findViewById(R.id.email_enter);
        reset_textview=(TextView) findViewById(R.id.reset_textview);
        reset_textview2=(TextView) findViewById(R.id.reset_textview2);

            reset =(TextView) findViewById(R.id.reset);
            cancel =(TextView) findViewById(R.id.cancel);

        typeface = Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((TextView)findViewById(R.id.reset_textview)).setTypeface(typeface);

        typeface = Typeface.createFromAsset(getAssets(),"Grundschrift-Regular.otf");
        ((TextView)findViewById(R.id.reset_textview2)).setTypeface(typeface);

        typeface = Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((EditText)findViewById(R.id.email_enter)).setTypeface(typeface);

        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((TextView)findViewById(R.id.reset)).setTypeface(typeface);

        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((TextView)findViewById(R.id.cancel)).setTypeface(typeface);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean s = checkedittextempty_forgetpassword();
                if(s==true) {

                    final String newuser = email_enter.getText().toString();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(newuser)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(ForgotActivity.this, "forget password on complete", Toast.LENGTH_SHORT).show();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
                else
                    Toast.makeText(ForgotActivity.this, "Please enter the valid emailId", Toast.LENGTH_SHORT).show();
            }
        });
         cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(ForgotActivity.this,Login.class));
             }
         });
    }
    public Boolean checkedittextempty() {
        EmailHolder = email_enter.getText().toString();

        if (TextUtils.isEmpty(EmailHolder)) {
            return false;
        } else
            return true;
    }

    //for forget password checking
    public Boolean checkedittextempty_forgetpassword() {
        EmailHolder = email_enter.getText().toString();

        if (TextUtils.isEmpty(EmailHolder)) {
            return false;
        } else
            return true;
    }
}
