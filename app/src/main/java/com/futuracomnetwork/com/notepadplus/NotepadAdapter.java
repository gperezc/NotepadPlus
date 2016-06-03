package com.futuracomnetwork.com.notepadplus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gperezc on 26/05/16.
 */

public class NotepadAdapter extends ArrayAdapter<Notepad> {

    private static class ViewHolder {
        TextView text1;
    }

    public NotepadAdapter(Context context, ArrayList<Notepad> notepads) {
        super(context, android.R.layout.simple_list_item_1, notepads);
    }

    /*
    public NotepadAdapter(Context context, ArrayList<Notepad> notepads) {
        super(context, android.R.layout.simple_dropdown_item_1line, notepads);
    }*/

    /*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Notepad notepad = getItem(position);

        ViewHolder viewHolder;



        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text1.setText(notepad.getNombre());

        return convertView;
    }*/

    public View getView(int position,View view, ViewGroup parent){

        Notepad notepad = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.content_note_list, parent ,false);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.text111);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgico);

        txtTitle.setText(notepad.getNombre());
        //imageView.setImageResource(integers[posicion]);

        return rowView;
    }

    @Override
    public long getItemId(int position)
    {
        return getItem(position).getId();
    }


}
