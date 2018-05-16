package com.funprojects.wotlksaves.ui.adapters.recyclers;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.GameCharacter;
import com.funprojects.wotlksaves.tools.CharacterInfoProcessor;
import com.funprojects.wotlksaves.ui.activities.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Andrei on 04.05.2018.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {
    private final static int TYPE_DATA = 1;
    private final static int TYPE_ADD = 2;
    private final int mResource;
    private final List<GameCharacter> mData;
    private Context mContext;


    public CharacterAdapter(Context context, int mResource, List<GameCharacter> mData) {
        this.mContext = context;
        this.mResource = mResource;
        this.mData = mData;
    }

    @Override
    public int getItemViewType(int position) {

        return (position < mData.size()? TYPE_DATA: TYPE_ADD);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_DATA: {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(mResource, parent, false);
                return new DataViewHolder(v);
            }
            case TYPE_ADD: {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_item_add, parent, false);
                return new AddViewHolder(v);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_DATA: {
                ((DataViewHolder)holder).bind(mData.get(position));
                break;
            }
            case TYPE_ADD: {
                ((AddViewHolder)holder).bind();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }

    class DataViewHolder extends ViewHolder {

        @BindView(R.id.card)
        CardView card;

        @BindView(R.id.nickname)
        TextView mName;


        DataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(GameCharacter item) {
            mName.setText(item.getNickname());
            mName.setTextColor(CharacterInfoProcessor.getClassColor(mContext, item.getGameClass()));

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Add logic", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    class AddViewHolder extends ViewHolder {

        @BindView(R.id.card)
        CardView card;

        @BindView(R.id.image)
        ImageView mImageView;


        AddViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind() {
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)mContext).openAddCharacterDialog();
                }
            });
        }
    }
}
