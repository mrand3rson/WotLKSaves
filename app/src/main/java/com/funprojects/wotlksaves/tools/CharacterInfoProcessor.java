package com.funprojects.wotlksaves.tools;

import android.content.Context;
import android.content.res.TypedArray;

import com.funprojects.wotlksaves.R;

import java.util.ArrayList;

import static com.funprojects.wotlksaves.tools.CharacterConstraints.BLOOD_ELF;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.DARK_KNIGHT;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.DRAENEI;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.DRUID;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.DWARF;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.FIRST_SPEC;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.GNOME;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.HUMAN;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.HUNTER;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.MAGE;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.NIGHT_ELF;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.ORC;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.PALADIN;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.PRIEST;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.ROGUE;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.ROLE_DPS;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.ROLE_HEALING;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.ROLE_PVP;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.ROLE_RDPS;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.ROLE_TANK;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.SECOND_SPEC;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.SHAMAN;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.TAUREN;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.THIRD_SPEC;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.TROLL;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.UNDEAD;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.WARLOCK;
import static com.funprojects.wotlksaves.tools.CharacterConstraints.WARRIOR;

/**
 * Created by Andrei on 04.05.2018.
 */

public class CharacterInfoProcessor {

    public static String getSpecFromCode(Context context, int classCode, int specCode, int role) {
        String suffix;
        switch (role) {
            case ROLE_TANK: {
                suffix = "pve tank";
                break;
            }
            case ROLE_HEALING: {
                suffix = "pve heal";
                break;
            }
            case ROLE_RDPS: {
                suffix = "pve rdps";
                break;
            }
            case ROLE_PVP: {
                suffix = "pvp";
                break;
            }
            case ROLE_DPS: {
                suffix = "pve dps";
                break;
            }
            default: suffix = "";
        }


        switch (classCode) {
            case MAGE: {
                switch (specCode) {
                    case FIRST_SPEC: {
                        return context.getString(R.string.spec_mage_1, suffix);
                    }
                    case SECOND_SPEC: {
                        return context.getString(R.string.spec_mage_2, suffix);
                    }
                    case THIRD_SPEC: {
                        return context.getString(R.string.spec_mage_3, suffix);
                    }
                }
            }
            case WARLOCK: {
                switch (specCode) {
                    case FIRST_SPEC: {
                        return context.getString(R.string.spec_warlock_1, suffix);
                    }
                    case SECOND_SPEC: {
                        return context.getString(R.string.spec_warlock_2, suffix);
                    }
                    case THIRD_SPEC: {
                        return context.getString(R.string.spec_warlock_3, suffix);
                    }
                }
            }
            case PRIEST: {
                switch (specCode) {
                    case FIRST_SPEC: {
                        return context.getString(R.string.spec_priest_1, suffix);
                    }
                    case SECOND_SPEC: {
                        return context.getString(R.string.spec_priest_2, suffix);
                    }
                    case THIRD_SPEC: {
                        return context.getString(R.string.spec_priest_3, suffix);
                    }
                }
            }
            case ROGUE: {
                switch (specCode) {
                    case FIRST_SPEC: {
                        return context.getString(R.string.spec_rogue_1, suffix);
                    }
                    case SECOND_SPEC: {
                        return context.getString(R.string.spec_rogue_2, suffix);
                    }
                    case THIRD_SPEC: {
                        return context.getString(R.string.spec_rogue_3, suffix);
                    }
                }
            }
            case DRUID: {
                switch (specCode) {
                    case FIRST_SPEC: {
                        return context.getString(R.string.spec_druid_1, suffix);
                    }
                    case SECOND_SPEC: {
                        return context.getString(R.string.spec_druid_2, suffix);
                    }
                    case THIRD_SPEC: {
                        return context.getString(R.string.spec_druid_3, suffix);
                    }
                }
            }
            case SHAMAN: {
                switch (specCode) {
                    case FIRST_SPEC: {
                        return context.getString(R.string.spec_shaman_1, suffix);
                    }
                    case SECOND_SPEC: {
                        return context.getString(R.string.spec_shaman_2, suffix);
                    }
                    case THIRD_SPEC: {
                        return context.getString(R.string.spec_shaman_3, suffix);
                    }
                }
            }
            case HUNTER: {
                switch (specCode) {
                    case FIRST_SPEC: {
                        return context.getString(R.string.spec_hunter_1, suffix);
                    }
                    case SECOND_SPEC: {
                        return context.getString(R.string.spec_hunter_2, suffix);
                    }
                    case THIRD_SPEC: {
                        return context.getString(R.string.spec_hunter_3, suffix);
                    }
                }
            }
            case DARK_KNIGHT: {
                switch (specCode) {
                    case FIRST_SPEC: {
                        return context.getString(R.string.spec_darkknight_1, suffix);
                    }
                    case SECOND_SPEC: {
                        return context.getString(R.string.spec_darkknight_2, suffix);
                    }
                    case THIRD_SPEC: {
                        return context.getString(R.string.spec_darkknight_3, suffix);
                    }
                }
            }
            case PALADIN: {
                switch (specCode) {
                    case FIRST_SPEC: {
                        return context.getString(R.string.spec_paladin_1, suffix);
                    }
                    case SECOND_SPEC: {
                        return context.getString(R.string.spec_paladin_2, suffix);
                    }
                    case THIRD_SPEC: {
                        return context.getString(R.string.spec_paladin_3, suffix);
                    }
                }
            }
            case WARRIOR: {
                switch (specCode) {
                    case FIRST_SPEC: {
                        return context.getString(R.string.spec_warrior_1, suffix);
                    }
                    case SECOND_SPEC: {
                        return context.getString(R.string.spec_warrior_2, suffix);
                    }
                    case THIRD_SPEC: {
                        return context.getString(R.string.spec_warrior_3, suffix);
                    }
                }
            }
        }
        return context.getString(R.string.no_spec);
    }

