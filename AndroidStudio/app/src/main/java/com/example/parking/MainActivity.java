package com.example.parking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView slot1StatusTextView = findViewById(R.id.slot1_status);
        TextView slot2StatusTextView = findViewById(R.id.slot2_status);
        TextView slot3StatusTextView = findViewById(R.id.slot3_status);

        // Initialize Firebase
        databaseRef = FirebaseDatabase.getInstance().getReference("carparking-76c2e-default-rtdb");

        // Read the status of slot 1
        readParkingSlotStatus("slot1_status", slot1StatusTextView);
        // Read the status of slot 2
        readParkingSlotStatus("slot2_status", slot2StatusTextView);
        // Read the status of slot 3
        readParkingSlotStatus("slot3_status", slot3StatusTextView);
    }

    private void readParkingSlotStatus(String slotName, final TextView statusTextView) {
        DatabaseReference slotStatusRef = databaseRef.child(slotName);
        slotStatusRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer slotStatus = dataSnapshot.getValue(Integer.class);
                if (slotStatus != null) {
                    if (slotStatus == 1) {
                        statusTextView.setText(slotName+": Occupied");
                    } else if (slotStatus == 0) {
                        statusTextView.setText(slotName+": Free");
                    } else {
                        statusTextView.setText(slotName+": Unknown");
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                statusTextView.setText("Status: Error");
            }
        });
    }
}
