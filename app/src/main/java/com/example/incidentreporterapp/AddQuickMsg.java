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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddQuickMsg extends AppCompatActivity {
    EditText fullMsg;
    Button addBtn,gotoDashboard;
    boolean valid = true;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quick_msg);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        fullMsg = findViewById(R.id.fullMsg);
        addBtn= findViewById(R.id.addBtn);
         gotoDashboard= findViewById(R.id.gotoDashboard);

          addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(fullMsg);

                if(valid){
                            DocumentReference df = firebaseFirestore.collection("Users").document(fullMsg.toString());
                            Toast.makeText(AddQuickMsg.this, "created", Toast.LENGTH_SHORT).show();
                            Map<String, Object> msgInfo = new HashMap<>();
                            msgInfo.put("FullName", fullMsg.getText().toString());
                            df.set(msgInfo);
                            startActivity(new Intent(getApplicationContext(),Admin.class));
                            finish();

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