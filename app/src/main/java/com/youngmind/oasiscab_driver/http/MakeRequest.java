package com.youngmind.oasiscab_driver.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MakeRequest {

    private Context context;
    private String url;

    public MakeRequest(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    public void makePostRequest(final HashMap<String, String> params){
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
        RQ.getInstance(context).addToRequestQueue(postRequest);
    }
}
