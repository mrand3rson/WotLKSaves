package com.funprojects.wotlksaves.ui.adapters.recyclers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.ListRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Andrei on 21.05.2018.
 */

public class ListRecordsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final Context mContext;
    private final int mItemLayout;

    public ArrayList<ListRecord> data;


    public ListRecordsAdapter(Context context, int itemLayout, ArrayList<ListRecord> data) {
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

            nicknameView.setText(record.getName());
            counterView.setText(String.valueOf(record.getTimesSeen()));
            seenButton.setOnClickListener(view -> {
                //TODO: open dialog with "whereSeen" board
                Toast.makeText(mContext, "OOPS", Toast.LENGTH_SHORT).show();
            });

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

            nicknameView.setText(record.getName());
            counterView.setText(String.valueOf(record.getTimesSeen()));
            seenButton.setOnClickListener(view -> {
                //TODO: open dialog with "whereSeen" board
                Toast.makeText(mContext, "OOPS", Toast.LENGTH_SHORT).show();
            });

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
                reasonView.setLayoutParams(reasonParams);
                reasonView.setText(reason);
                reasonsLayout.addView(reasonView);
            }
        }

    }

    @Override
    public void onClick(View view) {

    }
}
