package com.example.nagys.chatty.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nagys.chatty.Classes.Functionality;
import com.example.nagys.chatty.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kaopiz.kprogresshud.KProgressHUD;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        hud = Functionality.createProgressHud(MainActivity.this, "Please wait", "Signing in...");

        // Button instances
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);

        // adding onClickListener to buttons
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start activity
                logIn();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start activity
                startRegisterActivity();
            }
        });
    }

    private void startContactListActivity() { // contacts list

        Intent loginIntent = new Intent(MainActivity.this, ContactsActivity.class);
        startActivity(loginIntent);
        finish();
    }

    private void startRegisterActivity() { // register

        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private void logIn() {

        EditText edtEmail = findViewById(R.id.edt_email_address);
        EditText edtPwd = findViewById(R.id.edt_password);

        String email = edtEmail.getText().toString();
        String pwd = edtPwd.getText().toString();

        if(isEmailValid(email) && isPasswordValid(pwd)) {

            hud.show();

            mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {
                        // login successful

                        hud.dismiss();

                        startContactListActivity();
                    }
                    else {

                        hud.dismiss();
                        // login failed
                        Functionality.showErrorDialog("Something went wrong", "Connection failed!", MainActivity.this);
                    }
                }
            });

        }
    }
}
