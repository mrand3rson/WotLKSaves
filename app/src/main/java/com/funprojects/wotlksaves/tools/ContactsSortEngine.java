package com.funprojects.wotlksaves.tools;

import com.funprojects.wotlksaves.mvp.models.BlacklistRecord;
import com.funprojects.wotlksaves.mvp.models.ListRecord;

import java.util.Comparator;

/**
 * Created by Andrei on 27.05.2018.
 */

public class ContactsSortEngine {

    public static Comparator<ListRecord> getComparatorByType(int sortType) {
        Comparator<ListRecord> comparator = null;
        switch (sortType) {
            case SortTypes.SORT_BY_NAME_ASC: {
                comparator = buildAscNameComparator();
                break;
            }
            case SortTypes.SORT_BY_NAME_DESC: {
                comparator = buildDescNameComparator();
                break;
            }
            case SortTypes.SORT_BY_DATE_ASC: {
                comparator = buildAscIdComparator();
                break;
            }
            case SortTypes.SORT_BY_DATE_DESC: {
                comparator = buildDescIdComparator();
                break;
            }
            case SortTypes.SORT_BY_TIMES_SEEN: {
                comparator = buildTimesSeenComparator();
                break;
            }
        }
        return comparator;
    }

    private static Comparator<ListRecord> buildAscNameComparator() {
        return (r1, r2) -> r1.getName().toLowerCase()
                .compareTo(r2.getName().toLowerCase());
    }
    private static Comparator<ListRecord> buildDescNameComparator() {
        return (r1, r2) -> r2.getName().toLowerCase()
                .compareTo(r1.getName().toLowerCase());
    }
    private static Comparator<ListRecord> buildAscIdComparator() {
        return (r1, r2) -> Long.compare(r1.id, r2.id);
    }
    private static Comparator<ListRecord> buildDescIdComparator() {
        return (r1, r2) -> Long.compare(r2.id, r1.id);
    }
    private static Comparator<ListRecord> buildTimesSeenComparator() {
        return (r1, r2) -> {
            int i1 = r1.getTimesSeen();
            int i2 = r2.getTimesSeen();
            return Integer.compare(i1, i2);
        };
    }
}
