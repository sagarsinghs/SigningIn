package sangamsagar.signingin;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import sangamsagar.signingin.ActivitiesPackage.CircleTransform;
import sangamsagar.signingin.ActivitiesPackage.EditProfileActivity;
import sangamsagar.signingin.ActivitiesPackage.UserDetails;
import sangamsagar.signingin.Recordings.AllRecording;
import sangamsagar.signingin.Recordings.RecordingActivity;
import sangamsagar.signingin.Recordings.RecordingActivityList;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView android_studio,emailid;
    ImageView nav_image;
    Button login;
    FirebaseAuth auth;
    String uri;

    GoogleApiClient mGoogleApiClient;
    Button logout;
     String email1;


    private FirebaseAuth mAuth;
// ...

//Image changing
Uri imageUri;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NavigationView navigationActivity =(NavigationView)findViewById( R.id.nav_view);
        View hview= navigationActivity.getHeaderView(0);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference().child("newuser");

        mAuth = FirebaseAuth.getInstance();

        android_studio =(TextView) hview.findViewById(R.id.android_studio);
        emailid =(TextView) hview.findViewById(R.id.emailid);
       nav_image =(ImageView) hview.findViewById(R.id.image_navigation);

        Bundle bundle = getIntent().getExtras();


      email1 =bundle.getString("emailid");
        Log.e("navigation",email1);


        //CircleImageView imageView = (CircleImageView) findViewById(R.id.image);


        databaseReference.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp : dataSnapshot.getChildren())
                {

                    String key = dsp.getKey();

                    String email2 =(String) dataSnapshot.child(key).child("email").getValue();

                    Log.e("the aaccounts are",email2);
                    if(email2.equals(email1))
                    {

                        android_studio.setText((String) dataSnapshot.child(key).child("username").getValue());
                        emailid.setText((String) dataSnapshot.child(key).child("phone").getValue());

                         uri =(String) dataSnapshot.child(key).child("photo").getValue();

                        Picasso.with(NavigationActivity.this).load(uri).transform(new CircleTransform()).into(nav_image);


                            break;

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signout) {



                FirebaseUser currentUser = mAuth.getCurrentUser();

            }
            FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(NavigationActivity.this,Login.class));

        return super.onOptionsItemSelected(item);
    }
Typeface font;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {





            Intent intent = new Intent(NavigationActivity.this, UserDetails.class);
            intent.putExtra("emailid",email1);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {

           Intent intent = new Intent(NavigationActivity.this, EditProfileActivity.class);
           intent.putExtra("emailid",email1);
           startActivity(intent);


        } else if (id == R.id.nav_slideshow) {


            Intent intent = new Intent(NavigationActivity.this, RecordingActivity.class);
            intent.putExtra("emailid",email1);
            startActivity(intent);
               // startActivity(new Intent(NavigationActivity.this, RecordingActivity.class));
        } else if (id == R.id.nav_manage) {



            Intent intent = new Intent(NavigationActivity.this, RecordingActivityList.class);
            intent.putExtra("emailid",email1);
            startActivity(intent);
        }
        else if (id == R.id.all_recording) {



            Intent intent = new Intent(NavigationActivity.this, AllRecording.class);
            intent.putExtra("emailid",email1);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}