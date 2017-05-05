package com.example.sowmya.musictranscription;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity{
    private Button uploadButton, transcribeButton;
    private TextView pathField;
    private static final int PICK_FILE_REQUEST = 1;
    private String audioPath;
    private  ServerHandler serverHandler;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serverHandler = new ServerHandler();
        uploadButton = (Button) findViewById(R.id.uploadButton);
        transcribeButton = (Button) findViewById(R.id.transcribeButton);
        pathField =  (TextView) findViewById(R.id.pathField);

        uploadButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            showFileChooser();
            if(saveToLocal(audioPath)){

            } else {}
            }
        });
        transcribeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                transcribeMusic();
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
                if(getMimeType(getApplicationContext(), uri).equals("wav")){
                    Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_LONG).show();
                    audioPath = getRealPathFromURI(getApplicationContext(),  uri);
                }else{
                    Toast.makeText(getApplicationContext(),uri.getPath(),Toast.LENGTH_LONG).show();
                }
            }else{

            }
        }else{

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean saveToLocal(String path){
        return true;
    }
    private void transcribeMusic(){
        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Toast.makeText(getApplicationContext(),audioPath,Toast.LENGTH_LONG).show();
        pathField.setText(audioPath);

        //transcribeButton.setEnabled(false);
        // connect to php server to send recorded file and computeFeatures

        progress = ProgressDialog.show(MainActivity.this, "Connect to server", "uploading file", true);


        if(serverHandler.uploadFile(audioPath)) {
            serverHandler.transcribeFile();
            progress.dismiss();
            //get notes

        }
        else{
            progress.dismiss();
            Toast.makeText(getApplicationContext(),"couldn't upload wav file",Toast.LENGTH_SHORT).show();

        }

    }

    public String getMimeType(Context context, Uri uri) {
        String extension;

        //Check uri format to avoid null
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            //If scheme is a content
            final MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());

        }

        return extension;
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


}
