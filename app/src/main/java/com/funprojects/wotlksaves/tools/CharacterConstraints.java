package com.funprojects.wotlksaves.tools;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.funprojects.wotlksaves.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Andrei on 31.05.2018.
 */

public class CharacterConstraints {
    public final static int HUMAN = 0;
    public final static int DWARF = 1;
    public final static int NIGHT_ELF = 2;
    public final static int GNOME = 3;
    public final static int DRAENEI = 4;
    public final static int ORC = 5;
    public final static int UNDEAD = 6;
    public final static int TAUREN = 7;
    public final static int TROLL = 8;
    public final static int BLOOD_ELF = 9;

    public final static int MAGE = 0;
    public final static int WARLOCK = 1;
    public final static int PRIEST = 2;
    public final static int ROGUE = 3;
    public final static int DRUID = 4;
    public final static int SHAMAN = 5;
    public final static int HUNTER = 6;
    public final static int DARK_KNIGHT = 7;
    public final static int PALADIN = 8;
    public final static int WARRIOR = 9;

    public final static int NO_SPEC = -1;
    public final static int FIRST_SPEC = 0;
    public final static int SECOND_SPEC = 1;
    public final static int THIRD_SPEC = 2;

    public final static int ROLE_NO = -1;
    public final static int ROLE_PVP = 0;
    public final static int ROLE_TANK = 1;
    public final static int ROLE_HEALING = 2;
    public final static int ROLE_DPS = 3;
    public final static int ROLE_RDPS = 4;

    public final static boolean FACTION_ALLIANCE = false;
    public final static boolean FACTION_HORDE = true;


    public static byte parseRaceToCode(Context context, String race) {
        if (race.equals(context.getString(R.string.race_human))) {
            return HUMAN;
        } else if (race.equals(context.getString(R.string.race_dwarf))) {
            return DWARF;
        } else if (race.equals(context.getString(R.string.race_nightelf))) {
            return NIGHT_ELF;
        } else if (race.equals(context.getString(R.string.race_gnome))) {
            return GNOME;
        } else if (race.equals(context.getString(R.string.race_draenei))) {
            return DRAENEI;
        } else if (race.equals(context.getString(R.string.race_orc))) {
            return ORC;
        } else if (race.equals(context.getString(R.string.race_undead))) {
            return UNDEAD;
        } else if (race.equals(context.getString(R.string.race_tauren))) {
            return TAUREN;
        } else if (race.equals(context.getString(R.string.race_troll))) {
            return TROLL;
        } else if (race.equals(context.getString(R.string.race_bloodelf))) {
            return BLOOD_ELF;
        }

        return -1;
    }

    public static byte parseClassToCode(Context context, String classString) {
        if (classString.equals(context.getString(R.string.class_mage))) {
            return MAGE;
        } else if (classString.equals(context.getString(R.string.class_warlock))) {
            return WARLOCK;
        } else if (classString.equals(context.getString(R.string.class_priest))) {
            return PRIEST;
        } else if (classString.equals(context.getString(R.string.class_rogue))) {
            return ROGUE;
        } else if (classString.equals(context.getString(R.string.class_druid))) {
            return DRUID;
        } else if (classString.equals(context.getString(R.string.class_shaman))) {
            return SHAMAN;
        } else if (classString.equals(context.getString(R.string.class_hunter))) {
            return HUNTER;
        } else if (classString.equals(context.getString(R.string.class_darkknight))) {
            return DARK_KNIGHT;
        } else if (classString.equals(context.getString(R.string.class_paladin))) {
            return PALADIN;
        } else if (classString.equals(context.getString(R.string.class_warrior))) {
            return WARRIOR;
        }

        return -1;
    }

    public static byte parseSpecToCode(Context context, int classCode, String spec) {
        switch (classCode) {
            case MAGE: {
                String specBase = spec.split(" ")[0];
                if (specBase.equals(context.getString(R.string.spec_mage_1).split(" ")[0])) {
                    return FIRST_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_mage_2).split(" ")[0])) {
                    return SECOND_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_mage_3).split(" ")[0])) {
                    return THIRD_SPEC;
                }
            }
            case WARLOCK: {
                String specBase = spec.split(" ")[0];
                if (specBase.equals(context.getString(R.string.spec_warlock_1).split(" ")[0])) {
                    return FIRST_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_warlock_2).split(" ")[0])) {
                    return SECOND_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_warlock_3).split(" ")[0])) {
                    return THIRD_SPEC;
                }
            }
            case PRIEST: {
                String specBase = spec.split(" ")[0];
                if (specBase.equals(context.getString(R.string.spec_priest_1).split(" ")[0])) {
                    return FIRST_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_priest_2).split(" ")[0])) {
                    return SECOND_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_priest_3).split(" ")[0])) {
                    return THIRD_SPEC;
                }
            }
            case ROGUE: {
                String specBase = spec.split(" ")[0];
                if (specBase.equals(context.getString(R.string.spec_rogue_1).split(" ")[0])) {
                    return FIRST_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_rogue_2).split(" ")[0])) {
                    return SECOND_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_rogue_3).split(" ")[0])) {
                    return THIRD_SPEC;
                }
            }
            case DRUID: {
                String specBase = spec.split(" ")[0];
                if (specBase.equals(context.getString(R.string.spec_druid_1).split(" ")[0])) {
                    return FIRST_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_druid_2).split(" ")[0])) {
                    return SECOND_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_druid_3).split(" ")[0])) {
                    return THIRD_SPEC;
                }
            }
            case SHAMAN: {
                String specBase = spec.split(" ")[0];
                if (specBase.equals(context.getString(R.string.spec_shaman_1).split(" ")[0])) {
                    return FIRST_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_shaman_2).split(" ")[0])) {
                    return SECOND_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_shaman_3).split(" ")[0])) {
                    return THIRD_SPEC;
                }
            }
            case HUNTER: {
                String specBase = spec.split(" ")[0];
                if (specBase.equals(context.getString(R.string.spec_hunter_1).split(" ")[0])) {
                    return FIRST_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_hunter_2).split(" ")[0])) {
                    return SECOND_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_hunter_3).split(" ")[0])) {
                    return THIRD_SPEC;
                }
            }
            case DARK_KNIGHT: {
                String specBase = spec.split(" ")[0];
                if (specBase.equals(context.getString(R.string.spec_darkknight_1).split(" ")[0])) {
                    return FIRST_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_darkknight_2).split(" ")[0])) {
                    return SECOND_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_darkknight_3).split(" ")[0])) {
                    return THIRD_SPEC;
                }
            }
            case PALADIN: {
                String specBase = spec.split(" ")[0];
                if (specBase.equals(context.getString(R.string.spec_paladin_1).split(" ")[0])) {
                    return FIRST_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_paladin_2).split(" ")[0])) {
                    return SECOND_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_paladin_3).split(" ")[0])) {
                    return THIRD_SPEC;
                }
            }
            case WARRIOR: {
                String specBase = spec.split(" ")[0];
                if (specBase.equals(context.getString(R.string.spec_warrior_1).split(" ")[0])) {
                    return FIRST_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_warrior_2).split(" ")[0])) {
                    return SECOND_SPEC;
                } else if (specBase.equals(context.getString(R.string.spec_warrior_3).split(" ")[0])) {
                    return THIRD_SPEC;
                }
            }
        }
        return NO_SPEC;
    }
}
