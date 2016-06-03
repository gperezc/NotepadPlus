package com.futuracomnetwork.com.notepadplus;

import android.preference.PreferenceActivity;
import android.os.Bundle;

public class Configuracion extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.configuracion);
    }
}
