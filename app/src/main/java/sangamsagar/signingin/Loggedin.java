package sangamsagar.signingin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Loggedin extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button signout;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    TextView name,email,phone,gender;
    String name1,email1,phone1,gender1;
boolean doubleBackToExitPressedOnce = false;

    public void onBackPressed() {




        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin);
        signout =(Button) findViewById(R.id.signout);
        firebaseAuth = FirebaseAuth.getInstance();
       /* firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("newuser");

        final String email_intent =getIntent().getStringExtra("email");


        name =(TextView) findViewById(R.id.name) ;
        email =(TextView) findViewById(R.id.email);
        phone =(TextView) findViewById(R.id.phone);
        //gender= (TextView) findViewById(R.id.gender);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren())
                {
                    String key = dsp.getKey().toString();
                    String email_new = (String) dataSnapshot.child(key).child("email").getValue();

                    if(email_new.equals(email_intent))
                    {
                        email1 = (String) dataSnapshot.child(key).child("email").getValue();
                        phone1 = (String) dataSnapshot.child(key).child("phone").getValue();
                        //gender1 = (String) dataSnapshot.child(key).child("gender").getValue();
                        name1 = (String) dataSnapshot.child(key).child("username").getValue();

                        email.setText(email1);
                        //gender.setText(gender1);
                        phone.setText(phone1);
                        name.setText(name1);


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AuthUI.getInstance().signOut(Loggedin.this);
                finish();
                startActivity(new Intent(Loggedin.this,Login.class));


            }
        });
    }
}
