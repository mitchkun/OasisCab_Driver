package com.youngmind.oasiscab_driver.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.youngmind.oasiscab_driver.R;
import com.youngmind.oasiscab_driver.http.RQ;

import java.util.HashMap;
import java.util.Map;

import static com.youngmind.oasiscab_driver.Config.CREATE_PROFILE;

public class CreateAccount extends AppCompatActivity {

    private EditText etxt_username;
    private EditText etxt_email;
    private EditText etxt_phone_number;
    private EditText etxt_password;
    private EditText etxt_confirm_pass;
    private EditText etxt_number_of_seats;
    private EditText etxt_number_plate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        etxt_username = findViewById(R.id.etxt_username);
        etxt_email = findViewById(R.id.etxt_email);
        etxt_phone_number = findViewById(R.id.etxt_phone_number);
        etxt_password = findViewById(R.id.etxt_password);
        etxt_confirm_pass = findViewById(R.id.etxt_confirm_pass);
        etxt_number_of_seats = findViewById(R.id.etxt_number_of_seats);
        etxt_number_plate = findViewById(R.id.etxt_number_plate);

        Button btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get and clean data from fields
                String username = etxt_username.getText().toString();
                String email = etxt_email.getText().toString();
                String phoneNumber = etxt_phone_number.getText().toString();
                String password = etxt_password.getText().toString();
                String confPassword = etxt_confirm_pass.getText().toString();
                String numberOfSeats = etxt_number_of_seats.getText().toString();
                String numberPlate;
                numberPlate = etxt_number_plate.getText().toString();

                //account creation details
                Map<String, String> details = new HashMap<>();
                details.put("username", username);
                details.put("email", email);
                details.put("password1", password);
                details.put("password2", confPassword);

                createProfile(CREATE_PROFILE, details);
            }
        });
    }

    private void createProfile(String url, final Map<String, String> params){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "error");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        RQ.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }
}
