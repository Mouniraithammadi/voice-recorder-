package com.project.voicerecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    MediaRecorder Rcording_voice ;
    View view;
    MediaPlayer Playing_voice ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (CheckMic()) getMicPermission();
    }
    //this function for recording the voice and waite for the stop button to save the record
    public void RecordingVoice(View i ){
        try {
            Rcording_voice = new MediaRecorder();
            Rcording_voice.setAudioSource(MediaRecorder.AudioSource.MIC);
            Rcording_voice.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            Rcording_voice.setOutputFile(getPathOfRecordFile());
            Rcording_voice.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            Rcording_voice.prepare(); Rcording_voice.start();
            Toast.makeText(this, "Recording ", Toast.LENGTH_SHORT).show();
        } catch (Exception exp){
            exp.printStackTrace();
        }
    }
    //this function is for geting the path of the record file to use it
    private String getPathOfRecordFile(){
        ContextWrapper cntxWrpr = new ContextWrapper(getApplicationContext());
        File musicDrct = cntxWrpr.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDrct , "recordFile" + ".mp3");
        return file.getPath() ;
    }
// this function is will stoping the recording
    public void StopRecording(View i ){
        Rcording_voice.stop(); Rcording_voice.release(); Rcording_voice = null;
        Toast.makeText(this, " stop Recording ",
                Toast.LENGTH_SHORT)
                                    .show();

    }
    // this function for playing the last voice recorded
    public void PlayRecord(View i ){
        StopRecording(view);
        try {
            Playing_voice = new MediaPlayer();
            Playing_voice.setDataSource(getPathOfRecordFile()); Playing_voice.prepare(); Playing_voice.start();
            Toast.makeText(this, " play Record ",
                    Toast.LENGTH_SHORT)
                                        .show();

        } catch (Exception exp){
            exp.printStackTrace();
        }
    }
    //this function is for checking the microphone
    private boolean CheckMic(){
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) return true ;
         else return false ;
    }
    // this function is will get the permission for the microphone
    private  void getMicPermission(){
        if (ContextCompat.checkSelfPermission(this , Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) ActivityCompat.
                requestPermissions(this , new String[]
                        {Manifest.permission.RECORD_AUDIO} , 200);

    }
}