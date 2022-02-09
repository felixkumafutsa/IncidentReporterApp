package com.example.incidentreporterapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.incidentreporterapp.databinding.ActivityIncidentsMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ReportIncident extends AppCompatActivity {
    ImageView imgCaptured;
    Button captureButton;
    private Spinner department;
    private EditText message;
    private Button sendReport;
    boolean valid = true;
    FirebaseFirestore firebaseFirestore;
    GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng latLng;
    private  final  long MIN_TIME = 1000;
    private final long MAX_DIST = 5;
    private ActivityIncidentsMapBinding binding;
    private StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);

        firebaseFirestore = FirebaseFirestore.getInstance();
        // Create a Cloud Storage reference from the app
        storageRef = FirebaseStorage.getInstance().getReference();

        imgCaptured = findViewById(R.id.imgCaptured);
        sendReport = findViewById(R.id.sendReport);
        department = findViewById(R.id.departments_spinner);
        message = findViewById(R.id.incidentDescription);
        sendReport = findViewById(R.id.sendReport);

        imgCaptured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        sendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                        sendSms();
                        checkField(message);

                        if(valid){
                            saveQuickMessage();
                        }
                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }
            }
        });
    }
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("My location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }

            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MAX_DIST, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MAX_DIST, locationListener);
        }
        catch (SecurityException exception){
            exception.printStackTrace();
        }
    }


       private void sendSms(){
        String phoneNumber = department.getSelectedItem().toString().trim();
        String SMS = message.getText().toString().trim() + " http://maps.google.com/?q=-11.41988454439096,33.99534525947592";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null,SMS,null,null);
            Toast.makeText(this,"Message Sent",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Failed to send text",Toast.LENGTH_SHORT).show();
        }
    }
    public void saveQuickMessage(){
        String content = message.getText().toString().trim();
        String latitude = "-11.41988454439096";
        String longitude = "33.99534525947592";
        if(content.isEmpty()){
            Toast.makeText(this, "Can not accept empty input", Toast.LENGTH_SHORT).show();
        }
        CollectionReference quickMsgRef = FirebaseFirestore.getInstance().collection("incidents");
        Map<String, Object> incidentInfo = new HashMap<>();
        incidentInfo.put("incidentDescription", content);
        incidentInfo.put("incidentLatitude", latitude);
        incidentInfo.put("incidentLongitude", longitude);
        quickMsgRef.add(incidentInfo);

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

    public void uploadImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,101);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode == RESULT_OK){
           if (requestCode == 101){
                onCaptureImageResult(data);
           }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onCaptureImageResult(Intent data) {
        Bitmap b = (Bitmap)data.getExtras().get("data");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte bb[] = byteArrayOutputStream.toByteArray();
        imgCaptured.setImageBitmap(b);

        uploadToFirebase(bb);
    }

    private void uploadToFirebase(byte []bb) {
           StorageReference sr = storageRef.child("images/a.jpg");
           sr.putBytes(bb).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   Toast.makeText(ReportIncident.this, "successfully uploaded image to database", Toast.LENGTH_SHORT).show();
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Toast.makeText(ReportIncident.this, "failed", Toast.LENGTH_SHORT).show();
               }
           });
    }

}