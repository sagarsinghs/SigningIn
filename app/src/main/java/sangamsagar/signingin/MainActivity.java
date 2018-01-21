package sangamsagar.signingin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

EditText email,phone,password,username;
Spinner gender;
TextView login;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
Button done;
String item;
int flag=0;
String email1,username1,password1,phone1,gender1;
FirebaseAuth firebaseAuth;
    String [] category = {"male","female"};

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(MainActivity.this,Login.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login= (TextView) findViewById(R.id.login);
        email =(EditText ) findViewById(R.id.email);
        phone =(EditText ) findViewById(R.id.phone);
        password =(EditText ) findViewById(R.id.paassword);
        username =(EditText ) findViewById(R.id.username);
        gender =(Spinner) findViewById(R.id.gender);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference().child("newuser");
        done = (Button) findViewById(R.id.done);
        firebaseAuth = FirebaseAuth.getInstance();
        gender.setOnItemSelectedListener(this);


        ArrayAdapter newadapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,category);
        gender.setAdapter(newadapter);


        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         startActivity(new Intent(MainActivity.this, Login.class));
                                     }
                                 });
                done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username1 = username.getText().toString().trim();
                email1 = email.getText().toString().trim();
                password1 = password.getText().toString().trim();

                phone1 = phone.getText().toString().trim();



                if (isvalid(username1, email1, password1, phone1)) {
                    firebaseAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Toast.makeText(MainActivity.this, username1, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(MainActivity.this, password1, Toast.LENGTH_SHORT).show();
                            if(password1.length()<6)
                                Toast.makeText(MainActivity.this,"Password length is less than 6",Toast.LENGTH_SHORT).show();

                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Check your email for verification", Toast.LENGTH_SHORT).show();
                              sendEmailVerification();
                                   // Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();


                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        String key = databaseReference.push().getKey();

                                        @Override

                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            databaseReference.child(key).child("username").setValue(username1);
                                            databaseReference.child(key).child("email").setValue(email1);
                                            databaseReference.child(key).child("phone").setValue(phone1);
                                            if (item.equals("male"))
                                                databaseReference.child(key).child("gender").setValue("M");
                                            else
                                                databaseReference.child(key).child("gender").setValue("F");

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                    finish();
                                    startActivity(new Intent(MainActivity.this, Login.class));
                                }



                            else {
                                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                            }}
                        });
                    }
                }
            });

        }


    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
         item = arg0.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private  boolean isvalid(String username1,String email1,String password1,String phone1)
    {
        if( username1.isEmpty() || email1.isEmpty() || password1.isEmpty() || phone1.isEmpty())
            return false;
        else
            return  true;
    }
    public void sendEmailVerification()
    {
        FirebaseUser user= firebaseAuth.getCurrentUser();
        if(user!=null)
        {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        flag=1;
                        Toast.makeText(MainActivity.this,"Email Verification Successful",Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();

                    }
                }

            });

        }

    }

}
