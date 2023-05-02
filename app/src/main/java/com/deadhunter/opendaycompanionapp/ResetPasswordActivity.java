package com.deadhunter.opendaycompanionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deadhunter.opendaycompanionapp.databinding.ActivityResetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    ActivityResetPasswordBinding binding;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        final EditText emailReset = findViewById(R.id.reset_email);
        final Button btnReset = findViewById(R.id.reset_btn);
        // Changing text color red to invalid email and disable/enable the login button
        emailReset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Patterns.EMAIL_ADDRESS.matcher(emailReset.getText().toString()).matches()){
                    btnReset.setEnabled(true);
                }
                else{
                    btnReset.setEnabled(false);
                    emailReset.setError("Invalid EmailAddress");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.resetBtn.setOnClickListener(view -> {
            String rEmail =  binding.resetEmail.getText().toString();
            if (rEmail.equals("")) {
                Toast.makeText(this, "Email is mandatory", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.sendPasswordResetEmail(binding.resetEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(ResetPasswordActivity.this, "Please check your email!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ResetPasswordActivity.this, "Enter valid email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ResetPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
    }
}