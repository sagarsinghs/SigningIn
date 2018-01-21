package sangamsagar.signingin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText password,username;
    Button login;
    TextView register;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
int flag=0;
     String  username_login;

    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user= firebaseAuth.getCurrentUser();

        setContentView(R.layout.activity_login);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("newuser");

        password =(EditText) findViewById(R.id.password_login);
        username =(EditText ) findViewById(R.id.username_login);
        register =(TextView) findViewById(R.id.register);

        login = (Button) findViewById(R.id.login);




        register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        finish();
        startActivity(new Intent(Login.this,MainActivity.class));
    }
});

        firebaseAuth = FirebaseAuth.getInstance();


                    login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(user != null){
                                //user signed-in

                                Toast.makeText(Login.this, "You are Signed-in.Welcome to Friendly Chat App.",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this,Loggedin.class));
                            } else {
                                username_login = username.getText().toString();
                                String password_login = password.getText().toString();
                                if (TextUtils.isEmpty(username_login) || TextUtils.isEmpty(password_login)) {
                                    Toast.makeText(Login.this, "Please fill the empty field", Toast.LENGTH_SHORT).show();
                                } else {
                                    firebaseAuth.signInWithEmailAndPassword(username_login, password_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {
                                                databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                                            String key = dataSnapshot.getKey().toString();
                                                            //Toast.makeText(Login.this, key, Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });
                                                Toast.makeText(Login.this, "You are successfully logged in", Toast.LENGTH_SHORT).show();
                                                finish();
                                                Intent i = new Intent(Login.this, Loggedin.class);
                                                i.putExtra("email", username_login);
                                                startActivity(i);
                                            } else {
                                                Toast.makeText(Login.this, "LoginFailed,Try again", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                                }
                            }
                        }
                    });






    }
}
