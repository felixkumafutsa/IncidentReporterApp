package com.example.incidentreporterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import io.grpc.Context;

public class ReportIncident extends AppCompatActivity {
    ImageView imgCaptured;
    Button captureButton;
    private Spinner department;
    private EditText message;
    private Button sendReport;
    boolean valid = true;
    private DatabaseReference databaseIncident;
    String currentPhotoPath;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);

        databaseIncident = FirebaseDatabase.getInstance().getReference("incidents");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imgCaptured = findViewById(R.id.imgCaptured);
        captureButton = findViewById(R.id.captureButton);
        sendReport = findViewById(R.id.sendReport);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSms();
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.departments_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phoneNumber_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        department = findViewById(R.id.departments_spinner);
        message = findViewById(R.id.incidentDescription);
        sendReport = findViewById(R.id.sendReport);
        sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(message);

                if(valid){
                    String msg = message.getText().toString().trim();
                    String id = databaseIncident.push().getKey();
                    Incident incident = new Incident(id,msg);
                    databaseIncident.child(id).setValue(incident);

                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == Activity.RESULT_OK){
            File f = new File(currentPhotoPath);
            imgCaptured.setImageURI(Uri.fromFile(f));
            Log.d("TAG", "Uri is " + Uri.fromFile(f));
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);

            uploadImage(f.getName(), contentUri);
        }
    }

    private void uploadImage(String name, Uri contentUri) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading picture.....");
        pd.show();
        StorageReference image = storageReference.child("images/" + name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(@NonNull Uri uri) {
                        Log.d("tag","onsuccess: image uri is " + uri.toString());
                    }
                });
                pd.dismiss();
                Snackbar.make(findViewById(R.id.content), "Image uploaded", Snackbar.LENGTH_SHORT ).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Progress :" + (int) progressPercent+ "%");
            }
        });
    }

    private  File createImageFile() throws IOException{
        String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format( new Date());
        String imageFileName ="JPEG" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;

    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            }
            catch (Exception exception){

            }
            if(photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(this,"com.incidentreporterapp.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, RESULT_OK);
            }
        }

    }

    private void sendSms(){
        String phoneNumber = department.getSelectedItem().toString().trim();
        String SMS = message.getText().toString().trim();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null,SMS,null,null);
            Toast.makeText(this,"Message Sent",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Failed to send text",Toast.LENGTH_SHORT).show();
        }
    }
    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
}