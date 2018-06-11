package sangamsagar.signingin.Recordings;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import sangamsagar.signingin.R;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecordingActivity extends AppCompatActivity {

    private StorageReference mstoragereference;

    ImageButton buttonStart;
    Button buttonStop;
    Button buttonPlayLastRecordAudio;
    Button buttonStopPlayingRecording,save ;
    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;
    Random random ;
    Handler handler;
    TextView stopwatch,recording_name;
    String key;
 private ProgressDialog progressDialog;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference1,databaseReference,databaseReference2;
    Intent intent;

    int i=0;
    int flag=0;
    Button locate;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;
    Uri downloaduri;
    String uri1;

    ListView listView_recording;
    List<RecordingActivityObject> list;
    RecordingActivityAdapter recordingActivityAdapter;
    String email1;
    AlertDialog.Builder alert;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int Seconds, Minutes, MilliSeconds ;
    String YouEditTextValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        mstoragereference = FirebaseStorage.getInstance().getReference();

        handler =new Handler();
    progressDialog = new ProgressDialog(this);
        buttonStart = (ImageButton) findViewById(R.id.button);
       // buttonStop = (Button) findViewById(R.id.button2);
        buttonPlayLastRecordAudio = (Button) findViewById(R.id.button3);
        buttonStopPlayingRecording = (Button)findViewById(R.id.button4);

        stopwatch =(TextView) findViewById(R.id.stopwatch);
        final AlertDialog.Builder alert = new AlertDialog.Builder(RecordingActivity.this);
        Bundle bundle = getIntent().getExtras();



        email1 =bundle.getString("emailid");

        Toast.makeText(RecordingActivity.this, email1,
                Toast.LENGTH_LONG).show();
        save = (Button)findViewById(R.id.save);

        locate = (Button)findViewById(R.id.locate);
       // recording_name = (TextView) findViewById(R.id.recording_name);

        //go=(Button) findViewById(R.id.gothere);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1= firebaseDatabase.getReference().child("Songs");
        databaseReference= firebaseDatabase.getReference().child("newuser");
        databaseReference2= firebaseDatabase.getReference().child("newuser");

       // buttonStop.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(false);
        buttonStopPlayingRecording.setEnabled(false);

        random = new Random();
        checkPermission();
        requestPermission();
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                if(checkPermission()) {

                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                    CreateRandomAudioFileName(5) + "AudioRecording.3gp";

                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {

                        e.printStackTrace();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                    buttonStart.setEnabled(false);
                    //buttonStop.setEnabled(true);


                    Toast.makeText(RecordingActivity.this, "Recording started",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }

            }
        });




        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(flag==0)

                {
                    buttonPlayLastRecordAudio.setEnabled(true);
                    buttonStart.setEnabled(true);
                    buttonStopPlayingRecording.setEnabled(false);
                    mediaRecorder.stop();

                    TimeBuff += MillisecondTime;

                    handler.removeCallbacks(runnable);
                    stopwatch.setText("00:00:00");
                    final EditText edittext = new EditText(RecordingActivity.this);
                    alert.setMessage("Enter Your Voice Name");
                    alert.setTitle(" Your Recording");
                    alert.setView(edittext);

                    flag = 0;
                    alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            YouEditTextValue = edittext.getText().toString();

                            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {


                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    for(DataSnapshot dsp : dataSnapshot.getChildren())
                                    {
                                        key = dsp.getKey();
                                        String email2 =(String) dataSnapshot.child(key).child("email").getValue();

                                        if(email2.equals(email1))
                                        {
                                            final String key2 = databaseReference.push().getKey();

                                            StorageMetadata metadata = new StorageMetadata.Builder()
                                                    .setContentType("audio/mpeg")
                                                    .build();
                                            String s = AudioSavePathInDevice;
                                            int length = s.length();
                                            int length_new = length - 23;
                                            //Toast.makeText(AllRecording.this, Integer.toString(length_new), Toast.LENGTH_SHORT).show();
                                            String kim = s.substring(length_new, length_new+5);
                                            progressDialog.setMessage("Uploading started");
                                            StorageReference storageReference = mstoragereference.child("Media").child("Audio.3gp"+kim);
                                            Uri uri = Uri.fromFile(new File(AudioSavePathInDevice));


                                            storageReference.putFile(uri,metadata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    progressDialog.dismiss();

                                                    downloaduri = taskSnapshot.getDownloadUrl();
                                                    uri1 = downloaduri.toString();

                                                    databaseReference.child(key).child("Recording").child(key2).child("voice").setValue(uri1);
                                                    progressDialog.setMessage("uploading finished");
                                                    //Toast.makeText(RecordingActivity.this,"RecordingFinished",Toast.LENGTH_SHORT).show();
                                                }
                                            });


                                            databaseReference.child(key).child("Recording").child(key2).child("stopwatch").setValue(Long.toString(TimeBuff));
                                            databaseReference.child(key).child("Recording").child(key2).child("key").setValue(key2);
                                            databaseReference.child(key).child("Recording").child(key2).child("recordingname").setValue(YouEditTextValue);
                                            databaseReference.child(key).child("Recording").child(key2).child("keyparent").setValue(key);

                                            MillisecondTime = 0L ;
                                            StartTime = 0L ;
                                            TimeBuff = 0L ;
                                            UpdateTime = 0L ;
                                            Seconds = 0 ;
                                            Minutes = 0 ;
                                            MilliSeconds = 0 ;

                                            Toast.makeText(RecordingActivity.this, "Your voice has been saved",
                                                    Toast.LENGTH_LONG).show();
                                            uploadOnFirebase(AudioSavePathInDevice);

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

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                        }
                    });

                    alert.show();
                    //buttonStop.setEnabled(false);
                }




            }
        });

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 7);

            }
        });
        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);

                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }
            }
        });

    }
    public  void uploadOnFirebase(String audioSavePathInDevice)
    {

    }
    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        mediaRecorder.setOutputFile(AudioSavePathInDevice);

        Log.e("path",AudioSavePathInDevice.toString());
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        Log.e("permission for the ","request granted");
        ActivityCompat.requestPermissions(RecordingActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(RecordingActivity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RecordingActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }

    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
    String PathHolder;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch(requestCode){

            case 7:

                if(resultCode==RESULT_OK){

                     PathHolder = data.getData().getPath();

                    StorageReference storageReference = mstoragereference.child("Audio").child("Audio.3gp");
                    Uri uri = Uri.fromFile(new File(PathHolder));
                    //String uri_media =uri.toString();


                   // Toast.makeText(RecordingActivity.this,"In the RecordingActivity" + uri_media,Toast.LENGTH_SHORT).show();
                    storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(RecordingActivity.this,"RecordingFinished",Toast.LENGTH_SHORT).show();
                        }
                    });

                    Toast.makeText(RecordingActivity.this, PathHolder , Toast.LENGTH_LONG).show();

                }
                break;

        }
    }
    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            stopwatch.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };




}