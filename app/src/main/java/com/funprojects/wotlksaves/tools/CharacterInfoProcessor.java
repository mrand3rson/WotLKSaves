package com.funprojects.wotlksaves.tools;

import android.content.Context;
import android.content.res.TypedArray;

import com.funprojects.wotlksaves.R;

/**
 * Created by Andrei on 04.05.2018.
 */

public class CharacterInfoProcessor {
    private final static int FIRST_SPEC = 0;
    private final static int SECOND_SPEC = 1;
    private final static int THIRD_SPEC = 2;

    private final static int MAGE = 0;
    private final static int WARLOCK = 1;
    private final static int PRIEST = 2;
    private final static int ROGUE = 3;
    private final static int DRUID = 4;
    private final static int SHAMAN = 5;
    private final static int HUNTER = 6;
    private final static int DARK_KNIGHT = 7;
    private final static int PALADIN = 8;
    private final static int WARRIOR = 9;


    public static String getSpecFromCode(Context context, int classCode, int specCode, boolean pvp) {
        switch (classCode) {
            case MAGE: {
                switch (specCode) {
                    case FIRST_SPEC: {
                        return context.getString(R.string.spec_mage_1);
                    }
                    case SECOND_SPEC: {
                        break;
                    }
                    case THIRD_SPEC: {
                        break;
                    }
                }
            }
            case WARLOCK: {
                break;
            }
            case PRIEST: {
                break;
            }
            case ROGUE: {
                break;
            }
            case DRUID: {
                break;
            }
            case SHAMAN: {
                break;
            }
            case HUNTER: {
                break;
            }
            case DARK_KNIGHT: {
                break;
            }
            case PALADIN: {
                break;
            }
            case WARRIOR: {
                break;
            }
        }
        return "Hello";
    }

    public static int getClassColor(Context context, byte gameClass) {
        TypedArray array = context.getResources().obtainTypedArray(R.array.class_colors);
        int classColor = array.getColor(gameClass, 0);
        array.recycle();

        return classColor;
    }
}
