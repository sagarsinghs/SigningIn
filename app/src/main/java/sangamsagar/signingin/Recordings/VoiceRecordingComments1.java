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
 * Created by user 1 on 4/4/2018.
 */

public class VoiceRecordingComments1 extends ArrayAdapter<VoiceRecordingObjects> {
    private Typeface mCustomFont;


    public VoiceRecordingComments1(@NonNull Context context, int resource, @NonNull List<VoiceRecordingObjects> objects) {

        super(context, resource, objects);

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.activity_comments_layout, parent, false);


        TextView username, comments,key_value;


        comments = (TextView) convertView.findViewById(R.id.comments_section);
        username = (TextView) convertView.findViewById(R.id.username);
        //key_value =(TextView) convertView.findViewById(R.id.key_value) ;

        VoiceRecordingObjects voiceRecordingObjects = getItem(position);

        comments.setText(voiceRecordingObjects.getComments());
        username.setText(voiceRecordingObjects.getUsername());
       // key_value.setText(voiceRecordingObjects.getKey_value());
        return convertView;
    }
}