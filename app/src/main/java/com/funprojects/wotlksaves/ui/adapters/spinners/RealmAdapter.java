package com.funprojects.wotlksaves.ui.adapters.spinners;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.GameRealm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;

/**
 * Created by Andrei on 29.04.2018.
 */

public class RealmAdapter extends ArrayAdapter<GameRealm> {
    public RealmAdapter(@NonNull Context context, int resource, @NonNull List<GameRealm> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        GameRealm realm = getItem(position);
        TextView tName = convertView.findViewById(android.R.id.text1);
        tName.setText(String.format("%s - %s", realm.getServerName(), realm.getName()));
        return convertView;
    }
}
