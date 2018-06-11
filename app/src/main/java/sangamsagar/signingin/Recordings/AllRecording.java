package sangamsagar.signingin.Recordings;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import sangamsagar.signingin.R;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static sangamsagar.signingin.Recordings.RecordingActivity.RequestPermissionCode;

public class AllRecording extends AppCompatActivity {


    private StorageReference mstoragereference;
    String timer,keys_recording;
    ListView listView_recording;
    List<RecordingActivityObject> list;
    RecordingActivityAdapter recordingActivityAdapter;
    MediaPlayer mediaPlayer;
    MediaRecorder mediaRecorder;
    int k =0,flag=0;
    String s,email1;
    String email3;
    int counting=0;
    ImageButton likes;   TextView comments;
    Button stop;
    String key,keyvalue;
    int count_comments=0,count_likes=0;
     String keyhai,key_found,username_login;
     TextView likes_count,comments_count;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1,databaseReference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_list);

        mstoragereference = FirebaseStorage.getInstance().getReference();


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference().child("newuser");
        databaseReference1= firebaseDatabase.getReference().child("newuser");


        databaseReference2= firebaseDatabase.getReference().child("newuser");

        //stop = (Button) findViewById(R.id.stop);



        Bundle bundle = getIntent().getExtras();
        email1 =bundle.getString("emailid");

        likes_count = (TextView) findViewById(R.id.likes_count);
        comments_count = (TextView) findViewById(R.id.comments_count);

        listView_recording = (ListView) findViewById(R.id.listview_recording);
        list = new ArrayList<>();

        recordingActivityAdapter = new RecordingActivityAdapter(this, R.layout.textlistview, list);
        listView_recording.setAdapter(recordingActivityAdapter);

        ///counting the total number of likes and comments
        count_likes_comments();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp : dataSnapshot.getChildren())
                {

                    key = dsp.getKey();



                    String email2 =(String) dataSnapshot.child(key).child("email").getValue();


                    if(email2.equals(email1))
                    {
                         String username_login =(String) dataSnapshot.child(key).child("username").getValue();

                        key_found(key,username_login);
                        break;



                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(final DataSnapshot dsp : dataSnapshot.getChildren())
                {

                    key= dsp.getKey();
                    String email2 =(String) dataSnapshot.child(key).child("email").getValue();
                    String username = (String) dataSnapshot.child(key).child("username").getValue();

                        keyvalue = (String) dataSnapshot.child(key).child("key").getValue();
                        passthevalue(keyvalue,username);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

    public  void passthevalue(final String s, final String username) {
         keyhai = s;
        final String username1 = username;
        databaseReference1.child(s).child("Recording").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                for (final DataSnapshot dsp1 : dataSnapshot1.getChildren()) {
                    String key2 = dsp1.getKey();

                    String k = (String) dataSnapshot1.child(key2).child("stopwatch").getValue();
                    String string = (String) dataSnapshot1.child(key2).child("voice").getValue();
                    String recording_name = (String) dataSnapshot1.child(key2).child("recordingname").getValue();
                    String key_value = (String) dataSnapshot1.child(key2).child("key").getValue();
                    String key_recording =(String) dataSnapshot1.child(key2).child("keyparent").getValue();
                     Long comments = dataSnapshot1.child(key2).child("Comments").getChildrenCount();
                    Long Likes = dataSnapshot1.child(key2).child("Likes").getChildrenCount();

                    //Toast.makeText(AllRecording.this,"comments section" +Long.toString(comments), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(AllRecording.this,"comments section" +Long.toString(Likes), Toast.LENGTH_SHORT).show();

                count_likes=0;
                count_comments=0;
                    key_for_recording(key2);

                    RecordingActivityObject recordingActivityObject = new RecordingActivityObject(string, k, username1, recording_name,key_value,key_recording,Long.toString(comments),Long.toString(Likes));
                    recordingActivityAdapter.add(recordingActivityObject);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //counting the likes and comments


        listView_recording.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                         TextView v = (TextView) view.findViewById(R.id.new_song);
                         TextView time = (TextView) view.findViewById(R.id.duration);
                         final TextView username_te = (TextView) view.findViewById(R.id.username);
                         final String username_edited= username_te.getText().toString();
                         TextView key_newest = (TextView) view.findViewById(R.id.keys);
                        TextView key_recording = (TextView) view.findViewById(R.id.key_recording);


                        String key_Recording1 = key_recording.getText().toString();

                        timer = time.getText().toString();
                        String s = v.getText().toString();
                        String username_text = username_te.getText().toString();


                    Intent intent = new Intent(AllRecording.this,VoiceBackground.class);

                    intent.putExtra("Timer",timer);
                    intent.putExtra("Recording",s);
                    intent.putExtra("username",username_text);
                    intent.putExtra("email_user",email1);
                    intent.putExtra("KeyMain",key_found);
                    intent.putExtra("key_recording_main",key_Recording1);
                    intent.putExtra("username_login",username_login);
                Toast.makeText(AllRecording.this,"username_login  is " +  username_login,Toast.LENGTH_SHORT).show();

                Toast.makeText(AllRecording.this,"username_login  is " +  username_login,Toast.LENGTH_SHORT).show();


                Toast.makeText(AllRecording.this,username_login,Toast.LENGTH_SHORT).show();

                    intent.putExtra("keys_recording",key_newest.getText().toString());
                Toast.makeText(AllRecording.this,"keys recording is ----- " +key_newest.getText().toString(),Toast.LENGTH_SHORT).show();

                    startActivity(intent);


                }




        });


    }

public void key_for_recording(String key_recording)
{
    keys_recording = key_recording;
}
public  void  key_found(String key,String username_login)
{
    key_found = key;
    this.username_login =username_login;
}

public void count_likes_comments()
{

}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.stop();
        //startActivity(new Intent(RecordingActivityList.this, NavigationActivity.class));
    }
    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        Log.e("permission for the ","request granted");
        ActivityCompat.requestPermissions(AllRecording.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }
}
