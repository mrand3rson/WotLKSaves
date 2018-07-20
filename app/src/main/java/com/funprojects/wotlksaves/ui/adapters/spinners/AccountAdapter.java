package com.funprojects.wotlksaves.ui.adapters.spinners;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.funprojects.wotlksaves.mvp.models.Account;
import com.funprojects.wotlksaves.mvp.models.Server;

import java.util.List;

/**
 * Created by Andrei on 29.04.2018.
 */

public class AccountAdapter extends ArrayAdapter<Account> {
    public AccountAdapter(@NonNull Context context, int resource, @NonNull List<Account> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        Account account = getItem(position);
        TextView tName = convertView.findViewById(android.R.id.text1);
        tName.setText(account.getName());
        return convertView;
    }
}
