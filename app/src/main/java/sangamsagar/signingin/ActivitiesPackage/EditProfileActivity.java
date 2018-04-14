package sangamsagar.signingin.ActivitiesPackage;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sangamsagar.signingin.R;

public class EditProfileActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    StorageReference storageReference;

    EditText date,gender,mobile, username;
    TextView textview_update;

    Uri imageUri;

    String email1;
    Button update,date_change;
    String key;
    Button male,female;

    String image;
    String name1,phone1 ,date1 ,gender1;
    private static final int RC_PHOTO_PICKER = 2;

    ImageView imageView;

    Calendar mycalendar = Calendar.getInstance();
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        mobile = (EditText) findViewById(R.id.mobile);
        username= (EditText)findViewById(R.id.username);

        date = (EditText) findViewById(R.id.date);
        gender= (EditText)findViewById(R.id.gender);

        storageReference= FirebaseStorage.getInstance().getReference();

        textview_update= (TextView)findViewById(R.id.textview_update);

        update =(Button) findViewById(R.id.update);
        date_change =(Button) findViewById(R.id.date_change);

        imageView =(ImageView) findViewById(R.id.imageView);

        male =(Button) findViewById(R.id.male);
        female =(Button) findViewById(R.id.female);

        textview_update = (TextView) findViewById(R.id.textview_update);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("newuser");

        Typeface typeface = Typeface.createFromAsset(getAssets(), "RursusCompactMono.ttf");
        ((TextView)findViewById(R.id.username)).setTypeface(typeface);

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
                   // Toast.makeText(EditProfileActivity.this,email1,Toast.LENGTH_SHORT).show();
                    if(email2.equals(email1))
                    {
                       // Toast.makeText(EditProfileActivity.this,"yes you are right", Toast.LENGTH_SHORT).show();
                        mobile.setText((String) dataSnapshot.child(key).child("phone").getValue());
                        username.setText((String) dataSnapshot.child(key).child("username").getValue());
                        date.setText((String) dataSnapshot.child(key).child("date").getValue());
                        gender.setText((String) dataSnapshot.child(key).child("gender").getValue());
                       // name.setText((String) dataSnapshot.child(key).child("name").getValue());

                        String uri =(String) dataSnapshot.child(key).child("photo").getValue();
                    //    Toast.makeText(EditProfileActivity.this,"current -> "+uri, Toast.LENGTH_SHORT).show();
                      //  //Glide.with(imageview.getContext()).load(uri).into(imageview);
                        Picasso.with(EditProfileActivity.this).load(uri).transform(new CircleTransform()).into(imageView);

                           break;

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender.setText("M");
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender.setText("F");
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
               databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        name1 = username.getText().toString();
                        phone1 = mobile.getText().toString();
                        date1 = date.getText().toString();
                        gender1 = gender.getText().toString();



                        databaseReference.child(key).child("username").setValue(name1);
                        databaseReference.child(key).child("phone").setValue(phone1);
                        databaseReference.child(key).child("date").setValue(date1);
                        databaseReference.child(key).child("gender").setValue(gender1);
                       // databaseReference.child(key).child("photo").setValue(image);

                      //  Snackbar.make(view, "Your Profile has been uploaded", Snackbar.LENGTH_LONG)
                           //     .setAction("Action", null).show();


                        //databaseReference.child(key).child("username").setValue(username1);
                       // mobile.setText((String) dataSnapshot.child(key).child("phone").getValue());
                       // username.setText((String) dataSnapshot.child(key).child("username").getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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

        date_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditProfileActivity.this, date1,mycalendar.get(Calendar.YEAR), mycalendar.get(Calendar.MONTH),
                        mycalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        //for the photo activity
      /*  imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Complete action using"), RC_PHOTO_PICKER);

            }
        });*/
        // Toast.makeText(MainActivity.this,imageUri.toString(),Toast.LENGTH_SHORT).show();

        //Glide.with(imageView.getContext()).load(imageUri.toString()).into(imageView);
    }




//for the photo activity

  /*  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Toast.makeText(EditProfileActivity.this, "Photo succesfully entered", Toast.LENGTH_SHORT).show();
            imageUri = data.getData();
            String s = imageUri.toString();

             Toast.makeText(EditProfileActivity.this,"s value is - >" +s,Toast.LENGTH_SHORT).show();

            StorageReference file1 = storageReference.child("Photos").child(imageUri.getLastPathSegment());
            file1.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(EditProfileActivity.this,"newimage -> "+image, Toast.LENGTH_SHORT).show();
                    Uri downloaduri = taskSnapshot.getDownloadUrl();
                    image = downloaduri.toString();
                    Toast.makeText(EditProfileActivity.this,"newimage -> "+image, Toast.LENGTH_SHORT).show();

                    //  Toast.makeText(MainActivity.this,image,Toast.LENGTH_SHORT).show();

                    Picasso.with(EditProfileActivity.this).load(image).transform(new CircleTransform()).into(imageView);
                    Toast.makeText(EditProfileActivity.this, "Photo succesfully entered", Toast.LENGTH_SHORT).show();
                }
            });

        }


        }*/
    public  void  updateLabel()
    {
        String myFormat ="MM/dd/yyyy";
        SimpleDateFormat sdf =new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(mycalendar.getTime()));
    }
}
