package sangamsagar.signingin.Recordings;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import sangamsagar.signingin.R;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static sangamsagar.signingin.Recordings.RecordingActivity.RequestPermissionCode;

public class VoiceBackground extends AppCompatActivity {
    ProgressBar pb;
    TextView timer;
    ImageButton play;
    int flag = 0;
    CountDownTimer countDownTimer;
    ImageView likes;
    ImageView comments;
    EditText comments_section;
    Button main_comment;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference2, databaseReference3,databaseReference4;
    String key, passed_key, key_generation, keys_recording_main;
    TextView liked, comments_text;

    ProgressBar progressBar;
    private static final String FORMAT = "%02d:%02d:%02d";
    Thread thread;
    MediaPlayer mediaPlayer;
    Long changed;
    Long s_long;
    String sp, email_got_user;
    int s, flag1 = 0;
    String username, keys_recording, username_login;
    ListView alert_listview, alert_listview_voicerecording;
    List<VoiceRecordingObjects> list_alert, list_alert1;
    VoiceRecordingComments voiceRecordingadapter, voicerecordingadapter1;
    VoiceRecordingObjects voiceRecordingObjects;
    Button send;
    int username_found = 0;
    Handler mHandler;
String time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHandler = new Handler();

        this.mHandler.postDelayed(m_Runnable,5000);


        setContentView(R.layout.activity_voice_background);
        timer = (TextView) findViewById(R.id.timer);
        comments_section =(EditText)findViewById(R.id.comment_section);
        play = (ImageButton) findViewById(R.id.play);
        likes = (ImageView) findViewById(R.id.likes);
        comments = (ImageView) findViewById(R.id.comments);
        main_comment =(Button) findViewById(R.id.main_comment);
        // liked = (TextView) findViewById(R.id.liked);
        // comments_text =(TextView) findViewById(R.id.comments_text);



        play.setImageResource(R.drawable.ic_pause_black);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Likes");
        databaseReference2 = firebaseDatabase.getReference().child("newuser");
        databaseReference3 = firebaseDatabase.getReference().child("newuser");
        databaseReference4 = firebaseDatabase.getReference().child("newuser");

        Bundle intent = getIntent().getExtras();
        String get_recording = intent.getString("Recording");
        time = intent.getString("Timer");
        Toast.makeText(VoiceBackground.this, time,Toast.LENGTH_SHORT).show();
        username = intent.getString("username");
        final String email_user = intent.getString("email_user");
        keys_recording = intent.getString("keys_recording");
        keys_recording_main = intent.getString("key_recording_main");
        username_login = intent.getString("username_login");

        //Toast.makeText(VoiceBackground.this,"username_login  is " +  username_login,Toast.LENGTH_SHORT).show();




