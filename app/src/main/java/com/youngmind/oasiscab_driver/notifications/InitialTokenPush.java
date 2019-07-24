package com.youngmind.oasiscab_driver.notifications;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.youngmind.oasiscab_driver.Config;
import com.youngmind.oasiscab_driver.http.RQ;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

public class InitialTokenPush {

    private Context context;
    private String TAG = "notifications";

    public InitialTokenPush(Context context) {
        this.context = context;
    }

    public void pushTokenToServer(final String url) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        //uniqueID is obtained on successful login as user_id
                        String user_id = Config.uniqueID;
                        try {
                            makePostRequest(url, token, user_id);
                            Log.d(TAG, "token ::: " + token);
                            Log.d(TAG, "uniqueID ::: " + user_id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Log and toast
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void makePostRequest(String url, final String token, final String user_id) throws Exception{
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Log.d(TAG, "initial token push success");
                        Log.d(TAG, "token pushed => " + token);
                        Log.d(TAG, "token pushed :: ID => " + user_id);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
//                        Log.d("Error.Response", error.getMessage());
                        error.printStackTrace();
                        out.println("error occurs here!!!!!!!!");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id", user_id);
                params.put("token", token);

                return params;
            }
        };
        RQ.getInstance(context).addToRequestQueue(postRequest);
    }

}
