package com.example.sujaynaik.myapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by genora-pune on 4/21/17.
 */

public class MyPreferences {
    private static final String PREF_NAME = "MyApplication";
    private static final String USER_ID = "user_id";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String PHONE = "phone";
    private static final String TEMP_PHONE = "temp_phone";
    private static final String AUTH_TOKEN = "auth_token";
    private static final String IS_LOGGED_IN = "isloggedin";
    private static final String DEVICE_ID = "deviceID";
    private static final String IsSetupComplete = "IsSetupComplete";
    private static final String INITIALS = "initials";
    private static final String PROFILE_STATUS = "profile_status";
    private static final String PUBLIC_KEY = "public_key";
    private static final String COUNTRY_CODE = "country_code";

    /**
     * Saves auth_token, email in shared preferences and also sets isLoggedIn as true.
     */
    public static boolean saveUserDetails(Context context, String auth_token, String user_id, String email, String name, String phone,
                                          String initials, int profile_status) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(AUTH_TOKEN, auth_token);
        editor.putString(USER_ID, user_id);
        editor.putString(EMAIL, email);
        editor.putString(NAME, name);
        editor.putString(PHONE, phone);
        editor.putString(INITIALS, initials);
        editor.putInt(PROFILE_STATUS, profile_status);
        editor.putBoolean(IS_LOGGED_IN, true);
        return editor.commit();
    }

    /**
     * Returns true if logged in
     */
    public static boolean isLoggedIn(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }

    /**
     * User Id
     */
    public static String getUserId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(USER_ID, "");
    }

    /**
     * User Email
     */
    public static String getUserEmail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(EMAIL, "");
    }

    /**
     * User name
     */
    public static boolean setUserName(Context context, String user_name) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(NAME, user_name);
        return editor.commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(NAME, "");
    }

    /**
     * User Phone number
     */
    public static boolean setUserPhone(Context context, String user_phone) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PHONE, user_phone);
        return editor.commit();
    }

    public static String getUserPhone(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PHONE, "");
    }

    /**
     * Profile status
     */
    public static boolean setProfileStatus(Context context, int profile_status) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(PROFILE_STATUS, profile_status);
        return editor.commit();
    }

    public static int getProfileStatus(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(PROFILE_STATUS, 1);
    }

    /**
     * Token
     */
    public static boolean setToken(Context context, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(AUTH_TOKEN, token);
        return editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(AUTH_TOKEN, "");
    }

    /**
     * Temporary phone number
     */
    public static boolean setTempPhone(Context context, String temp_phone) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(TEMP_PHONE, temp_phone);
        return editor.commit();
    }

    public static String getTempPhone(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(TEMP_PHONE, "0");
    }

    /**
     * Public key from server
     */
    public static boolean setPublicKey(Context context, String public_key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PUBLIC_KEY, public_key);
        return editor.commit();
    }

    public static String getPublicKey(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PUBLIC_KEY, "");
    }

    /**
     * Initials of User name
     */
    public static boolean setInitials(Context context, String initials) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(INITIALS, initials);
        return editor.commit();
    }

    public static String getInitials(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(INITIALS, "test");
    }

    /**
     * Country code
     */
    public static boolean setCountryCode(Context context, String country_code) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(COUNTRY_CODE, country_code);
        return editor.commit();
    }

    public static String getCountryCode(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(COUNTRY_CODE, "91");
    }

    /**
     * Saves Device Id during setup process of the Bolt Device
     */
    /*public static boolean saveDeviceId(Context context, String deviceId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(DEVICE_ID, deviceId);
        return editor.commit();
    }

    public static String getDeviceId(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(DEVICE_ID, "");
    }*/

    /**
     * Returns false if setup was left incomplete
     */
    public static boolean checkSetupStatus(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(IsSetupComplete, true);
    }

    /**
     * updates the setup complete status as and when complete
     */
    public static void updateSetUpComplete(Context context, boolean status) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(IsSetupComplete, status);
        editor.apply();
    }

    /**
     * clear all preferences
     */
    public static boolean clearPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.edit().clear().commit();
    }
}