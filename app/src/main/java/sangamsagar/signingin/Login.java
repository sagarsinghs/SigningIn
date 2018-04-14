package sangamsagar.signingin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    // Creating EditText.
    EditText email, password ;
    LinearLayout colorchange;
    TextView forgetpasswod,textview1;
    String email2;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    String EmailHolder, PasswordHolder,userHolder;

    // Creating buttons.
    Button Login;
    TextView SignUP;

    // Creating progress dialog.
    ProgressDialog progressDialog;

    // Creating FirebaseAuth object.
    FirebaseAuth firebaseAuth;
    String user,mobile,gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("newuser");
        colorchange = (LinearLayout) findViewById(R.id.colorchange);
        //colorchange.setBackgroundColor(Color.WHITE);
        // Assign ID's to EditText.
        email = (EditText) findViewById(R.id.editText_email);
        password = (EditText) findViewById(R.id.editText_password);

        forgetpasswod = (TextView) findViewById(R.id.forgetpassword);


        //textview1 = (TextView) findViewById(R.id.textview1);

        // Assign ID's to button.
        Login = (Button) findViewById(R.id.button_login);
        SignUP = (TextView) findViewById(R.id.button_SignUP);

        progressDialog = new ProgressDialog(Login.this);

      // Typeface typeface = Typeface.createFromAsset(getAssets(),"RursusCompactMono.ttf");
       // ((TextView)findViewById(R.id.textview1)).setTypeface(typeface);

        Typeface typeface = Typeface.createFromAsset(getAssets(),"RursusCompactMono.ttf");
        ((TextView)findViewById(R.id.editText_email)).setTypeface(typeface);

         typeface = Typeface.createFromAsset(getAssets(),"RursusCompactMono.ttf");
        ((TextView)findViewById(R.id.editText_password)).setTypeface(typeface);

        typeface = Typeface.createFromAsset(getAssets(),"PomorskyUnicode.otf");
        ((Button)findViewById(R.id.button_login)).setTypeface(typeface);

        typeface = Typeface.createFromAsset(getAssets(),"PomorskyUnicode.ttf");
        ((TextView)findViewById(R.id.button_SignUP)).setTypeface(typeface);

        typeface = Typeface.createFromAsset(getAssets(),"ArchitectsDaughter.ttf");
        ((TextView)findViewById(R.id.forgetpassword)).setTypeface(typeface);


        // Assign FirebaseAuth instance to FirebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance();

        //final String newemail=  "sangamsagar626@gmail.com";


        if (firebaseAuth.getCurrentUser() != null) {
            finish();
             email2 = firebaseAuth.getCurrentUser().getEmail();
            Intent intent = new Intent(Login.this,NavigationActivity.class);

            intent.putExtra("emailid",email2);
            Log.e("login",email2);
            Toast.makeText(Login.this,email2,Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }

        forgetpasswod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean s = checkedittextempty_forgetpassword();
                if(s==true) {

                    final String newuser = email.getText().toString();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(newuser)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Login.this, "forget password on complete", Toast.LENGTH_SHORT).show();
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Email Sent", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
                else
                    Toast.makeText(Login.this, "Please enter the valid emailId", Toast.LENGTH_SHORT).show();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking if the emailid is empty or not
                Boolean s = checkedittextempty();
                if(s==true)
                {
                    loginfunction();
                }
                else
                {
                    Toast.makeText(Login.this,"Please fill the Correct details",Toast.LENGTH_SHORT).show();
                }

            }
        });

        SignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,MainActivity.class));
            }
        });
    }
public  void loginfunction()
{
    progressDialog.setMessage("please wait till the login opens");
    progressDialog.show();

    firebaseAuth.signInWithEmailAndPassword(EmailHolder,PasswordHolder).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful())
            {
                progressDialog.dismiss();
                finish();
                String email1 = firebaseAuth.getCurrentUser().getEmail();
                Intent intent = new Intent(Login.this,NavigationActivity.class);

                intent.putExtra("emailid",email1);
                startActivity(intent);
            }
            else
            {
                progressDialog.dismiss();
                Toast.makeText(Login.this,"Sorry,Try again",Toast.LENGTH_SHORT).show();
            }
        }
    });


}

                    public Boolean checkedittextempty() {
                        EmailHolder = email.getText().toString();
                        PasswordHolder = password.getText().toString();

                        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {
                            return false;
                        } else
                            return true;
                    }

                    //for forget password checking
             public Boolean checkedittextempty_forgetpassword() {
            EmailHolder = email.getText().toString();

        if (TextUtils.isEmpty(EmailHolder)) {
            return false;
        } else
            return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);;
    }
}
