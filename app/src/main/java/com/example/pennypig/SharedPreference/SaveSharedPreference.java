package com.example.pennypig.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String USER_ID = "userid";
    static final String USER_NAME = "username";
    static final String USER_EMAIL = "useremail";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserId(Context ctx, String userId)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public static String getUserId(Context ctx)
    {
        return getSharedPreferences(ctx).getString(USER_ID, "");
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(USER_NAME, "");
    }

    public static void setUserEmail(Context ctx, String userEmail)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(USER_EMAIL, userEmail);
        editor.commit();
    }

    public static String getUserEmail(Context ctx)
    {
        return getSharedPreferences(ctx).getString(USER_EMAIL, "");
    }
}
