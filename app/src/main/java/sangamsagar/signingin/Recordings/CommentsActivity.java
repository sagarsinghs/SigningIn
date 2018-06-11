package sangamsagar.signingin.Recordings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sangamsagar.signingin.R;

public class CommentsActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference4, databaseReference3;
    ListView alert_listview_inside;
    EditText comment_section;
    Button main_comment;
    List<VoiceRecordingObjects> list_alert;

    VoiceRecordingComments1 voiceRecordingadapter;
    VoiceRecordingObjects voiceRecordingObjects;
    int flag=0;
    String passed_key, keys_recording, key_obtain, username_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comments);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference4 = firebaseDatabase.getReference().child("newuser");
        databaseReference3 = firebaseDatabase.getReference().child("newuser");

        Bundle intent = getIntent().getExtras();
        passed_key = intent.getString("passed_key");
        keys_recording = intent.getString("keys_recording");
        key_obtain = intent.getString("key_obtain");
        username_login = intent.getString("username_login");

        comment_section = (EditText) findViewById(R.id.comment_section);
        main_comment = (Button) findViewById(R.id.main_comment);

        display_the_comments();
        list_alert = new ArrayList<>();
        alert_listview_inside = (ListView) findViewById(R.id.alert_listview_inside);
        voiceRecordingadapter = new VoiceRecordingComments1(this, R.layout.activity_comments_layout, list_alert);
        alert_listview_inside.setAdapter(voiceRecordingadapter);





        main_comment.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick (View view){


        databaseReference4.child(passed_key).child("Recording").child(keys_recording).child("Comments").
                addListenerForSingleValueEvent(new ValueEventListener() {


                    String key3 = databaseReference4.push().getKey();

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        final String s = comment_section.getText().toString();

                        if(s.equals(null) || s.equals(" ") ) {
                            Toast.makeText(CommentsActivity.this, "Please write the comments", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            databaseReference4.child(passed_key).child("Recording").child(keys_recording).child("Comments")
                                    .child(key_obtain).child("Zcomments_done").child(key3).child("comments").setValue(s);

                            databaseReference4.child(passed_key).child("Recording").child(keys_recording).child("Comments")
                                    .child(key_obtain).child("Zcomments_done").child(key3).child("username").setValue(username_login);


                            voiceRecordingObjects = new VoiceRecordingObjects(s, username_login);
                            voiceRecordingadapter.add(voiceRecordingObjects);
                            comment_section.setText(" ");

                        }

                        //Toast.makeText(CommentsActivity.this, passed_key + keys_recording + key_obtain, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });

    }
    });


    }
    public  void display_the_comments()
    {

        databaseReference4.child(passed_key).child("Recording").addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot dsp : dataSnapshot.getChildren()) {
                    final String keys = dsp.getKey();

                    //  Toast.makeText(VoiceBackground.this,keys,Toast.LENGTH_SHORT).show();
                    if (keys.equals(keys_recording)) {

                        databaseReference3.child(passed_key).child("Recording").child(keys_recording).child("Comments").child(key_obtain).child("Zcomments_done").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot1) {
                                for (DataSnapshot dsp : dataSnapshot1.getChildren()) {
                                    String key_pure_new = dsp.getKey();


                                    String comments = (String) dataSnapshot1.child(key_pure_new).child("comments").getValue();
                                    String username = (String) dataSnapshot1.child(key_pure_new).child("username").getValue();

                                    if(!((comments.equals(null)) || (comments.equals(" ")) || comments.equals("")))
                                    {
                                        flag=1;
                                        voiceRecordingObjects = new VoiceRecordingObjects(comments, username);
                                    }

                                    if(flag==0)
                                        voiceRecordingObjects = new VoiceRecordingObjects("NO REPLIES YET", "BE THE FIRST TO REPLY");
                                    voiceRecordingadapter.add(voiceRecordingObjects);

                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}