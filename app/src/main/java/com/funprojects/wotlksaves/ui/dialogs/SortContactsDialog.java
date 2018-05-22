package com.funprojects.wotlksaves.ui.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.funprojects.wotlksaves.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.funprojects.wotlksaves.ui.dialogs.SortTypes.ARG_SORT_TYPE;

/**
 * Created by Andrei on 22.05.2018.
 */

public class SortContactsDialog extends DialogFragment {

    @BindView(R.id.lv)
    ListView lv;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Sort by...");
        View v = inflater.inflate(R.layout.dialog_contacts_sort, container, false);
        ButterKnife.bind(this, v);
        lv.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                SortTypes.getAllSortNames()));
        lv.setOnItemClickListener((adapterView, view, index, l) -> {
            Intent intent = new Intent();

            switch (index) {
                case 0: {
                    intent.putExtra(ARG_SORT_TYPE, SortTypes.SORT_BY_NAME_ASC);
                    break;
                }
                case 1: {
                    intent.putExtra(ARG_SORT_TYPE, SortTypes.SORT_BY_NAME_DESC);
                    break;
                }
                case 2: {
                    intent.putExtra(ARG_SORT_TYPE, SortTypes.SORT_BY_DATE_ASC);
                    break;
                }
                case 3: {
                    intent.putExtra(ARG_SORT_TYPE, SortTypes.SORT_BY_DATE_DESC);
                    break;
                }
                case 4: {
                    intent.putExtra(ARG_SORT_TYPE, SortTypes.SORT_BY_TIMES_SEEN);
                    break;
                }
            }

            getTargetFragment().onActivityResult(SortTypes.REQUEST_SORT_TYPE, 0, intent);
            dismiss();
        });
        return v;
    }
}
