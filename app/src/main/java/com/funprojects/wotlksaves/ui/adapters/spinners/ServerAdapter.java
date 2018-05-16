package com.funprojects.wotlksaves.ui.adapters.spinners;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.funprojects.wotlksaves.mvp.models.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andrei on 29.04.2018.
 */

public class ServerAdapter extends ArrayAdapter<Server> {
    public ServerAdapter(@NonNull Context context, int resource, @NonNull List<Server> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        Server server = getItem(position);
        TextView tName = convertView.findViewById(android.R.id.text1);
        tName.setText(server.getName());
        return convertView;
    }
}
