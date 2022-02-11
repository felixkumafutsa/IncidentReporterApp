package com.example.incidentreporterapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.incidentreporterapp.databinding.ActivityIncidentsMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuickMsg extends AppCompatActivity {
    private Spinner department;
    private Spinner phone;
    private String message;
    private Button sendPanicSituation;
    boolean valid = true;
    private DatabaseReference databaseIncident;

    GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng latLng;
    private  final  long MIN_TIME = 1000;
    private final long MAX_DIST = 5;
    private ActivityIncidentsMapBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_msg);

        databaseIncident = FirebaseDatabase.getInstance().getReference("incidents");

        message = "Help me!, it's an emergency\n please reach ASAP to the location below\n   ";
        department = findViewById(R.id.departmentSpinner);
           sendPanicSituation = findViewById(R.id.sendPanic);

        Spinner spinnerDepartment = (Spinner) findViewById(R.id.departmentSpinner);
        ArrayAdapter<CharSequence> adapterDepartment = ArrayAdapter.createFromResource(this,
                R.array.phoneNumber_array, android.R.layout.simple_spinner_item);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapterDepartment);


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},PackageManager.PERMISSION_GRANTED);

        sendPanicSituation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                        sendSms();
                        checkField(message);

                        if(valid){
                            String msg = message.trim();
                            String id = databaseIncident.push().getKey();
                            Incident incident = new Incident(id,msg);
                            databaseIncident.child(id).setValue(incident);

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

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
         latLng = new LatLng(-34, 151);
        String myLat = String.valueOf(latLng.latitude);
        String myLon  = String.valueOf(latLng.longitude);
        String phoneNumber = department.getSelectedItem().toString().trim();
        String SMS = message.trim() + " http://maps.google.com/?q=-11.41988454439096,33.99534525947592";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null,SMS,null,null);
            Toast.makeText(this,"Message Sent",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Failed to send text",Toast.LENGTH_SHORT).show();
        }
    }
    public boolean checkField(String textField){
        if(textField.isEmpty()){

            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
}