package com.youngmind.oasiscab_driver.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RQ {

    private static RQ instance;
    private RequestQueue requestQueue;
    private static Context mContext;

    private RQ(Context context){
        mContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized RQ getInstance(Context context){
        if (instance == null) {
            instance = new RQ(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
