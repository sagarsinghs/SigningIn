package sangamsagar.signingin.Recordings;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import sangamsagar.signingin.R;


/**
 * Created by user 1 on 2/18/2018.
 */

public class RecordingActivityAdapter extends ArrayAdapter<RecordingActivityObject>  {
private Typeface mCustomFont;


public RecordingActivityAdapter(@NonNull Context context, int resource, @NonNull List<RecordingActivityObject> objects){

        super(context,resource,objects);

        }


@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        if(convertView==null)
        convertView=((Activity)getContext()).getLayoutInflater().inflate(R.layout.textlistview,parent,false);


        TextView text,time,username,recording_database,key_recording,likes,comments_user;
        TextView comments,keys;

        text=(TextView)convertView.findViewById(R.id.recording_name);
        time=(TextView)convertView.findViewById(R.id.duration);
        username=(TextView)convertView.findViewById(R.id.username);
        recording_database=(TextView)convertView.findViewById(R.id.new_song);
        keys =(TextView)convertView.findViewById(R.id.keys);
        key_recording =(TextView)convertView.findViewById(R.id.key_recording);
        likes =(TextView)convertView.findViewById(R.id.likes_count);
        comments_user =(TextView)convertView.findViewById(R.id.comments_count);
          //likes =(ImageButton) convertView.findViewById(R.id.likes);
        //comments =(TextView) convertView.findViewById(R.id.comments);




        RecordingActivityObject recordingActivityObject=getItem(position);

        text.setText(recordingActivityObject.getRecording_name());
        keys.setText(recordingActivityObject.getKey());
        likes.setText(recordingActivityObject.getLikes_user());
        comments_user.setText(recordingActivityObject.getComments_user());
        String s =recordingActivityObject.getTime();
        key_recording.setText(recordingActivityObject.getKey_recording());
        //comments.setText(recordingActivityObject.getComments());
        int x =Integer.parseInt(s);
        int last =x/10000;
        int front = x%10000;
        time.setText(Integer.toString(last)+":"+Integer.toString(front/100));
        username.setText(recordingActivityObject.getUsername());
        recording_database.setText(recordingActivityObject.getNewsong());
        return convertView;

        }
        }
