package com.funprojects.wotlksaves.ui.dialogs;

/**
 * Created by Andrei on 22.05.2018.
 */

public class SortTypes {
    public final static String ARG_SORT_TYPE = "type";
    public final static int REQUEST_SORT_TYPE = 1;
    public final static int SORT_BY_NAME_ASC = 1;
    public final static int SORT_BY_NAME_DESC = 2;
    public final static int SORT_BY_TIMES_SEEN = 5;
    public final static int SORT_BY_DATE_ASC = 3;
    public final static int SORT_BY_DATE_DESC = 4;


    public static String getSortName(int trainingType) {
        String result;
        switch (trainingType) {
            case SORT_BY_NAME_ASC: {
                result = "By Name Ascending";
                break;
            }
            case SORT_BY_NAME_DESC: {
                result = "By Name Descending";
                break;
            }
            case SORT_BY_DATE_ASC: {
                result = "By Adding Date Ascending";
                break;
            }
            case SORT_BY_DATE_DESC: {
                result = "By Adding Date Descending";
                break;
            }
            case SORT_BY_TIMES_SEEN: {
                result = "By Times Seen";
                break;
            }
            default: {
                result = "Undefined";
            }
        }
        return result;
    }

    public static String[] getAllSortNames() {
        return new String[] {getSortName(SORT_BY_NAME_ASC),
                getSortName(SORT_BY_NAME_DESC),
                getSortName(SORT_BY_DATE_ASC),
                getSortName(SORT_BY_DATE_DESC),
                getSortName(SORT_BY_TIMES_SEEN)
        };
    }
}
