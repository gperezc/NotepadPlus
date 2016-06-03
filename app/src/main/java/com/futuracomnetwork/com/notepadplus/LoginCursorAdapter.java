package com.futuracomnetwork.com.notepadplus;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by gperezc on 31/05/16.
 */

public class LoginCursorAdapter extends CursorAdapter {

    private LoginDbAdapter dbAdapter = null ;

    public LoginCursorAdapter(Context context, Cursor c)
    {
        super(context, c);
        dbAdapter = new LoginDbAdapter(context);
        dbAdapter.abrir();
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        TextView tv = (TextView) view ;

        tv.setText(cursor.getString(cursor.getColumnIndex(LoginDbAdapter.C_COLUMNA_USUARIO)));

        if (cursor.getString(cursor.getColumnIndex(LoginDbAdapter.C_COLUMNA_PISTA)).equals("S"))
        {
            tv.setTextColor(Color.BLACK);
        }
        else
        {
            tv.setTextColor(Color.GRAY);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);

        return view;
    }

}
