package com.deadhunter.opendaycompanionapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.deadhunter.opendaycompanionapp.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());

        // Catching the data from user-input
        final EditText emailText = findViewById(R.id.login_email);
        final Button logBtn = findViewById(R.id.login_btn);

        // Changing text color red to invalid email and disable/enable the login button
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches()){
                    logBtn.setEnabled(true);
                }
                else{
                    logBtn.setEnabled(false);
                    emailText.setError("Invalid EmailAddress");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.loginBtn.setOnClickListener(view -> {
            // Retrieve the user inputted data
            String email = binding.loginEmail.getText().toString();
            String logPwd = binding.loginPwd.getText().toString();

            // Checking if the fields are empty
            if (email.equals("") || logPwd.equals("")) {
                Toast.makeText(LoginActivity.this, "All fields are mandatory",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Sign-in the user using firebase authentication
                mAuth.signInWithEmailAndPassword(email, logPwd)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                if (Objects.requireNonNull(mAuth.getCurrentUser()).isEmailVerified()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "Login Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Please verify your email.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        });

            }
        });


        // If user clicks register open the RegisterActivity
        binding.regRedirectText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        binding.resetPwdText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}