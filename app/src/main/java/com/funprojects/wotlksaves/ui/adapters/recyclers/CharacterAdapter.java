package com.funprojects.wotlksaves.ui.adapters.recyclers;

import android.content.Context;
import android.graphics.Typeface;
import android.support.transition.TransitionManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.funprojects.wotlksaves.R;
import com.funprojects.wotlksaves.mvp.models.Account;
import com.funprojects.wotlksaves.mvp.models.GameCharacter;
import com.funprojects.wotlksaves.tools.CharacterInfoProcessor;
import com.funprojects.wotlksaves.ui.activities.MainActivity;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by Andrei on 04.05.2018.
 */

public class CharacterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public RealmList<Account> getData() {
        return mData;
    }

    private final static int TYPE_DATA = 1;
    private final static int TYPE_ADD = 2;

    private final int mResource;
    private final RealmList<Account> mData;
    private final Context mContext;
    private final RecyclerView recyclerView;
    private ArrayList<DataViewHolder> holders;


    public CharacterAdapter(Context context, int mResource, RealmList<Account> mData, RecyclerView view) {
        this.mContext = context;
        this.mResource = mResource;
        this.mData = mData;
        this.holders = new ArrayList<>(mData.size());
        this.recyclerView = view;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_DATA;//(position < mData.size()? TYPE_DATA: TYPE_ADD);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_DATA: {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(mResource, parent, false);
                DataViewHolder dvh = new DataViewHolder(v);
                holders.add(dvh);
                return dvh;
            }
//            case TYPE_ADD: {
//                View v = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.recycler_item_add, parent, false);
//                return new AddViewHolder(v);
//            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_DATA: {
                ((DataViewHolder)holder).bind(mData.get(position));
                break;
            }
//            case TYPE_ADD: {
//                ((AddViewHolder)holder).bind();
//            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();//+1;
    }

    public void addCharacter(GameCharacter character) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Account account = realm.where(Account.class)
                .equalTo("id", character.getAccountId())
                .findFirst();
        int accountIndex = mData.indexOf(account);
        if (accountIndex > -1 && holders.size() > 0) {
            mData.get(accountIndex).getCharacters().add(character);
            holders.get(accountIndex).addCharacterToView(character);
        } else {
            account.getCharacters().add(character);
            mData.add(account);
            notifyItemInserted(mData.size());
        }

        realm.commitTransaction();
    }


    class DataViewHolder extends RecyclerView.ViewHolder {

        @BindDimen(R.dimen.characters_card_width)
        int cardWidth;

        @BindDimen(R.dimen.characters_card_height)
        int cardHeight;

        @BindColor(R.color.colorRecyclerCharsItemBackground)
        int cardColor;

        @BindColor(R.color.color_recycler_chars_alliance_background)
        int colorAlliance;

        @BindColor(R.color.color_recycler_chars_horde_background)
        int colorHorde;

        @BindView(R.id.account_header)
        TextView mHeader;

        @BindView(R.id.characters_layout)
        GridLayout mDetails;
        View itemView;

        boolean isExpanded = true;


        DataViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        void bind(Account account) {
            if (mDetails.getChildCount() < 1) {
                fillAccountLayout(account);
            }
            mHeader.setText(account.getName());
            mHeader.setTextColor(mContext.getResources().getColor(
                    isExpanded? android.R.color.black: android.R.color.white
            ));
            mHeader.setOnClickListener(v -> {
                isExpanded = !isExpanded;
                itemView.setActivated(isExpanded);
                mDetails.setVisibility(isExpanded? View.VISIBLE: View.GONE);
                mHeader.setTextColor(mContext.getResources().getColor(
                        isExpanded? android.R.color.black: android.R.color.white
                ));
                if (isExpanded && mDetails.getChildCount() < 1) {
                    fillAccountLayout(account);
                    notifyDataSetChanged();
                }
                TransitionManager.beginDelayedTransition(recyclerView);
            });
        }

        private void fillAccountLayout(Account account) {
            mDetails.setColumnCount(2);
            mDetails.removeAllViews();
            for (GameCharacter character : account.getCharacters()) {
                addCharacterToView(character);
            }
        }

        void addCharacterToView(GameCharacter character) {
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    cardWidth,
                    cardHeight);
            cardParams.setMargins(8, 8, 8, 8);
            CardView card = new CardView(mContext);
            card.setLayoutParams(cardParams);
            //A.S. replaced with level color
            card.setCardBackgroundColor(cardColor);
            card.setRadius(80);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout cardLayout = new LinearLayout(mContext);
            cardLayout.setLayoutParams(layoutParams);
            cardLayout.setGravity(Gravity.CENTER);
            cardLayout.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams levelNicknameParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);


            TextView levelView = new TextView(mContext);
            levelView.setLayoutParams(levelNicknameParams);
            levelView.setText(String.valueOf(character.getLevel()));
            levelView.setTextColor(character.isHordeFaction()? colorHorde : colorAlliance);
            levelView.setTextSize(36);

            TextView nicknameView = new TextView(mContext);
            nicknameView.setLayoutParams(levelNicknameParams);
            nicknameView.setText(character.getNickname());
            nicknameView.setShadowLayer(0.5f, 0.1f, 0.1f,
                    mContext.getResources().getColor(android.R.color.black));
            nicknameView.setTextColor(CharacterInfoProcessor.getClassColor(mContext, character.getGameClass()));
            nicknameView.setTextSize(20);
            nicknameView.setTypeface(null, Typeface.BOLD);

            //adding views to layouts
            mDetails.addView(card);
            card.addView(cardLayout);
            cardLayout.addView(levelView);
            cardLayout.addView(nicknameView);

            card.setOnClickListener(view -> Toast.makeText(mContext, "Add logic", Toast.LENGTH_SHORT).show());
        }
    }
    //TODO: safe delete class
    class AddViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card)
        CardView card;

        @BindView(R.id.image)
        ImageView mImageView;


        AddViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind() {
            card.setOnClickListener(view -> ((MainActivity)mContext).openAddCharacterDialog());
        }
    }
}
