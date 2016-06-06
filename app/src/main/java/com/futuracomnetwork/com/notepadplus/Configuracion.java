package com.futuracomnetwork.com.notepadplus;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class Configuracion extends PreferenceActivity {

    private LoginDbAdapter dbAdapter;

    private long id ;
    private String user;
    private String pass;
    private String pist;

    private String iuser;
    private String ipass;
    private String ipist;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addPreferencesFromResource(R.xml.configuracion);


    }

 }




