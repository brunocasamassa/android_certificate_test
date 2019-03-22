package com.example.brunogerassicasamassa.testroompersistence.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    private static final String PREF_NAME = "backup";


    public SessionManager(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

    public void putCoordinate(String key, Double coordinate) {
        editor.putLong(key , coordinate.longValue());
        editor.commit();
    }



    public void removeKey(String key) {
        editor.remove(key);
        editor.commit();
    }


    public long getCoordinate(String key) {
        return pref.getLong( key, 0);
    }


}



