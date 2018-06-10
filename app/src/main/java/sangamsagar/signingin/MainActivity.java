package sangamsagar.signingin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sangamsagar.signingin.ActivitiesPackage.CircleTransform;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    EditText email,phone,password,username;
    Spinner gender;
    TextView login;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseRemoteConfig firebaseRemoteConfig;
    Button done,click;
    String image;
    Uri imageUri;
    ImageButton imageView;
    EditText date,month,year,verify_password;

    String item;
    int flag=0;
    String email1,username1,password1,phone1,gender1;
    private static final int RC_PHOTO_PICKER = 2;
    String date1,month1,year1,verify_password1;
    FirebaseAuth firebaseAuth;
    String [] category = {"male","female"};
    Calendar mycalendar = Calendar.getInstance();
    Typeface typeface;
    SharedPreferences sharedpreferences;
    @Override
    public void onBackPressed() {
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

        date =(EditText ) findViewById(R.id.date);


        verify_password =(EditText ) findViewById(R.id.paassword_verify);
        imageView =(ImageButton) findViewById(R.id.imageView);

        done = (Button) findViewById(R.id.done);
        //click = (Button) findViewById(R.id.click);

        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference().child("newuser");
        databaseReference1 = firebaseDatabase.getReference().child("EmailandKeys");

        firebaseAuth = FirebaseAuth.getInstance();
        gender.setOnItemSelectedListener(this);

        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((EditText)findViewById(R.id.username)).setTypeface(typeface);

        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((EditText)findViewById(R.id.email)).setTypeface(typeface);

        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((EditText)findViewById(R.id.paassword)).setTypeface(typeface);

        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((EditText)findViewById(R.id.paassword_verify)).setTypeface(typeface);

        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((EditText)findViewById(R.id.date)).setTypeface(typeface);

        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((EditText)findViewById(R.id.phone)).setTypeface(typeface);


        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((Button)findViewById(R.id.done)).setTypeface(typeface);

        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((TextView)findViewById(R.id.login)).setTypeface(typeface);

        //firebaseStorage=FirebaseStorage.getInstance();
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        storageReference=FirebaseStorage.getInstance().getReference();


        ArrayAdapter newadapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,category);
        gender.setAdapter(newadapter);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });



        final DatePickerDialog.OnDateSetListener date1= new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mycalendar.set(Calendar.YEAR, year);
                mycalendar.set(Calendar.MONTH, monthOfYear);
                mycalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, date1,mycalendar.get(Calendar.YEAR), mycalendar.get(Calendar.MONTH),
                        mycalendar.get(Calendar.DAY_OF_MONTH)).show();
               // String myFormat ="MM/dd/yyyy";
               // SimpleDateFormat sdf =new SimpleDateFormat(myFormat, Locale.US);
              //  click.setText(sdf.format(mycalendar.getTime()));

            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                username1 = username.getText().toString().trim();
                email1 = email.getText().toString().trim();
                password1 = password.getText().toString().trim();





               final String date2 =date.getText().toString().trim();

                verify_password1 =verify_password.getText().toString().trim();

                phone1 = phone.getText().toString().trim();

                if (isvalid(username1, email1, password1, phone1)== false)
                {
                    Toast.makeText(MainActivity.this,"Please enter the full details",Toast.LENGTH_SHORT).show();
                    new CountDownTimer(5000, 50) {

                        @Override
                        public void onTick(long arg0) {
                            // TODO Auto-generated method stub
                            done.setBackgroundColor(Color.BLACK);
                           // done.setTextColor(Color.);
                        }

                        @Override
                        public void onFinish() {
                            done.setBackgroundColor(Color.GREEN);
                        }
                    }.start();

                }

               else  if (isvalid(username1, email1, password1, phone1)) {

                    if(!(isvalidpassword(password1,verify_password1)))
                    {
                        done.setBackgroundColor(Color.BLUE);
                        done.setTextColor(Color.GREEN);
                        Toast.makeText(MainActivity.this,"Please enter the correct password",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        firebaseAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(password1.length()<6)
                                    Toast.makeText(MainActivity.this,"Password length is less than 6",Toast.LENGTH_SHORT).show();

                                if (task.isSuccessful()) {

                                    databaseReference.addValueEventListener(new ValueEventListener() {
                                        String key = databaseReference.push().getKey();

                                        @Override

                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            databaseReference.child(key).child("username").setValue(username1);
                                            databaseReference.child(key).child("email").setValue(email1);
                                            databaseReference.child(key).child("phone").setValue(phone1);
                                            databaseReference.child(key).child("photo").setValue(image);
                                            databaseReference.child(key).child("key").setValue(key);

                                            done.setBackgroundColor(Color.WHITE);
                                            done.setTextColor(Color.BLACK);
                                            databaseReference.child(key).child("date").setValue(date2);

                                            if (item.equals("male"))
                                                databaseReference.child(key).child("gender").setValue("M");
                                            else
                                                databaseReference.child(key).child("gender").setValue("F");
                                            finish();
                                            Snackbar.make(view, "You are Successfully Registered", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                           // Toast.makeText(MainActivity.this,"You are Successfully Registered",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, Login.class));
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });



                                }
                                else {
                                    Snackbar.make(view, "You are Successfully Registered", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();

                                }}
                        });
                    }
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Complete action using"), RC_PHOTO_PICKER);


            }
        });

    }


    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        item = arg0.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
           // Toast.makeText(MainActivity.this,"Photo succesfully entered",Toast.LENGTH_SHORT).show();
            imageUri = data.getData();
            String  s  =  imageUri.toString();
            //  Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            StorageReference file= storageReference.child("Photos").child(imageUri.getLastPathSegment());
            file.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloaduri = taskSnapshot.getDownloadUrl();
                    image = downloaduri.toString();

                    //  Toast.makeText(MainActivity.this,image,Toast.LENGTH_SHORT).show();

                   // Glide.with(imageView.getContext()).load(image).into(imageView);
                    Picasso.with(MainActivity.this).load(image).transform(new CircleTransform()).into(imageView);
                    Toast.makeText(MainActivity.this,"Photo succesfully entered",Toast.LENGTH_SHORT).show();



                }
            });



        }
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
            //startActivity(new Intent(MainActivity.this, ActivitySettings.class));
            AuthUI.getInstance().signOut(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private  boolean isvalid(String username1,String email1,String password1,String phone1)
    {
        if( username1.isEmpty() || email1.isEmpty() || password1.isEmpty() || phone1.isEmpty())
            return false;
        else
            return  true;
    }
    private  boolean isvalidpassword(String password1,String verify_password1)
    {
        if(password1.equals(verify_password1))
            return true;
        else
            return false;

    }
    public  void  updateLabel()
    {
        String myFormat ="MM/dd/yyyy";
        SimpleDateFormat sdf =new SimpleDateFormat(myFormat, Locale.US);

        typeface =Typeface.createFromAsset(getAssets(),"Grundschrift-Bold.ttf");
        ((EditText)findViewById(R.id.date)).setTypeface(typeface);

        date.setText(sdf.format(mycalendar.getTime()));
    }

}
