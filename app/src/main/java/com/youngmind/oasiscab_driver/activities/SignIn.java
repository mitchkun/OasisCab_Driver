package com.youngmind.oasiscab_driver.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auth0.android.jwt.JWT;
import com.youngmind.oasiscab_driver.Config;
import com.youngmind.oasiscab_driver.R;
import com.youngmind.oasiscab_driver.http.RQ;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.youngmind.oasiscab_driver.Config.BUSINESS_API;
import static com.youngmind.oasiscab_driver.Config.accessToken;

public class SignIn extends AppCompatActivity {

    private EditText etxt_username;
    private EditText etxt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_login);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnCreateAccount = findViewById(R.id.btn_create_account);
        etxt_username = findViewById(R.id.etxt_username);
        etxt_password = findViewById(R.id.etxt_password);

        // login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etxt_username.getText().toString();
                String password = etxt_password.getText().toString();

                if(username.isEmpty() || password.isEmpty())
                    Toast.makeText(getApplicationContext(),
                            "Username or Password field empty!",
                            Toast.LENGTH_SHORT).show();
                else {
                    try {
                        authenticate(BUSINESS_API, username, password);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        // create account
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCreateAccount();
            }
        });
    }


    private void authenticate(String url, final String username, final String password) throws Exception{

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", username);
            jsonBody.put("password", password);

            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);

                    JSONObject data = null;
                    try {
                          data = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // on login success
                    try {

                        //store tokens on device, globally
                        //for making subsequent requests
                        Config.accessToken = data.getString("access");
                        Config.refreshToken = data.getString("refresh");

                        // get unique ID from token
                        JWT jwt = new JWT(accessToken);
                        Config.uniqueID = jwt.getClaim("user_id").asString();
                        Log.d("JWT", "unique_id => " + Config.uniqueID);


                        startHomeScreen();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Log.e("VOLLEY", error.getMessage());

                    //status code
                    //401 - Auth failure
                    int statusCode = error.networkResponse.statusCode;
                    if (statusCode == 401) {
                        Log.e("VOLLEY", "its here");
                        Toast.makeText(SignIn.this,
                                "Username or password incorrect",
                                Toast.LENGTH_SHORT).show();
                    }
                    error.printStackTrace();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                /*@Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }*/
            };

            RQ.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void requestUniqueID(String url, final Map<String, String> params){
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
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

    private void startHomeScreen() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    private void startCreateAccount() {
        startActivity(new Intent(this, CreateAccount.class));
        finish();
    }
}
