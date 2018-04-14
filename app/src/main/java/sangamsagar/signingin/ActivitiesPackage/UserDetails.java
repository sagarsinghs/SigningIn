package sangamsagar.signingin.ActivitiesPackage;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import sangamsagar.signingin.R;

public class UserDetails extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

   TextView email,date,month,year,phone,username,gender,text_profile;
   ImageView imageview;

    String email1;
    Button update;
    String key;
    Typeface font;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        gender = (TextView) findViewById(R.id.gendermain);
        email = (TextView) findViewById(R.id.email);
        date = (TextView) findViewById(R.id.date);
        phone = (TextView) findViewById(R.id.phone);
        username = (TextView) findViewById(R.id.username);
        text_profile = (TextView) findViewById(R.id.text_profile);



        font = Typeface.createFromAsset(getAssets(), "PomorskyUnicode.otf");
        ((TextView)findViewById(R.id.gendermain)).setTypeface(font);


        font = Typeface.createFromAsset(getAssets(), "PomorskyUnicode.otf");
        ((TextView)findViewById(R.id.username)).setTypeface(font);

        font = Typeface.createFromAsset(getAssets(), "PomorskyUnicode.otf");
        ((TextView)findViewById(R.id.date)).setTypeface(font);


        font = Typeface.createFromAsset(getAssets(), "PomorskyUnicode.otf");
        ((TextView)findViewById(R.id.email)).setTypeface(font);

        font = Typeface.createFromAsset(getAssets(), "PomorskyUnicode.otf");
        ((TextView)findViewById(R.id.phone)).setTypeface(font);

        font = Typeface.createFromAsset(getAssets(), "PomorskyUnicode.otf");
        ((TextView)findViewById(R.id.username)).setTypeface(font);

        font = Typeface.createFromAsset(getAssets(), "NordSudA.ttf");
        ((TextView)findViewById(R.id.text_profile)).setTypeface(font);






        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("newuser");



        Bundle bundle = getIntent().getExtras();


        email1 =bundle.getString("emailid");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp : dataSnapshot.getChildren())
                {

                    key = dsp.getKey();



                    String email2 =(String) dataSnapshot.child(key).child("email").getValue();
                    // Toast.makeText(EditProfileActivity.this,email2,Toast.LENGTH_SHORT).show();

                    if(email2.equals(email1))
                    {  gender = (TextView) findViewById(R.id.gendermain);
                        email = (TextView) findViewById(R.id.email);
                        date = (TextView) findViewById(R.id.date);

                        phone = (TextView) findViewById(R.id.phone);
                        username = (TextView) findViewById(R.id.username);
                        imageview =(ImageView) findViewById(R.id.imageView);

                        gender.setText( (String) dataSnapshot.child(key).child("gender").getValue().toString());
                        email.setText( (String) dataSnapshot.child(key).child("email").getValue().toString());
                        date.setText( (String) dataSnapshot.child(key).child("date").getValue().toString().trim());
                        phone.setText( (String) dataSnapshot.child(key).child("phone").getValue().toString());
                        username.setText( (String) dataSnapshot.child(key).child("username").getValue().toString());


                        String uri =(String) dataSnapshot.child(key).child("photo").getValue();
                        //Glide.with(imageview.getContext()).load(uri).into(imageview);
                        Picasso.with(UserDetails.this).load(uri).transform(new CircleTransform()).into(imageview);

                        break;



                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