        //updated_comments();
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {


                    key = dsp.getKey();
                    //   String email2 = (String) dataSnapshot.child(key).child("email").getValue();

                    if (key.equals(keys_recording_main)) {
                        pass_the_key(key);
                        //clicking_comments(key);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //diaplayinf the listview of comments
        list_alert1 = new ArrayList<>();
        alert_listview_voicerecording = (ListView) findViewById(R.id.alert_listview_voicerecording);

       // setListViewHeightBasedOnChildren(alert_listview_voicerecording);

        voicerecordingadapter1 = new VoiceRecordingComments(this, R.layout.alert_dialog_textview, list_alert1);
        alert_listview_voicerecording.setAdapter(voicerecordingadapter1);

        clicking_like();

        databaseReference2.child(keys_recording_main).child("Recording").child(keys_recording).child("Likes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String s1 = dsp.getKey();
                    String username = (String) dataSnapshot.child(s1).child("userlike").getValue();
                    if (username.equals(username_login)) {

                        username_found = 1;
                        //Toast.makeText(VoiceBackground.this,"username found"+Integer.toString(username_found),Toast.LENGTH_SHORT).show();
                        if (username_found == 1) {
                            String setting = "Liked";
                            ////liked.setText(setting);
                            //  liked.setTextColor(Color.BLUE);
                            //likes.setColorFilter(Color.BLUE);
                            likes.setImageResource(R.drawable.heart_full);
                            //likes.setEnabled(false);
                            // username_found=2;
                        }
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       // final String recording = intent.getString("Recording");

       for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) == ':') {

                int l = time.length();
                int len = l - 2;
                String sp = time.substring(len, len + 2);
                s = Integer.parseInt(sp);
                flag1 = 1;
                break;
            }


        }
        for (int i = 0; i < time.length(); i++) {
            if (time.charAt(i) == ':') {

                int l = time.length();
                int len = l - 2;
                String sp = time.substring(len, len + 2);
                s = Integer.parseInt(sp);
                flag1 = 1;
                break;
            }


        }
        if (flag1 == 0) {
            s_long = Long.parseLong(time);
            s_long = s_long / 100;
            s_long = s_long % 100;
            String sk = Long.toString(s_long);
            s = Integer.parseInt(sk);
            flag1 = 1;

        }
        final String recording = intent.getString("Recording");

        mediaPlayer = new MediaPlayer();
        if (checkPermission()) {
            try {

                mediaPlayer.setDataSource(recording);
                mediaPlayer.prepare();


            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        } else {
            requestPermission();
        }


        countDownTimer = new CountDownTimer(s * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                changed = millisUntilFinished;
                timer.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));


            }

