package com.example.odawo.dodo_drive.model_class;

import android.location.Location;

import com.example.odawo.dodo_drive.remote.IGoogleAPI;
import com.example.odawo.dodo_drive.remote.Retrofit_Client;

public class Common {

    public static final String DRIVER_TB = "Cab_Driver"; //userdriver
    public static final String DRIVERINFO_TB = "Driver_Information";
    public static final String CLIENT_TB = "Client_Information";
    public static final String PICKUPREQUEST_TB = "PickUpRequest";
    public static final String TOKEN_TB = "Tokens";

    public static Location mLastLocation = null;

    public static User currentUser;

    public static String currentToken = "";

    public static final String baseURL = "https://maps.googleapis.com";
    public static final String fcmURL = "https://fcm.googleapis.com/";

    public static IGoogleAPI getGoogleAPI() {
        return Retrofit_Client.getClient(baseURL).create(IGoogleAPI.class);
    }

}
