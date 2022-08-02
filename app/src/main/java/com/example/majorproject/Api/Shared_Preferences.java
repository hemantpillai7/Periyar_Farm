package com.example.majorproject.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Shared_Preferences
{
    static String prefName = "sharedPrefs";
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    public Shared_Preferences(Context prefs)
    {
        this.prefs = PreferenceManager.getDefaultSharedPreferences(prefs);
    }

    public static void setPrefs(Context context, String prefKey, String prefValue)
    {
        preferences = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(prefKey, prefValue);
        editor.commit();
    }
    public static String getPrefs(Context context, String prefKey)
    {
        preferences = context.getSharedPreferences(prefName, context.MODE_PRIVATE);
        return preferences.getString(prefKey, null);
    }
}
