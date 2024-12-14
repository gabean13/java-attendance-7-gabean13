package attendance.service;

import attendance.constant.ServiceMessage;
import attendance.model.Attendance;
import attendance.model.MyClass;
import attendance.model.Record;
import camp.nextstep.edu.missionutils.DateTimes;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class ProcessAttendanceService {
    public Record recordAttendance(MyClass myClass, String nickNameInput, String timeInput) {
        Attendance attendances = myClass.getStudentAttendance(nickNameInput);
        LocalDateTime attendanceTime = getLocalDateTime(DateTimes.now().getDayOfMonth(), timeInput);
        Record record = attendances.addRecord(attendanceTime);
        return record;
    }

    private LocalDateTime getLocalDateTime(int date, String timeInput) {
        String[] split = timeInput.split(":");
        int hour = Integer.parseInt(split[0]);
        int min = Integer.parseInt(split[1]);
        return LocalDateTime.of(2024, 12, date, hour, min);
    }

    public String fixAttendance(MyClass myClass, String nickNameInput, String dateInput, String timeInput) {
        StringBuilder sb = new StringBuilder();
        int date = Integer.parseInt(dateInput);
        Attendance attendance = myClass.getStudentAttendance(nickNameInput);
        Record record = attendance.getRecord(date);
        LocalDateTime fixDateTime = getLocalDateTime(date, timeInput);
        if(record == null) {
            sb.append(MessageFormat.format(ServiceMessage.RECORD_INFO, date, fixDateTime.getDayOfWeek().getDisplayName(
                    TextStyle.NARROW, Locale.KOREAN), "--", "--" ,"결석"));
        } else {
            sb.append(record);
        }

        sb.append(" -> ");

        sb.append(attendance.fixAttendance(fixDateTime));
        sb.append(" 수정 완료!");
        return sb.toString();
    }

    public Attendance getNickNameAttendanceResult(MyClass myClass, String nickNameInput) {
        return myClass.getStudentAttendance(nickNameInput);
    }
}
