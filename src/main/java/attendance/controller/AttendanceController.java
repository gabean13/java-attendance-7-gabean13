package attendance.controller;

import attendance.constant.ErrorMessage;
import attendance.constant.ServiceMessage;
import attendance.model.Attendance;
import attendance.model.Date;
import attendance.model.MyClass;
import attendance.model.Record;
import attendance.service.ProcessAttendanceService;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final MyClass myClass;
    private final ProcessAttendanceService processAttendanceService;

    public AttendanceController(InputView inputView, OutputView outputView, MyClass myClass,
                                ProcessAttendanceService processAttendanceService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.myClass = myClass;
        this.processAttendanceService = processAttendanceService;
    }

    public void run() {
        //1. 오더를 받는다
        while(true) {
            try {
                outputView.printOrder();
                String input = inputView.getOrder();
                if(input.equals("Q")) return;
                //2. 오더를 처리한다
                processOrder(input);
            } catch (IllegalArgumentException ex) {
                outputView.printError(ex.getMessage());
                throw new IllegalArgumentException(ErrorMessage.ERROR_PREFIX + ex.getMessage());
            }
        }
    }

    private void processOrder(String input) {
        LocalDateTime now = DateTimes.now();
        //출석 확인
        if(input.equals("1")) {
            if(Date.isHoliday(now.getDayOfMonth())) {
                throw new IllegalArgumentException(MessageFormat.format(ErrorMessage.HOLIDAY_ERROR, now.getDayOfMonth(), now.getDayOfWeek().getDisplayName(
                        TextStyle.NARROW, Locale.KOREAN)));
            }
            recordAttendance();
            return;
        }
        //출석 수정
        if(input.equals("2")) {
            fixAttendance();
            return;
        }
        //크루별 출석 확인
        if(input.equals("3")) {
            checkAttendanceRecord();
            return;
        }
        //재적 위험 확인
        if(input.equals("4")) {
            checkRegisteredDangerList();
        }
    }

    private void recordAttendance() {
        outputView.printGetNickName();
        String nickNameInput = inputView.getNickNameInput(myClass);
        outputView.printGetTime();
        String timeInput = inputView.getAttendanceTimeInput(myClass, nickNameInput);
        Record record = processAttendanceService.recordAttendance(myClass, nickNameInput, timeInput);
        outputView.printAttendanceResult(record);
    }

    private void fixAttendance() {
        outputView.printGetFixNickName();
        String nickNameInput = inputView.getFixNickNameInput(myClass);
        outputView.printGetDate();
        String date = inputView.getFixDateInput();
        outputView.printGetFixTime();
        String timeInput = inputView.getAttendanceTimeInput(myClass, nickNameInput);
        String result = processAttendanceService.fixAttendance(myClass, nickNameInput, date, timeInput);
        outputView.printFixResult(result);
    }

    private void checkAttendanceRecord() {
        outputView.printGetNickName();
        String nickNameInput = inputView.getNickNameInput(myClass);
        Attendance attendance = processAttendanceService.getNickNameAttendanceResult(myClass, nickNameInput);
        outputView.printNickNameAttendanceResult(nickNameInput, attendance);
    }

    private void checkRegisteredDangerList() {
        outputView.printCaution(myClass);
    }


}
