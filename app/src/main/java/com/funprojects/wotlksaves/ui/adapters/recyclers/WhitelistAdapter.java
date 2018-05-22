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
import com.funprojects.wotlksaves.mvp.models.WhitelistRecord;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Andrei on 21.05.2018.
 */

public class WhitelistAdapter extends RecyclerView.Adapter<WhitelistAdapter.ViewHolder> {

    private Context mContext;
    private List<WhitelistRecord> mData;


    public WhitelistAdapter(Context context, List<WhitelistRecord> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_whitelist_item, parent, false);
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

        @BindView(R.id.white_layout)
        RelativeLayout layoutView;

        @BindView(R.id.white_nickname)
        TextView nicknameView;

        @BindView(R.id.white_seen)
        Button seenButton;


        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(WhitelistRecord whitelistRecord) {
            this.setIsRecyclable(false);
            nicknameView.setText(whitelistRecord.getName());
            seenButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: open dialog with "whereSeen" board
                    Toast.makeText(mContext, "OOPS", Toast.LENGTH_SHORT).show();
                }
            });

            LinearLayout reasonsLayout = prepareReasonsLayout();
            layoutView.addView(reasonsLayout);

            fillReasonsLayout(reasonsLayout, whitelistRecord.getReasons());
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
