package com.example.nagys.chatty;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtEmailAdress;
    private EditText edtPassword;
    private EditText edtConfirmPassword;

    private FirebaseAuth mAuth;

    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // creating progress hud
        hud = Functionality.createProgressHud(RegisterActivity.this, "Please wait", "Creating account...");

        Button btnRegister = findViewById(R.id.btn_register);

        edtEmailAdress = findViewById(R.id.edt_email_address);
        edtPassword = findViewById(R.id.edt_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFirebaseUser();
            }
        });
    }

    // creating user with email and password
    private void createFirebaseUser() {

        String email = edtEmailAdress.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPwd = edtConfirmPassword.getText().toString();


        if(isEmailValid(email) && isPasswordValid(password, confirmPwd)) {

            hud.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        // user has been created

                        hud.dismiss();

                        Intent contactsIntent = new Intent(RegisterActivity.this, ContactsActivity.class);
                        startActivity(contactsIntent);
                        finish();
                    }
                    else {
                        // user creation failed
                        Functionality.showErrorDialog("Oops!", "Something went wrong. Check internet connection!", RegisterActivity.this);
                    }
                }
            });
        }
        else {
            Functionality.showErrorDialog("Oops!","Something went wrong. Password must be at least 6 characters and must match confirmation password. Also email must be valid!", RegisterActivity.this);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password, String confirmPassword) {
        return password.length() >= 6 && password.equals(confirmPassword);
    }


}
