package sangamsagar.signingin.Recordings;

/**
 * Created by user 1 on 4/4/2018.
 */

public class VoiceRecordingObjects {

    String username,comments,key_value;


    public VoiceRecordingObjects(String comments,String username,String key_value)
    {
        this.username = username;
        this.comments = comments;
        this.key_value= key_value;

    }

    public VoiceRecordingObjects(String comments,String username)
    {
        this.username = username;
        this.comments = comments;


    }

    public String getUsername() {
        return username;
    }

    public String getComments() {
        return comments;
    }

    public String getKey_value() {
        return key_value;
    }
}
