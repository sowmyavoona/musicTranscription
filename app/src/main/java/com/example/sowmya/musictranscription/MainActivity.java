package com.example.sowmya.musictranscription;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity{
    private Button uploadButton;
    private static final int PICK_FILE_REQUEST = 1;
    private String audioPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadButton = (Button) findViewById(R.id.uploadButton);

        uploadButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            showFileChooser();
            }
        });

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to audio types of files
        intent.setType("audio/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){

        if(requestCode == 1){

            if(resultCode == RESULT_OK){

                //the selected audio.
                Uri uri = data.getData();
                if(getMimeType(uri).equals("wav")){
                    Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_LONG).show();
                    
                }else{
                    Toast.makeText(getApplicationContext(),uri.getPath(),Toast.LENGTH_LONG).show();
                }
            }else{

            }
        }else{

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getMimeType(Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(getApplicationContext().getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }
}
