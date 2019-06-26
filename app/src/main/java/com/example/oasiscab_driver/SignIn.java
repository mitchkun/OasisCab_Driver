package com.example.oasiscab_driver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    public EditText loginEmailId, logInpasswd;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);
        Button loginButton = findViewById(R.id.login_btn);

        loginEmailId = findViewById(R.id.email_address);
        logInpasswd = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = loginEmailId.getText().toString();
                String userPaswd = logInpasswd.getText().toString();
                if (userEmail.isEmpty()) {
                    loginEmailId.setError("Provide your Email first!");
                    loginEmailId.requestFocus();
                } else if (userPaswd.isEmpty()) {
                    logInpasswd.setError("Enter Password!");
                    logInpasswd.requestFocus();
                } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                    Toast.makeText(SignIn.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(SignIn.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignIn.this, "Not successfull", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(SignIn.this, MainActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignIn.this, "Error", Toast.LENGTH_SHORT).show();
                }
                //intentToHomeScreen();
            }
        });
    }

    public void intentToHomeScreen() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
