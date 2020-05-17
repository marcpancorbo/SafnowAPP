package com.example.safnow;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesController {

    private static PreferencesController preferencesController;
    SharedPreferences preferences;


    Set<String> contactsSet = new HashSet<String>();

    public static PreferencesController getInstance() {
        if (preferencesController == null) {
            preferencesController = new PreferencesController();
        }
        return preferencesController;
    }
    public Set<String> getContactsSet() {
        return contactsSet;
    }


    /**
     * Method that allows to save the user token in shared preferences
     *
     * @param context Receives the context of the call
     * @param token   Receives the user token to save
     */
    public void addToken(Context context, String token) {
        preferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    /**
     * Method that allows to get from the shared preferences the user token
     *
     * @param context Receives the context of the call
     * @return Return the user token or null if no exist
     */
    public String getToken(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        return preferences.getString("token", null);
    }

    /**
     * Method that allows to save the time between notifications
     *
     * @param context Receives the context of the call
     * @param time    Receives the time
     */
    public void setTimerNotification(Context context, int time) {
        preferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("timer", time);
        editor.apply();
    }

    /**
     * Method that allows to get from the shared preferences the time between notifications
     *
     * @param context Receives the context of the call
     * @return Return the time or -1 if no exist
     */
    public int getTimeNotification(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        return preferences.getInt("timer", -1);
    }

    /**
     * Method that allows to save if timer notifications are active
     *
     * @param context Receives the context of the call
     * @param active  Receives the boolean
     */
    public void setTimerNotificationActive(Context context, boolean active) {
        preferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("timerActive", active);
        editor.apply();
    }

    /**
     * Method that allows to get from the shared preferences if the time notifications are activated
     *
     * @param context Receives the context of the call
     * @return Return the boolean or false if no exist
     */
    public boolean getTimerNotificationActive(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        return preferences.getBoolean("timerActive", false);
    }


    public void setContactFavorite(Context context,String contactsFav){
        preferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        contactsSet.add(contactsFav);
        editor.putStringSet("contactsFav", contactsSet);
        editor.apply();
    }

    public void deleteContactFav(Context context, String number){
        preferences = context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        contactsSet.remove(number);
        editor.putStringSet("contactsFav", contactsSet);
        editor.apply();
    }


    public Set<String> getContactsFavorite(Context context){
        preferences = context.getSharedPreferences(context.getPackageName(),MODE_PRIVATE);
        Set<String> contacts = preferences.getStringSet("contactsFav", null);
        if (contacts != null){
            contactsSet.addAll(contacts);
        }
        return contactsSet;
    }

}
