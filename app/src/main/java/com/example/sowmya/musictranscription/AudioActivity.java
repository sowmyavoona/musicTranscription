package com.example.sowmya.musictranscription;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by sowmya on 2/5/17.
 */
public class AudioActivity extends AppCompatActivity {

    private static final int SELECT_AUDIO = 2;
    String selectedPath = "";
    ProgressDialog prgDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_audio);

        openGalleryAudio();

        prgDialog = new ProgressDialog(this);
        prgDialog.setCancelable(false);

    }

    public void openGalleryAudio(){

        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Audio "), SELECT_AUDIO);
    }

    /*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_AUDIO)
            {
                System.out.println("SELECT_AUDIO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                System.out.println("SELECT_AUDIO Path : " + selectedPath);
                doFileUpload();

                prgDialog.setMessage("Calling Upload");
                prgDialog.show();
            }

        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void doFileUpload(){

        try{

            String urlString = "http://twiliosms.ashrafnaim.com/imgUpload/audio.php";

            File file=new File(selectedPath);

            RequestParams params = new RequestParams();
            params.put("uploadedfile",file);
            AsyncHttpClient client = new AsyncHttpClient();
            client.post(urlString, params,new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, PreferenceActivity.Header[] headers, byte[] responseBody) {

                    prgDialog.cancel();
                    String s=new String(responseBody);
                    Toast.makeText(AudioActivity.this,s,Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(int statusCode, PreferenceActivity.Header[] headers, byte[] responseBody, Throwable error) {

                    prgDialog.cancel();

                }
            });

        }
        catch (Exception e)
        {
            prgDialog.cancel();
        }
    }*/
}
