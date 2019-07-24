package com.youngmind.oasiscab_driver;

public class Config {

    public static final String HOST = "http://13.92.100.73";
    public static final String NOTIFICATION_API = "http://13.92.100.73:8000/api/token/";
    public static final String BUSINESS_API = "http://13.92.100.73:8003/api/token/";
    public static final String CREATE_PROFILE = "http://13.92.100.73:8003/api/rest-auth/registration/";
    public static final String KAFKA_API_LOCATION = "http://13.92.100.73:8001/api/post-location";

    //Tokens
    public static String accessToken = null;
    public static String refreshToken = null;

    //unique_id to driver
    public static String uniqueID = null;
}
