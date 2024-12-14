package attendance.model;

import java.util.List;

public enum Date {
    HOLIDAY(List.of(1,7,8,14,15,21,22,25,28,29)),
    WEEKDAY(List.of(2,3,4,5,6,9,10,11,12,13,16,17,18,19,20,23,24,26,27,30,31));

    List<Integer> dates;

    Date(List<Integer> dates) {
        this.dates = dates;
    }

    public static boolean isHoliday(int date) {
        return HOLIDAY.dates.contains(date);
    }
}
