package com.deadhunter.opendaycompanionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.deadhunter.opendaycompanionapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference ref = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText emailText = findViewById(R.id.login_email);
        final Button logBtn = findViewById(R.id.login_btn);
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


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String logEmail = binding.loginEmail.getText().toString();
                String logPwd = binding.loginPwd.getText().toString();

                if (logEmail.equals("") || logPwd.equals("")) {
                    Toast.makeText(LoginActivity.this, "All fields are mandatory",
                            Toast.LENGTH_SHORT).show();
                } else {
                    String encryptedPwd = null;
                    try {
                        encryptedPwd = encrypt(logPwd);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    pwdAlreadyExists(logEmail, encryptedPwd);
                }
            }
        });

        binding.regRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    private void pwdAlreadyExists(String email, String userPassword) {
        Query emailQuery = ref.whereEqualTo("email", email);
        emailQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult().isEmpty()){
                            Toast.makeText(LoginActivity.this, "Please Register", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "Please Register");
                        }
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String storedPassword = document.getString("password");
                                if (storedPassword.equals(userPassword)) {
                                    Log.d("TAG", "Login Successful");
                                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    Log.d("TAG", "Invalid Credentials");
                                }
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "Login Failed");
                        }
                    }
                });
    }

    private String encrypt(String password) throws Exception {
        String ALGORITHM = "Blowfish";
        String MODE = "Blowfish/CBC/PKCS5Padding";
        String IV = "abcdefgh";
        String Key = "CyanCat"; // Key: whatever key we want

        SecretKeySpec secretKeySpec = new SecretKeySpec(Key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes()));
        byte[] values = cipher.doFinal(password.getBytes());
        return Base64.encodeToString(values, Base64.DEFAULT);
    }
}