            public void onFinish() {
                timer.setText("00:00:00");
            }
        }.start();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    countDownTimer.cancel();
                    mediaPlayer.pause();
                    flag = 1;
                    play.setImageResource(R.drawable.ic_play_arrow_black1);


                } else {
                    countDownTimer.start();
                    mediaPlayer.start();
                    flag = 0;
                    play.setImageResource(R.drawable.ic_pause_black);
                }
            }
        });


    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {

            clicking_comments(passed_key);
            VoiceBackground.this.mHandler.postDelayed(m_Runnable, 1000);
        }

    };
            // Do something
            public void clicking_comments(final String passed_key) {

               // Toast.makeText(VoiceBackground.this,passed_key,Toast.LENGTH_SHORT).show();
              //  Toast.makeText(VoiceBackground.this,keys_recording,Toast.LENGTH_SHORT).show();

                main_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String comments1 = comments_section.getText().toString();
                        //Toast.makeText(VoiceBackground.this,comments1,Toast.LENGTH_SHORT).show();
                        databaseReference2.child(passed_key).child("Recording").addListenerForSingleValueEvent(new ValueEventListener() {

                            String key2 = databaseReference2.push().getKey();

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    String keys_hai = dsp.getKey();
                                    if(comments1.equals(null) || comments1.equals(" ") ) {
                                        Toast.makeText(VoiceBackground.this, "Please write the comments", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        if (keys_hai.equals(keys_recording)) {
                                            // Toast.makeText(VoiceBackground.this,keys_recording,Toast.LENGTH_SHORT).show();
                                            databaseReference2.child(passed_key).child("Recording").child(keys_recording).child("Comments").child(key2).child("comments").setValue(comments1);
                                            databaseReference2.child(passed_key).child("Recording").child(keys_recording).child("Comments").child(key2).child("username").setValue(username_login);
                                            databaseReference2.child(passed_key).child("Recording").child(keys_recording).child("Comments").child(key2).child("key_comments").setValue(key2);

                                            comments_section.setText(" ");

                                            voiceRecordingObjects = new VoiceRecordingObjects(comments1, username_login,key2);
                                            voicerecordingadapter1.add(voiceRecordingObjects);
                                            break;
                                        }
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

    public void pass_the_key(String keys_new) {
        passed_key = keys_new;
        display_the_comments();

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
        Log.e("permission for the ", "request granted");
        ActivityCompat.requestPermissions(VoiceBackground.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    public void clicking_like() {
        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((flag == 0 || flag == 1) && username_found == 0) {


                    if (flag == 0) {
                        // likes.setColorFilter(Color.BLUE);
                        flag = 1;
                        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                key_generation = databaseReference2.push().getKey();
                                Toast.makeText(VoiceBackground.this, keys_recording, Toast.LENGTH_SHORT).show();

                                databaseReference2.child(passed_key).child("Recording").child(keys_recording).child("Likes").child(key_generation).child("userlike").setValue(username_login);
                                String setting = "Liked";
                                //liked.setText(setting);
                                //liked.setTextColor(Color.BLUE);
                                likes.setImageResource(R.drawable.heart_full);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else {
                        likes.setImageResource(R.drawable.heart_empty);
                        flag = 0;
                        String setting = "Like";
                        // liked.setText(setting);
                        // liked.setTextColor(Color.BLACK);
                        databaseReference2.child(passed_key).child("Recording").child(keys_recording).child("Likes").child(key_generation).child("userlike").removeValue();


                    }
                }
            }
        });

    }



    public void display_the_comments() {

        databaseReference4.child(passed_key).child("Recording").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot dsp : dataSnapshot.getChildren()) {
                    final String keys = dsp.getKey();

                    //  Toast.makeText(VoiceBackground.this,keys,Toast.LENGTH_SHORT).show();
                    if (keys.equals(keys_recording)) {
                        databaseReference4.child(passed_key).child("Recording").child(keys_recording).child("Comments").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot1) {
                                for (DataSnapshot dsp : dataSnapshot1.getChildren()) {
                                    String key_pure_new = dsp.getKey();

                                    String comments = (String) dataSnapshot1.child(key_pure_new).child("comments").getValue();
                                    String username = (String) dataSnapshot1.child(key_pure_new).child("username").getValue();
                                   String key_get = (String) dataSnapshot1.child(key_pure_new).child("key_comments").getValue();

                                    voiceRecordingObjects = new VoiceRecordingObjects(comments, username,key_get);
                                    voicerecordingadapter1.add(voiceRecordingObjects);

                                }
                                writing_comments_activity();

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

    public  void writing_comments_activity()
    {
        alert_listview_voicerecording.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final TextView key_id = (TextView) view.findViewById(R.id.key_value);
                final String key_obtain =  key_id.getText().toString();


                Intent intent = new Intent(VoiceBackground.this,CommentsActivity.class);
                intent.putExtra("position",Integer.toString(i));
                intent.putExtra("passed_key",passed_key);
                intent.putExtra("keys_recording",keys_recording);
                intent.putExtra("key_obtain",key_obtain);
                intent.putExtra("username_login",username_login);

                //intent.putExtra("")
                startActivity(intent);
            }
        });
            }

    public  void  updated_comments1(final String key_updated1) {
        databaseReference4.child(passed_key).child("Recording").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot dsp : dataSnapshot.getChildren()) {
                    final String keys = dsp.getKey();


                    if (keys.equals(keys_recording)) {

                        databaseReference4.child(passed_key).child("Recording").child(keys).child("Comments").child(key_updated1).child("comments_done").
                                addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot1) {
                                for (DataSnapshot dsp : dataSnapshot1.getChildren()) {
                                    String key_pure_new = dsp.getKey();

                                    Toast.makeText(VoiceBackground.this,key_pure_new ,Toast.LENGTH_SHORT).show();

                                    String comments = (String) dataSnapshot1.child(key_pure_new).child("comments").getValue();
                                    String username = (String)  dataSnapshot1.child(key_pure_new).child("username").getValue();


                                    Toast.makeText(VoiceBackground.this,comments ,Toast.LENGTH_SHORT).show();
                                    Toast.makeText(VoiceBackground.this,username ,Toast.LENGTH_SHORT).show();

                                    voiceRecordingObjects = new VoiceRecordingObjects(comments, username);
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