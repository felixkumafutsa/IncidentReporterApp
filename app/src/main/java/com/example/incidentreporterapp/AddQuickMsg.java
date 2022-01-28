package com.example.incidentreporterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddQuickMsg extends AppCompatActivity {
    EditText fullMsg;
    Button addBtn,gotoDashboard;
    boolean valid = true;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quick_msg);

        firebaseFirestore = FirebaseFirestore.getInstance();

        fullMsg = findViewById(R.id.fullMsg);
        addBtn= findViewById(R.id.addBtn);
         gotoDashboard= findViewById(R.id.gotoDashboard);

          addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(fullMsg);

                if(valid){
                    saveQuickMessage();
                }
            }
        });
        gotoDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Admin.class));
            }
        });
    }

    public void saveQuickMessage(){
        String content = fullMsg.getText().toString().trim();
        if(content.isEmpty()){
            Toast.makeText(this, "Can not accept empty input", Toast.LENGTH_SHORT).show();
        }
        CollectionReference quickMsgRef = FirebaseFirestore.getInstance().collection("quickmessages");
        quickMsgRef.add(new QuickMessageModel(content));
        Toast.makeText(this, "Added seccessfully", Toast.LENGTH_SHORT).show();
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