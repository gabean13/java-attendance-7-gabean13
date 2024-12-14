package attendance.view;

import attendance.constant.ErrorMessage;
import attendance.constant.ServiceMessage;
import attendance.model.Attendance;
import attendance.model.Date;
import attendance.model.MyClass;
import attendance.model.Record;
import camp.nextstep.edu.missionutils.DateTimes;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {
    public void printOrder() {
        StringBuilder sb = new StringBuilder();

        LocalDateTime now = DateTimes.now();
        sb.append("\n");
        sb.append(MessageFormat.format(ServiceMessage.TODAY_DATE, now.getDayOfMonth(), now.getDayOfWeek().getDisplayName(
                        TextStyle.NARROW, Locale.KOREAN)));
        sb.append(ServiceMessage.ORDER);
        print(sb.toString());
    }

    private void print(String message) {
        System.out.println(message);
    }

    public void printError(String message) {
        print(ErrorMessage.ERROR_PREFIX + message);
    }

    public void printGetNickName() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(ServiceMessage.RECORD_NICKNAME);
        print(sb.toString());
    }

    public void printGetTime() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(ServiceMessage.RECORD_TIME);
        print(sb.toString());
    }

    public void printAttendanceResult(Record record) {
        print(record.toString());
    }

    public void printGetDate() {
        print(ServiceMessage.FIX_DATE);
    }

    public void printGetFixNickName() {
        print(ServiceMessage.FIX_NICKNAME);
    }

    public void printGetFixTime() {
        print(ServiceMessage.FIX_TIME);
    }

    public void printFixResult(String result) {
        print(result);
    }

    public void printNickNameAttendanceResult(String name, Attendance attendance) {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageFormat.format(ServiceMessage.PRINT_RECORD_START, name));
        List<Record> records = attendance.getRecords();

        int nowDate = DateTimes.now().getDayOfMonth();
        for(int i = 1; i < nowDate; i++) {
            LocalDateTime date = LocalDate.of(2024,12, i).atStartOfDay();
            if(Date.isHoliday(i)) {
                continue;
            }
            boolean isExist = false;
            for(Record record : records) {
                if(record.isSameDate(i)) {
                    sb.append(record);
                    isExist = true;
                    break;
                }
            }
            if(!isExist) {
                sb.append(MessageFormat.format(ServiceMessage.RECORD_INFO, i, date.getDayOfWeek().getDisplayName(
                        TextStyle.NARROW, Locale.KOREAN), "--", "--" , "결석"));
            }
            sb.append("\n");
        }

        sb.append(attendance.getTotalRecord());
        sb.append(attendance.getInterviewNeeded());
        print(sb.toString());
    }

    public void printCaution(MyClass myClass) {

    }
}
