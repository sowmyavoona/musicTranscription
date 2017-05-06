package com.example.sowmya.musictranscription;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;
import android.widget.ImageButton;

import java.io.IOException;

/**
 * Created by sowmya on 5/5/17.
 */
public class SoundRecorder {
    private static final String LOG_TAG = "AudioRecord";

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    private Context context;

    String recordedFilePath = "";
    String directoryPath = "";

    public  SoundRecorder(String directoryPath, Context context) {
        this.directoryPath = directoryPath;
        this.context = context;
    }

    void startPlaying(String recordedFilePath) {
        mPlayer = new MediaPlayer();

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                updatePlayButton();
            }
        });

        try {
            mPlayer.setDataSource(recordedFilePath);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.prepare();
            mPlayer.start();

        } catch (IOException e) {
            Log.e(LOG_TAG, "media player prepare() failed");
        }
    }

    void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    boolean startRecording(String recordedFilePath) {
        mRecorder = new MediaRecorder();
        try {
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(recordedFilePath);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.prepare();
            mRecorder.start();
            return true;

        } catch (IOException e) {
            Log.e(LOG_TAG, "record prepare() failed");
            return false;
        }
    }

    void stopRecording() {
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
    }

    public void updatePlayButton(){
        ImageButton playButton = (ImageButton) ((Activity) context).findViewById(R.id.playButton);
        playButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
    }

}
