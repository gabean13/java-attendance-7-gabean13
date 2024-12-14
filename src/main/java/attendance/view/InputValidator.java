package attendance.view;

import attendance.constant.ErrorMessage;
import attendance.model.Date;
import attendance.model.MyClass;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InputValidator {
    public void checkGetOrder(String input) {
        String patterns = "^(1|2|3|4|Q)$";
        if (!input.matches(patterns)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT);
        }
    }

    public void checkNickNameInput(String input, MyClass myClass) {
        //이미 체크했는데 또 체크
        if (myClass.isAlreadyAttendant(input, DateTimes.now())) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_ATTENDANCE);
        }

        if (!myClass.isNickNameExist(input)) {
            throw new IllegalArgumentException(ErrorMessage.NON_EXIST_NAME);
        }
    }

    public void checkAttendanceTime(MyClass myClass, String input, String name) {
        //0. 형식 오류
        String[] split = input.split(":");
        int hour = 0, min = 0;
        try {
            hour = Integer.parseInt(split[0]);
            min = Integer.parseInt(split[1]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT);
        }

        if (hour < 0 || hour > 23 || min < 0 || min > 59) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT);
        }

        LocalDateTime now = DateTimes.now();
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, now.getDayOfMonth(), hour, min);
        LocalDateTime openTime = LocalDateTime.of(2024, 12, now.getDayOfMonth(), 8, 00);
        LocalDateTime closedTime = LocalDateTime.of(2024, 12, now.getDayOfMonth(), 23, 00);
        //1. 영업 시간 외의 방문
        if (attendanceTime.isBefore(openTime) || attendanceTime.isAfter(closedTime)) {
            throw new IllegalArgumentException(ErrorMessage.CLASS_CLOSED);
        }
        //2. 미래 시간 방문

    }

    public void checkFixDateInput(String input) {
        int date = 0;
        try {
            date = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT);
        }

        if (date < 1 || date > 31) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_FORMAT);
        }
        if (Date.isHoliday(date)) {
            throw new IllegalArgumentException(ErrorMessage.HOLIDAY_ERROR);
        }

        LocalDateTime now = DateTimes.now();
        LocalDateTime attendanceDate = LocalDate.of(2024, 12, date).atStartOfDay();
        if (attendanceDate.isAfter(now)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_TIME);
        }
    }

    public void checkFixNickNameInput(MyClass myClass, String input) {
        if(!myClass.isNickNameExist(input)) {
            throw new IllegalArgumentException(ErrorMessage.NON_EXIST_NAME);
        }
    }
}
