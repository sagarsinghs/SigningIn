package sangamsagar.signingin.Recordings;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sangamsagar.signingin.R;

public class RecordingActivityList extends AppCompatActivity {

    TextView norecordinglist;
    ListView listView_recording;
    List<RecordingActivityObject> list;
    Button recordingactivity;
    RecordingActivityAdapter recordingActivityAdapter;
    MediaPlayer mediaPlayer;
    MediaRecorder mediaRecorder;
     int k =0,flag=0;
    String s,email1;
    String email3;
    Button stop;
     String key,keyvalue;
     int norecordingpresent=0;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_list);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference().child("newuser");
        databaseReference1= firebaseDatabase.getReference().child("newuser");


        Bundle bundle = getIntent().getExtras();
        email1 =bundle.getString("emailid");




        listView_recording = (ListView) findViewById(R.id.listview_recording);
        list = new ArrayList<>();

        recordingActivityAdapter = new RecordingActivityAdapter(this, R.layout.textlistview, list);
        listView_recording.setAdapter(recordingActivityAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(final DataSnapshot dsp : dataSnapshot.getChildren())
                {

                   key= dsp.getKey();
                    String email2 =(String) dataSnapshot.child(key).child("email").getValue();



                    if(email2.equals(email1))
                    {

                        keyvalue = (String) dataSnapshot.child(key).child("key").getValue();
                        passthevalue(keyvalue);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }


    public  void passthevalue(String s)
    {
        final String keyhai =s;

        databaseReference1= firebaseDatabase.getReference().child("newuser").child(s).child("Recording");


        databaseReference1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot1) {
                for (final DataSnapshot dsp1 : dataSnapshot1.getChildren()) {
                    String key2 = dsp1.getKey();


                    String k = (String)dataSnapshot1.child(key2).child("stopwatch").getValue();
                    String s =(String) dataSnapshot1.child(key2).child("voice").getValue();
                    String recording_name =(String) dataSnapshot1.child(key2).child("recordingname").getValue();



                    RecordingActivityObject recordingActivityObject =new RecordingActivityObject(s,k,recording_name);
                    recordingActivityAdapter.add(recordingActivityObject);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView_recording.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int i, long l) {


            //    norecordinglist.setVisibility(View.INVISIBLE);
                 /*   if (flag == 1) {
                        mediaPlayer.stop();
                        flag = 0;
                    }*/

                    //mediaPlayer = new MediaPlayer();

                    databaseReference1.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {

                            for (final DataSnapshot dsp1 : dataSnapshot1.getChildren()) {

                                if (k < i) {
                                    k++;
                                    // Toast.makeText(RecordingActivityList.this,"continue" +Integer.toString(i), Toast.LENGTH_SHORT).show();
                                    continue;
                                } else {
                                    ////Toast.makeText(RecordingActivityList.this,"else" +Integer.toString(i), Toast.LENGTH_SHORT).show();
                                    String key2 = dsp1.getKey();

                                    String s = (String) dataSnapshot1.child(key2).child("voice").getValue();
                                    String recording = (String) dataSnapshot1.child(key2).child("recordingname").getValue();
                                    String timer = (String) dataSnapshot1.child(key2).child("stopwatch").getValue();
                                    Toast.makeText(RecordingActivityList.this,timer, Toast.LENGTH_SHORT).show();

                                    k=0;   flag = 1;
                                    Intent intent = new Intent(RecordingActivityList.this,VoiceBackground.class);
                                   // Toast.makeText(RecordingActivityList.this,"All recordings timer" +timer, Toast.LENGTH_SHORT).show();
                                    intent.putExtra("Timer",timer);
                                    intent.putExtra("Recording",s);
                                    startActivity(intent);



                               /*     try {

                                        mediaPlayer.setDataSource(s);

                                        mediaPlayer.prepare();
                                        Toast.makeText(RecordingActivityList.this,recording, Toast.LENGTH_SHORT).show();
                                        k = 0;
                                    }
                                    mediaPlayer.start();*/
                                    break;


                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }





        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.stop();
        //startActivity(new Intent(RecordingActivityList.this, NavigationActivity.class));
    }
}

