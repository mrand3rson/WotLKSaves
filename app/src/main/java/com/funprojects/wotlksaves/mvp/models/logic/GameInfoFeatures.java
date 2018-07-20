package com.funprojects.wotlksaves.mvp.models.logic;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.funprojects.wotlksaves.tools.CharacterConstraints.*;
import static com.funprojects.wotlksaves.tools.CharacterInfoProcessor.getSpecFromCode;


/**
 * Created by Andrei on 31.05.2018.
 */

public class GameInfoFeatures {

    public static List<Integer> getRaceClassTable(int race) {
        switch (race) {
            case HUMAN: {
                return Arrays.asList(MAGE, WARLOCK, PRIEST, ROGUE, DARK_KNIGHT, PALADIN, WARRIOR);
            }
            case DWARF: {
                return Arrays.asList(PRIEST, ROGUE, HUNTER, DARK_KNIGHT, PALADIN, WARRIOR);
            }
            case NIGHT_ELF: {
                return Arrays.asList(PRIEST, ROGUE, DRUID, HUNTER, DARK_KNIGHT, WARRIOR);
            }
            case GNOME: {
                return Arrays.asList(MAGE, WARLOCK, ROGUE, DARK_KNIGHT, WARRIOR);
            }
            case DRAENEI: {
                return Arrays.asList(MAGE, PRIEST, SHAMAN, HUNTER, DARK_KNIGHT, PALADIN, WARRIOR);
            }
            case ORC: {
                return Arrays.asList(WARLOCK, ROGUE, SHAMAN, HUNTER, DARK_KNIGHT, WARRIOR);
            }
            case UNDEAD: {
                return Arrays.asList(MAGE, WARLOCK, PRIEST, ROGUE, DARK_KNIGHT, WARRIOR);
            }
            case TAUREN: {
                return Arrays.asList(DRUID, SHAMAN, HUNTER, DARK_KNIGHT, WARRIOR);
            }
            case TROLL: {
                return Arrays.asList(MAGE, PRIEST, ROGUE, SHAMAN, HUNTER, DARK_KNIGHT, WARRIOR);
            }
            case BLOOD_ELF: {
                return Arrays.asList(MAGE, WARLOCK, PRIEST, ROGUE, HUNTER, DARK_KNIGHT, PALADIN);
            }
            default: {
                return null;
            }
        }
    }

    public static List<String> getAllSpecs(Context context, int classCode) {
        ArrayList<String> specs = new ArrayList<>();
        specs.add(getSpecFromCode(context, classCode, NO_SPEC, ROLE_NO));
        switch (classCode) {
            case MAGE: {
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_RDPS));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_RDPS));
                specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_RDPS));
                break;
            }
            case WARLOCK: {
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_RDPS));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_RDPS));
                specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_RDPS));
                break;
            }
            case PRIEST: {
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_HEALING));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_HEALING));
                specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_RDPS));
                break;
            }
            case ROGUE: {
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_DPS));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_DPS));
                specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_DPS));
                break;
            }
            case DRUID: {
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_RDPS));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_DPS));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_TANK));
                specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_HEALING));
                break;
            }
            case SHAMAN: {
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_RDPS));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_DPS));
                specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_HEALING));
                break;
            }
            case HUNTER: {
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_RDPS));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_RDPS));
                specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_RDPS));
                break;
            }
            case DARK_KNIGHT: {
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_TANK));
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_DPS));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_TANK));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_DPS));
                specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_TANK));
                specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_DPS));
                break;
            }
            case PALADIN: {
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_HEALING));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_TANK));
                specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_DPS));
                break;
            }
            case WARRIOR: {
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_DPS));
                specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_TANK));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_DPS));
                specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_TANK));
                specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_TANK));
                break;
            }
        }
        specs.add(getSpecFromCode(context, classCode, FIRST_SPEC, ROLE_PVP));
        specs.add(getSpecFromCode(context, classCode, SECOND_SPEC, ROLE_PVP));
        specs.add(getSpecFromCode(context, classCode, THIRD_SPEC, ROLE_PVP));

        return specs;
    }
}
