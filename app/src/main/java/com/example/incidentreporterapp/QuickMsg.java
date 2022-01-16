package com.example.incidentreporterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class QuickMsg extends AppCompatActivity {
    private Spinner department;
    private Spinner message;
    private Spinner phone;
    private Button sendPanicSituation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_msg);

        message = findViewById(R.id.messageSpiner);
        department = findViewById(R.id.departmentSpinner);
        phone = findViewById(R.id.phoneSpinner);
        sendPanicSituation = findViewById(R.id.sendPanic);


        sendPanicSituation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                        sendSms();
                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                }
            }
        });

        Spinner spinnerMessage = (Spinner) findViewById(R.id.messageSpiner);
        ArrayAdapter<CharSequence> adapterMessage = ArrayAdapter.createFromResource(this,
                R.array.messages_array, android.R.layout.simple_spinner_item);
        adapterMessage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMessage.setAdapter(adapterMessage);


        Spinner spinnerDepartment = (Spinner) findViewById(R.id.departmentSpinner);
        ArrayAdapter<CharSequence> adapterDepartment = ArrayAdapter.createFromResource(this,
                R.array.departments_array, android.R.layout.simple_spinner_item);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapterDepartment);



        Spinner spinnerPhone = (Spinner) findViewById(R.id.phoneSpinner);
        ArrayAdapter<CharSequence> adapterPhone = ArrayAdapter.createFromResource(this,
                R.array.phoneNumber_array, android.R.layout.simple_spinner_item);
        adapterDepartment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapterPhone);
    }

    private void sendSms(){
        String phoneNumber = department.getSelectedItem().toString().trim();
        String SMS = message.getSelectedItem().toString().trim();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null,SMS,null,null);
            Toast.makeText(this,"Message Sent",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Failed to send text",Toast.LENGTH_SHORT).show();
        }
    }

}