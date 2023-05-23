package com.deadhunter.opendaycompanionapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.deadhunter.opendaycompanionapp.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    CollectionReference ref = db.collection("users");

    EditText txtPwd;
    TextView txtLower, txtUpper, txtNum, txtChar, txtPwdConfirm;

    Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        txtPwd = findViewById(R.id.reg_pwd);
        txtPwdConfirm = findViewById(R.id.pwd_confirm);
        txtLower = findViewById(R.id.txtLower);
        txtUpper = findViewById(R.id.txtUpper);
        txtNum = findViewById(R.id.txtNumber);
        txtChar = findViewById(R.id.txtChar);

        // Catching the data from user-input
        final EditText mobileText = findViewById(R.id.reg_mobile);
        final EditText emailText = findViewById(R.id.reg_email);
        regBtn = findViewById(R.id.reg_btn);

        // Changing text color red to invalid email and disable/enable the register button
        mobileText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(validateMobile(mobileText.getText().toString()))
                {
                    regBtn.setEnabled(true);
                }
                else{
                    regBtn.setEnabled(false);
                    mobileText.setError("Invalid mobile number!");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        // Changing text color red to invalid email and disable/enable the login button
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches()){
                    regBtn.setEnabled(true);
                }
                else{
                    regBtn.setEnabled(false);
                    emailText.setError("Invalid EmailAddress");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Changing text color red to invalid email and disable/enable the login button
        txtPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = txtPwd.getText().toString();
                validatePassword(password);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Changing text color red to invalid email and disable/enable the login button
        txtPwdConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String password = txtPwd.getText().toString();
                String confirmPassword = txtPwdConfirm.getText().toString();

                if (password.equals(confirmPassword)) {
                    regBtn.setEnabled(true);
                } else {
                    regBtn.setEnabled(false);
                    txtPwdConfirm.setError("Passwords don't match");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.regBtn.setOnClickListener(view -> {
            // Retrieve the user inputted data
            String email = binding.regEmail.getText().toString();
            String pwd = binding.regPwd.getText().toString();
            String pwd_confirm = binding.pwdConfirm.getText().toString();
            String name = binding.regName.getText().toString();
            String mobile = binding.regMobile.getText().toString();

            // Checking if the fields are empty
            if (email.equals("") || pwd.equals("") || pwd_confirm.equals("") ||
                    name.equals("") || mobile.equals("")) {
                Toast.makeText(RegisterActivity.this, "All fields are mandatory",
                        Toast.LENGTH_SHORT).show();
            } else{
                // Checking if the password and confirmation password are matching
                if (pwd.equals(pwd_confirm)) {
                    // Checking if the email is already exists in the Firestore
                    Query query = ref.whereEqualTo("email", email);
                    query.get()
                            .addOnSuccessListener(queryDocumentSnapshots -> {
                                // If email already exists in the database show an error
                                if (queryDocumentSnapshots.size() > 0) {
                                    Toast.makeText(RegisterActivity.this,
                                            "Email already exist, please login",Toast.LENGTH_SHORT).show();
                                } else{
                                    // Encrypting the password before storing
                                    String encryptedPwd;
                                    try {
                                        encryptedPwd = encrypt(pwd);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }

                                    // Creating an user object using user-inputted data
                                    User user = new User(name,email,mobile,encryptedPwd);

                                    // Saving user data in Firestore
                                    db.collection("users")
                                            .add(user)
                                            .addOnSuccessListener(documentReference -> Toast.makeText(RegisterActivity.this,
                                                    "Registration Success", Toast.LENGTH_SHORT).show())
                                            .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this,
                                                    "Registration Failed", Toast.LENGTH_SHORT).show());

                                    // Creating user in Firebase Authentication
                                    mAuth.createUserWithEmailAndPassword(email, pwd)
                                            .addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    // Sign in success, update UI with the signed-in user's information
                                                    Toast.makeText(RegisterActivity.this, "Account Created.",
                                                            Toast.LENGTH_SHORT).show();
                                                    Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(task1 -> Toast.makeText(RegisterActivity.this, "Please Verify account before login.",
                                                            Toast.LENGTH_LONG).show());

                                                } else {
                                                    // If sign in fails, display a message to the user.
                                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }

                                // Open the LoginActivity
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            });
                } else {
                    // If passwords are not matching show an error
                    Toast.makeText(RegisterActivity.this,
                            "Invalid Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Method for encrypting password
    private String encrypt(String password) throws Exception {
        String ALGORITHM = "Blowfish";
        String MODE = "Blowfish/CBC/PKCS5Padding";
        String IV = "abcdefgh";
        String Key = "CyanCat";

        SecretKeySpec secretKeySpec = new SecretKeySpec(Key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(IV.getBytes()));
        byte[] values = cipher.doFinal(password.getBytes());
        return Base64.encodeToString(values, Base64.DEFAULT);
    }

    // Method for validate mobile number
    boolean validateMobile(String input){
        Pattern p = Pattern.compile("[0-9]{10}");
        Matcher m = p.matcher(input);
        return m.matches();

    }

    // Method for validate password
    public void validatePassword(String password){
        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern digitCase = Pattern.compile("[0-9]");

        if(!lowerCase.matcher(password).find()){
            txtLower.setTextColor(Color.RED);
        }else {
            txtLower.setTextColor(Color.GREEN);
        }

        if(!upperCase.matcher(password).find()){
            txtUpper.setTextColor(Color.RED);
        }else {
            txtUpper.setTextColor(Color.GREEN);
        }

        if(!digitCase.matcher(password).find()){
            txtNum.setTextColor(Color.RED);
        }else {
            txtNum.setTextColor(Color.GREEN);
        }

        if (password.length()<8){
            txtChar.setTextColor(Color.RED);
        }else{
            txtChar.setTextColor(Color.GREEN);
        }

        regBtn.setEnabled(lowerCase.matcher(password).find() && upperCase.matcher(password).find() && digitCase.matcher(password).find() && password.length() >= 8);
    }

    @Override
    public void onBackPressed() {
        finishActivity(0);
        // Open the LoginActivity
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }
}