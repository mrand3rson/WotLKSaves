package com.funprojects.wotlksaves.ui.dialogs;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.funprojects.wotlksaves.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordContextMenuDialog extends DialogFragment {

    public final static String ARG_ACTION = "Action";
    public static final String ARG_INDEX = "Index";
    public static final String ARG_ADAPTER_INDEX = "Adapter_ndex";
    private final static String ARG_ITEMS = "Items";

    @BindView(R.id.lv_menu)
    ListView lv;

    private ArrayList<String> menuItems;
    private int index;
    private int adapterIndex;


    public RecordContextMenuDialog() {

    }

    public static RecordContextMenuDialog newInstance(ArrayList<String> menuItems, int index, int adapterIndex) {
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_ITEMS, menuItems);
        args.putInt(ARG_INDEX, index);
        args.putInt(ARG_ADAPTER_INDEX, adapterIndex);
        RecordContextMenuDialog fragment = new RecordContextMenuDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuItems = getArguments().getStringArrayList(ARG_ITEMS);
        index = getArguments().getInt(ARG_INDEX);
        adapterIndex = getArguments().getInt(ARG_ADAPTER_INDEX);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.string_choose_action);

        View v = inflater.inflate(R.layout.dialog_list_layout, container, false);

        ButterKnife.bind(this, v);

        lv.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, menuItems));
        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent();
            intent.putExtra(ARG_ACTION, i);
            intent.putExtra(ARG_INDEX, index);
            intent.putExtra(ARG_ADAPTER_INDEX, adapterIndex);
            getTargetFragment().onActivityResult(1, 0, intent);
            dismiss();
        });
        return v;
    }

}
