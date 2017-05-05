package com.example.sowmya.musictranscription;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    void onRecord( boolean start) {

        if (start) {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            Date now = new Date();
            String strDate = sdfDate.format(now);
            recordedFilePath = directoryPath + strDate + ".3gp";

            startRecording();
        } else {
            stopRecording();
        }
    }

    void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
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

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        try {
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(recordedFilePath);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "record prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.reset();
        mRecorder.release();
        mRecorder = null;
    }



}
