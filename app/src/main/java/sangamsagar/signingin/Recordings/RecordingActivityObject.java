package sangamsagar.signingin.Recordings;

/**
 * Created by user 1 on 2/18/2018.
 */

public class RecordingActivityObject {
    String newsong,time,username,recording_name,likes_user,comments_user,key,key_recording;
    String comments ="you have comments";

    public RecordingActivityObject(String newsong,String time,String recording_name)
    {
        this.newsong = newsong;
        this.time = time;
        this.recording_name = recording_name;
    }

   /* public RecordingActivityObject(String newsong,String time,String username,String recording_name,String likes,String comments)
    {
        this.newsong = newsong;
        this.time = time;
        this.username = username;
        this.recording_name = recording_name;
        this.likes =likes;

    }*/
    public RecordingActivityObject(String newsong,String time,String username,String recording_name,String key,String key_recording,String comments,String likes)
    {
        this.newsong = newsong;
        this.time = time;
        this.username = username;
        this.recording_name = recording_name;
        this.key = key;
        this.key_recording = key_recording;
        likes_user =likes;
        comments_user = comments;


    }

    public  String getNewsong()
    {
        return newsong;
    }
    public  String getKey()
    {
        return key;
    }
    public  String getTime()
    {
        return time;
    }
    public  String getUsername()
    {
        return username;
    }
    public  String getRecording_name()
    {
        return recording_name;
    }
    public  String getComments()
    {
        return comments;
    }
    public  String getKey_recording()
    {
        return key_recording;
    }
    public  String getLikes_user()
    {
        return likes_user;
    }
    public  String getComments_user()
    {
        return comments_user;
    }
}
