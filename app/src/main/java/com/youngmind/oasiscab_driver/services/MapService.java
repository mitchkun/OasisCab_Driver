package com.youngmind.oasiscab_driver.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;
import com.youngmind.oasiscab_driver.fragments.Home;

public class MapService extends Service implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FusedLocationProviderClient fusedLocationClient;
    // Create a new HttpClient and Post Header
    //private final RequestQueue queue = Volley.newRequestQueue(this);
    // TODO: change url string to our server string
    private final String url ="http://www.google.com";


    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Home.OnFragmentInteractionListener mListener;
    private GoogleMap mMap;
    private int requestCode;
    private String[] permissions;
    private int[] grantResults;
    private Location loc = null;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(){fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        /*mGoogleApiClient = new GoogleApiClient.Builder(MapService.this)
                .addApi(LocationServices.API).addConnectionCallbacks(MapService.this)
                .addOnConnectionFailedListener(MapService.this).build();
        mGoogleApiClient.connect();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //final Location loc = fusedLocationClient.getLastLocation().getResult();
        Task task = fusedLocationClient.getLastLocation();
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                loc = (Location) task.getResult();
                Log.d(TAG, "Location returned. About to call getForecast(): "  + loc.getLongitude());
                //location.setLatitude(location1.getLatitude());
                //location.setLongitude(location1.getLongitude());
            }
        });*/

        //*Location loc;
        /*fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d(TAG, "Location returned.");
                            // write code here to to make the API call to the weather service and update the UI
                            // this code here is running on the Main UI thread as far as I understand
                            loc = location;
                        } else {
                            Log.d(TAG, "Null location returned.");
                        }
                    }
                });

        Task task = fusedLocationClient.getLastLocation();*/
        //final Location loc = (Location) task.getResult();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(4000);
        mLocationRequest.setFastestInterval(4000);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        /*RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // TODO: change url string to our server string
        String url ="http://www.google.com";
        //Post Request Function, uses Volley and Request Queue to process
        //requests as they are cached
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String resp = response.substring(0,100);
                        //Toasts created for showing request responses. if not necessary, will be removed

                        Toast.makeText(getApplicationContext(), "Post Response:\n" + resp, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "UnSuccessful Request\n", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Map<String,String> params = new HashMap<String, String>();
                //Post Data as strings. Service will however be checking UID every 4 seconds
                //Maybe this can be optimized
                params.put("UID",user.getUid());
                params.put("Long",Double.toString(loc.getLongitude()));
                params.put("Lat", Double.toString(loc.getLatitude()));


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        //The requests are put in a Cache queue and processed in line once connected or server response is successful
        queue.add(stringRequest);*/
        //buildGoogleApiClient();
        /*if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }*/
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        //final Location loc = fusedLocationClient.getLastLocation().getResult();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(4000);
        mLocationRequest.setFastestInterval(4000);
        Task task = fusedLocationClient.getLastLocation();
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                loc = (Location) task.getResult();
                Log.d(TAG, "Location returned. About to call getForecast(): "  + loc.getLongitude());
                //location.setLatitude(location1.getLatitude());
                //location.setLongitude(location1.getLongitude());
            }
        });



        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // TODO: change url string to our server string
        String url ="http://13.92.100.73:8001/api/post-location";
        //Post Request Function, uses Volley and Request Queue to process
        //requests as they are cached
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String resp = response.substring(0,100);
                        //Toasts created for showing request responses. if not necessary, will be removed
                        Toast.makeText(getApplicationContext(), "Post Response:\n" + resp, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "UnSuccessful Request\n", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Map<String,String> params = new HashMap<String, String>();
                //Post Data as strings. Service will however be checking UID every 4 seconds
                //Maybe this can be optimized
                params.put("UID",user.getUid());
                params.put("Long",Double.toString(loc.getLongitude()));
                params.put("Lat", Double.toString(loc.getLatitude()));


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        //The requests are put in a Cache queue and processed in line once connected or server response is successful
        queue.add(stringRequest);

        if (ContextCompat.checkSelfPermission(this,
              Manifest.permission.ACCESS_FINE_LOCATION)
               == PackageManager.PERMISSION_GRANTED) {
           LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                //checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }



    }


    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + "(" +location.getLatitude()+";" + location.getLongitude()+")", Toast.LENGTH_LONG).show();
    }






    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        //log location changes
        Log.d("locations", location.getLatitude() + ", " + location.getLongitude());

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        // TODO: change url string to our server string
        String url ="http://13.92.100.73:8001/api/post-location";
        //Post Request Function, uses Volley and Request Queue to process
        //requests as they are cached
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String resp = response.substring(0,100);
                        //Toasts created for showing request responses. if not necessary, will be removed
                        Toast.makeText(getApplicationContext(), "Post Response:\n" + resp, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "UnSuccessful Request\n", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Map<String,String> params = new HashMap<String, String>();
                //Post Data as strings. Service will however be checking UID every 4 seconds
                //Maybe this can be optimized
                params.put("UID",user.getUid());
                params.put("Long",Double.toString(mLastLocation.getLongitude()));
                params.put("Lat", Double.toString(mLastLocation.getLatitude()));


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        //The requests are put in a Cache queue and processed in line once connected or server response is successful
        queue.add(stringRequest);

        //Place current location marker
        //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //MarkerOptions markerOptions = new MarkerOptions();
        //markerOptions.position(latLng);
        //markerOptions.title("Current Position");
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        //mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));*/
        //Toast.makeText(this, "Current location:\n" + "(" +location.getLatitude()+";" + location.getLongitude()+")", Toast.LENGTH_LONG).show();
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 8));
        Toast.makeText(this, "Current location:\n" +
                "(" +location.getLatitude()+";" + location.getLongitude()+")", Toast.LENGTH_LONG).show();
    }



}
