package com.deadhunter.opendaycompanionapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;
import java.util.Map;

public class QrActivity extends AppCompatActivity {
    private Button btnScan;

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    private DocumentSnapshot scannedDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        btnScan = findViewById(R.id.btnScan);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Button Click", "Button clicked"); // Add this line to check if the button click listener is registered
                Scanner();
            }
        });
    }

    private void Scanner() {
        Log.d("Scanner", "Scanner method called"); // Add this line to check if the method is being called

        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to Flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(StartScan.class);
        launcher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> launcher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String scannedContent = result.getContents();
            Timestamp scannedTime = Timestamp.now(); // Get the current timestamp

            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser != null) {
                String userLoginEmail = currentUser.getEmail();

                // Assuming 'attendusers' is the collection name
                CollectionReference usersCollection = firestore.collection("attendusers");

                // Query the collection to find documents with matching user email
                Query query = usersCollection.whereEqualTo("email", userLoginEmail);

                query.get()
                        .addOnSuccessListener(querySnapshot -> {
                            // Check if any matching documents exist
                            if (!querySnapshot.isEmpty()) {
                                boolean hasMatchingQR = false;

                                // Iterate through the matching documents
                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                    // Check if the document has a matching QR code
                                    if (document.getString("faculty").equals(scannedContent)) {
                                        hasMatchingQR = true;

                                        // Get the existing data from the document
                                        scannedDocument = document;

                                        // Check if leaving time is already recorded
                                        if (document.get("leaving_time") != null) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(QrActivity.this);
                                            builder.setTitle("QR-SCANNER RESULT");
                                            builder.setMessage("Leaving time already recorded for QR: " + scannedContent);
                                            builder.setPositiveButton("Okay", (dialog, i) -> dialog.dismiss()).show();
                                        } else {
                                            // Add the leaving time to the existing data
                                            updateDocumentWithLeavingTime(scannedTime);
                                        }

                                        break;
                                    }
                                }

                                if (!hasMatchingQR) {
                                    // No document with matching QR found, create a new document
                                    createNewDocument(userLoginEmail, scannedContent, scannedTime);
                                }
                            } else {
                                // No matching documents found, create a new document
                                createNewDocument(userLoginEmail, scannedContent, scannedTime);
                            }
                        })
                        .addOnFailureListener(e -> {
                            // Error occurred while querying the collection in Firestore
                            // Handle the error as per your requirement
                        });

            }
        }
    });

    private void updateDocumentWithLeavingTime(Timestamp scannedTime) {
        // Add the leaving time to the existing data
        scannedDocument.getReference().update("leaving_time", scannedTime)
                .addOnSuccessListener(aVoid -> {
                    // Document successfully updated in Firestore
                    AlertDialog.Builder builder = new AlertDialog.Builder(QrActivity.this);
                    builder.setTitle("QR-SCANNER RESULT");
                    builder.setMessage(scannedDocument.getString("faculty") + "\n\nLeaving Time: " + scannedTime.toString());
                    builder.setPositiveButton("Okay", (dialog, i) -> dialog.dismiss()).show();
                })
                .addOnFailureListener(e -> {
                    // Error occurred while updating the document in Firestore
                    // Handle the error as per your requirement
                });
    }

    private void createNewDocument(String userLoginEmail, String scannedContent, Timestamp scannedTime) {
        // Assuming 'attendusers' is the collection name
        CollectionReference usersCollection = firestore.collection("attendusers");

        // Create a new document with the required data
        Map<String, Object> newData = new HashMap<>();
        newData.put("email", userLoginEmail); // Save the user's login email in the "email" field
        newData.put("entering_time", scannedTime);
        newData.put("faculty", scannedContent);
        newData.put("leaving_time", null); // Initialize leaving_time as null

        usersCollection.add(newData)
                .addOnSuccessListener(documentReference -> {
                    // Document successfully added to Firestore
                    AlertDialog.Builder builder = new AlertDialog.Builder(QrActivity.this);
                    builder.setTitle("QR-SCANNER RESULT");
                    builder.setMessage(scannedContent + "\n\nEntering Time: " + scannedTime.toString());
                    builder.setPositiveButton("Okay", (dialog, i) -> dialog.dismiss()).show();
                })
                .addOnFailureListener(e -> {
                    // Error occurred while adding the document to Firestore
                    // Handle the error as per your requirement
                });
    }
}