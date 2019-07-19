package com.youngmind.oasiscab_driver.notifications;

import android.app.Notification;
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
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.youngmind.oasiscab_driver.R;
import com.youngmind.oasiscab_driver.activities.RideRequest;
import com.youngmind.oasiscab_driver.http.RQ;

import java.util.HashMap;
import java.util.Map;

import br.com.goncalves.pugnotification.notification.PugNotification;

import static com.youngmind.oasiscab_driver.Config.NOTIFICATION_API;
import static java.lang.System.out;

public class NotificationService extends FirebaseMessagingService {

    private static final String TAG = "notifications";
    private static final String link = NOTIFICATION_API;

    public NotificationService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        out.println("msg from => " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
            } else {
                // Handle message within 10 seconds
//                handleNow();
            }

        }

        String title = null;
        String message = null;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            message = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Title: " + title);
            Log.d(TAG, "Message Notification Body: " + message);
        }

        //show notification on tray
        displayNotification(title, message);
    }


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        //send token to server
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {

        //send token
        makePostRequest(link, token);
    }

    private void displayNotification(String title, String message){
        PugNotification.with(getApplicationContext())
                .load()
                .title(title)
                .message(message)
                .bigTextStyle("bigtext")
                .smallIcon(R.drawable.pugnotification_ic_launcher)
                .largeIcon(R.drawable.pugnotification_ic_launcher)
                .click(RideRequest.class)
                .flags(Notification.DEFAULT_ALL)
                .simple()
                .build();
    }

    private void makePostRequest(String url, final String token){
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
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id", "hama");
                params.put("token", token);

                return params;
            }
        };
        RQ.getInstance(getApplicationContext()).addToRequestQueue(postRequest);
    }




}
