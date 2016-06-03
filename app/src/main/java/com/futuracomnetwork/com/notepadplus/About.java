package com.futuracomnetwork.com.notepadplus;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity {

    //private Button btAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button btAceptar = (Button) findViewById(R.id.button);
        btAceptar.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

    }
}