    public static String getRaceFromCode(Context context, int raceCode) {
        switch (raceCode) {
            case HUMAN: {
                return context.getString(R.string.race_human);
            }
            case DWARF: {
                return context.getString(R.string.race_dwarf);
            }
            case NIGHT_ELF: {
                return context.getString(R.string.race_nightelf);
            }
            case GNOME: {
                return context.getString(R.string.race_gnome);
            }
            case DRAENEI: {
                return context.getString(R.string.race_draenei);
            }
            case ORC: {
                return context.getString(R.string.race_orc);
            }
            case UNDEAD: {
                return context.getString(R.string.race_undead);
            }
            case TAUREN: {
                return context.getString(R.string.race_tauren);
            }
            case TROLL: {
                return context.getString(R.string.race_troll);
            }
            case BLOOD_ELF: {
                return context.getString(R.string.race_bloodelf);
            }
        }
        return null;
    }

    public static String getClassFromCode(Context context, int classCode) {
        switch (classCode) {
            case MAGE: {
                return context.getString(R.string.class_mage);
            }
            case WARLOCK: {
                return context.getString(R.string.class_warlock);
            }
            case PRIEST: {
                return context.getString(R.string.class_priest);
            }
            case ROGUE: {
                return context.getString(R.string.class_rogue);
            }
            case DRUID: {
                return context.getString(R.string.class_druid);
            }
            case SHAMAN: {
                return context.getString(R.string.class_shaman);
            }
            case HUNTER: {
                return context.getString(R.string.class_hunter);
            }
            case DARK_KNIGHT: {
                return context.getString(R.string.class_darkknight);
            }
            case PALADIN: {
                return context.getString(R.string.class_paladin);
            }
            case WARRIOR: {
                return context.getString(R.string.class_warrior);
            }
        }

        return null;
    }

    public static int getClassColor(Context context, byte gameClass) {
        TypedArray array = context.getResources().obtainTypedArray(R.array.class_colors);
        int classColor = array.getColor(gameClass, 0);
        array.recycle();

        return classColor;
    }
}
