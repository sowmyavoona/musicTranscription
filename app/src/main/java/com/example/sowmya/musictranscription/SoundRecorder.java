package com.example.sowmya.musictranscription;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by sowmya on 5/5/17.
 */
public class SoundRecorder {
    private static final String LOG_TAG = "AudioRecord";

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    String recordedFilePath = "";
    String directoryPath = "";

    public  SoundRecorder(String directoryPath){
        this.directoryPath = directoryPath;
    }

    void startPlaying(String recordedFilePath) {
        mPlayer = new MediaPlayer();
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



}
