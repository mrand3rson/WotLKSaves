package com.funprojects.wotlksaves.ui.adapters.recyclers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Andrei on 15.05.2018.
 */

public class BlacklistAdapter extends RecyclerView.Adapter<BlacklistAdapter.ViewHolder> {

    private Context mContext;
    private List<BlacklistRecord> mData;


    public BlacklistAdapter(Context context, List<BlacklistRecord> data) {
        this.mContext = context;
        this.mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_blacklist_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindDimen(R.dimen.activity_horizontal_margin)
        int horizontalMargin;

        @BindView(R.id.black_layout)
        RelativeLayout layoutView;

        @BindView(R.id.black_nickname)
        TextView nicknameView;

        @BindView(R.id.black_counter)
        TextView counterView;


        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(BlacklistRecord blacklistRecord) {
            this.setIsRecyclable(false);
            nicknameView.setText(blacklistRecord.getName());
            counterView.setText(String.valueOf(
                    blacklistRecord.getTimesCaught()
            ));

            //adding LinearLayout (in future with TextView reasons)
            LinearLayout reasonsLayout = prepareReasonsLayout();
            layoutView.addView(reasonsLayout);

            fillReasonsLayout(reasonsLayout, blacklistRecord.getReasons());
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
}

