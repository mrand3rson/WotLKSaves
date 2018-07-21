package com.funprojects.wotlksaves.ui.adapters.recyclers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.ListRecord;
import com.funprojects.wotlksaves.tools.ListTypes;
import com.funprojects.wotlksaves.ui.activities.MainActivity;
import com.funprojects.wotlksaves.ui.dialogs.RecordContextMenuDialog;
import com.funprojects.wotlksaves.ui.fragments.TabFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Andrei on 21.05.2018.
 */

public class ListRecordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private final int mItemLayout;
    private final TabFragment parentFragment;
    public ArrayList<ListRecord> data;

    private boolean multiSelect = false;
    private ArrayList<ListRecord> selectedItems = new ArrayList<>();


    public ListRecordsAdapter(TabFragment fragment, Context context, int itemLayout, ArrayList<ListRecord> data) {
        this.parentFragment = fragment;
        this.mContext = context;
        this.mItemLayout = itemLayout;
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return mItemLayout;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(mItemLayout, parent, false);
        switch (viewType) {
            case R.layout.recycler_blacklist_item: {
                return new BlackViewHolder(v);
            }
            case R.layout.recycler_whitelist_item: {
                return new WhiteViewHolder(v);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case R.layout.recycler_blacklist_item: {
                ((BlackViewHolder)holder).bind(data.get(position));
                break;
            }
            case R.layout.recycler_whitelist_item: {
                ((WhiteViewHolder)holder).bind(data.get(position));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    private void selectItemForAction(View layout, ListRecord record) {
        int checked;
        int unchecked;

        switch (mItemLayout) {
            case R.layout.recycler_blacklist_item: {
                checked = mContext.getResources().getColor(R.color.whitelist_background);
                unchecked = mContext.getResources().getColor(R.color.blacklist_background);
                break;
            }
            case R.layout.recycler_whitelist_item: {
                checked = mContext.getResources().getColor(R.color.blacklist_background);
                unchecked = mContext.getResources().getColor(R.color.whitelist_background);
                break;
            }
            default:
                return;
        }

        if (multiSelect) {
            if (selectedItems.contains(record)) {
                selectedItems.remove(record);
                layout.setBackgroundColor(unchecked);
            } else {
                selectedItems.add(record);
                layout.setBackgroundColor(checked);
            }
        }
    }


    private void openPlacesBoard(ListRecord record) {
        //TODO: open dialog to show instances' checkboxes
        Toast.makeText(mContext, "OOPS", Toast.LENGTH_SHORT).show();
    }

    private void openDialogMenu(ListRecord record, int adapterIndex) {
        ArrayList<String> menuItems = new ArrayList<>();
        List<ListRecord> originalList;
        if (record.getListType() == ListTypes.BLACK) {
            menuItems.add("Move to whitelist");
            originalList = parentFragment.getPresenter().getBlacklist((MainActivity)mContext);
        } else if (record.getListType() == ListTypes.WHITE) {
            menuItems.add("Move to blacklist");
            originalList = parentFragment.getPresenter().getWhitelist((MainActivity)mContext);
        } else return;

        RecordContextMenuDialog dialog = RecordContextMenuDialog.newInstance(menuItems, originalList.indexOf(record), adapterIndex);
        dialog.setTargetFragment(parentFragment, 1);

        ((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction()
                .add(dialog, null)
                .commit();
    }


    class BlackViewHolder extends RecyclerView.ViewHolder {

        @BindDimen(R.dimen.activity_horizontal_margin)
        int horizontalMargin;
        @BindView(R.id.black_item_layout)
        RelativeLayout layoutView;

        @BindView(R.id.black_nickname)
        TextView nicknameView;

        @BindView(R.id.black_counter)
        TextView counterView;

        @BindView(R.id.black_seen)
        Button seenButton;

        private BlackViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        private void bind(ListRecord record) {
            this.setIsRecyclable(false);

            layoutView.setOnLongClickListener(view -> {
                if (!multiSelect) {
                    initActionMode();
                    selectItemForAction(view, record);
                }
                return true;
            });
            layoutView.setOnClickListener(view -> {
                if (multiSelect) {
                    selectItemForAction(view, record);
                } else {
                    openDialogMenu(record, getAdapterPosition());
                }
            });

            nicknameView.setText(record.getName());
            int factionColor = mContext.getResources().getColor(record.isHorde()?
                    R.color.faction_horde_color:
                    R.color.faction_alliance_color);
            nicknameView.setTextColor(factionColor);

            counterView.setText(String.valueOf(record.getTimesSeen()));
            seenButton.setOnClickListener(view -> openPlacesBoard(record));

            LinearLayout reasonsLayout = prepareReasonsLayout();
            layoutView.addView(reasonsLayout);

            fillReasonsLayout(reasonsLayout, record.getReasons());
        }

        private LinearLayout prepareReasonsLayout() {
            RelativeLayout.LayoutParams relativeParams =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParams.addRule(RelativeLayout.ALIGN_START, R.id.black_title_layout);
            relativeParams.addRule(RelativeLayout.BELOW, R.id.black_title_layout);
            relativeParams.leftMargin = horizontalMargin;
            relativeParams.rightMargin = horizontalMargin;

            LinearLayout reasonsLayout = new LinearLayout(mContext);
            reasonsLayout.setLayoutParams(relativeParams);
            reasonsLayout.setOrientation(LinearLayout.VERTICAL);

            return reasonsLayout;
        }

        private void fillReasonsLayout(LinearLayout reasonsLayout, List<String> reasons) {
            LinearLayout.LayoutParams reasonParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            for (String reason : reasons) {
                TextView reasonView = new TextView(mContext);
                reasonView.setTextAppearance(mContext, R.style.AppTheme_Dark);
                reasonView.setLayoutParams(reasonParams);
                reasonView.setText(reason);
                reasonsLayout.addView(reasonView);
            }
        }
    }


    class WhiteViewHolder extends RecyclerView.ViewHolder {
        @BindDimen(R.dimen.activity_horizontal_margin)
        int horizontalMargin;

        @BindView(R.id.white_item_layout)
        RelativeLayout layoutView;

        @BindView(R.id.white_nickname)
        TextView nicknameView;

        @BindView(R.id.white_counter)
        TextView counterView;

        @BindView(R.id.white_seen)
        Button seenButton;


        private WhiteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(ListRecord record) {
            this.setIsRecyclable(false);

            layoutView.setOnLongClickListener(view -> {
                if (!multiSelect) {
                    initActionMode();
                    selectItemForAction(view, record);
                }
                return true;
            });
            layoutView.setOnClickListener(view -> {
                if (multiSelect) {
                    selectItemForAction(view, record);
                } else {
                    openDialogMenu(record, getAdapterPosition());
                }
            });

            nicknameView.setText(record.getName());
            int factionColor = mContext.getResources().getColor(record.isHorde()?
                    R.color.faction_horde_color:
                    R.color.faction_alliance_color);
            nicknameView.setTextColor(factionColor);

            counterView.setText(String.valueOf(record.getTimesSeen()));
            seenButton.setOnClickListener(view -> {
                openPlacesBoard(record);
            });

            LinearLayout reasonsLayout = prepareReasonsLayout();
            layoutView.addView(reasonsLayout);

            fillReasonsLayout(reasonsLayout, record.getReasons());
        }

        private LinearLayout prepareReasonsLayout() {
            RelativeLayout.LayoutParams relativeParams =
                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeParams.addRule(RelativeLayout.ALIGN_START, R.id.white_title_layout);
            relativeParams.addRule(RelativeLayout.BELOW, R.id.white_title_layout);
            relativeParams.leftMargin = horizontalMargin;
            relativeParams.rightMargin = horizontalMargin;

            LinearLayout reasonsLayout = new LinearLayout(mContext);
            reasonsLayout.setLayoutParams(relativeParams);
            reasonsLayout.setOrientation(LinearLayout.VERTICAL);

            return reasonsLayout;
        }
        private void fillReasonsLayout(LinearLayout reasonsLayout, List<String> reasons) {
            LinearLayout.LayoutParams reasonParams =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            for (String reason : reasons) {
                TextView reasonView = new TextView(mContext);
                reasonView.setLayoutParams(reasonParams);
                reasonView.setText(reason);
                reasonsLayout.addView(reasonView);
            }
        }

    }

    private void initActionMode() {
        ActionMode.Callback callback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                multiSelect = true;
                ((MainActivity)mContext).getMenuInflater().inflate(R.menu.main_contacts_action, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_delete: {
                        removeItems(selectedItems);
                        actionMode.finish();
                        return true;
                    }
                }

                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                multiSelect = false;
                selectedItems.clear();
                notifyDataSetChanged();
            }

            private void removeItems(ArrayList<ListRecord> selectedItems) {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();

                data.removeAll(selectedItems);
                for (ListRecord item : selectedItems) {
                    item.deleteFromRealm();
                }

                realm.commitTransaction();
                notifyDataSetChanged();
            }
        };

        ((MainActivity)mContext).initContactsActionMode(callback);
    }
}
