package com.example.incidentreporterapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.incidentreporterapp.databinding.ActivityIncidentsMapBinding;


public class IncidentsMap extends FragmentActivity implements OnMapReadyCallback {
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

        binding = ActivityIncidentsMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},PackageManager.PERMISSION_GRANTED);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
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

                    String phoneNumber = "0997473256";
                    String myLat = String.valueOf(location.getLatitude());
                    String myLon  = String.valueOf(location.getLongitude());
                    String SMS = "Latitude = " + myLat + "Longitude = " + myLon;

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber,null,SMS,null,null);

                    }
                    catch (Exception e){
                        e.printStackTrace();

                    }
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
}