package attendance.model;

import attendance.constant.ServiceMessage;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Record {
    LocalDateTime dateTime;
    String recordName;

    public Record(LocalDateTime datetime) {
        this.dateTime = datetime;
        if (datetime == null) {
            this.recordName = "결석";
            return;
        }
        this.recordName = getRecordName(datetime, datetime.getDayOfWeek().getValue());
    }

    private String getRecordName(LocalDateTime datetime, int day) {
        if(day == 1) {
            return checkTime(datetime, 13);
        }

        return checkTime(datetime, 10);
    }

    private static String checkTime(LocalDateTime datetime, int hour) {
        LocalDateTime standard = LocalDateTime.of(2024, 12, datetime.getDayOfMonth(), hour, 00);
        long time = ChronoUnit.MINUTES.between(standard, datetime) % 60;
        if (datetime.isAfter(standard) && time > 30) {
            return "결석";
        }
        if (datetime.isAfter(standard) && time > 5) {
            return "지각";
        }
        return "출석";
    }

    public String getRecordName() {
        return recordName;
    }

    public boolean hasDateChecked(int date) {
        if(dateTime.getDayOfMonth() == date) {
            return true;
        }

        return false;
    }

    public boolean isSameDate(int date) {
        return dateTime.getDayOfMonth() == date;
    }

    public Record getSameDateRecord(int date) {
        if(dateTime.getDayOfMonth() == date) {
            return this;
        }
        return null;
    }

    public String getTime() {
        String hour = String.format("%02d", dateTime.getHour());
        String min = String.format("%02d", dateTime.getMinute());
        return hour + ":" + min + " ";
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return MessageFormat.format(ServiceMessage.RECORD_INFO, String.format("%02d", dateTime.getDayOfMonth()), dateTime.getDayOfWeek().getDisplayName(
                TextStyle.NARROW, Locale.KOREAN), String.format("%02d", dateTime.getHour()), String.format("%02d", dateTime.getMinute()), recordName);
    }
}
