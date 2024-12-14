package attendance;

import attendance.controller.AttendanceController;
import attendance.model.MyClass;
import attendance.service.FileService;
import attendance.service.ProcessAttendanceService;
import attendance.view.InputValidator;
import attendance.view.InputView;
import attendance.view.OutputView;

public class ApplicationConfig {
    public AttendanceController createAttendanceController() {
        OutputView outputView = new OutputView();
        InputValidator inputValidator = new InputValidator();
        InputView inputView = new InputView(inputValidator);
        FileService fileService = new FileService();
        MyClass myClass = new MyClass(fileService);
        ProcessAttendanceService processAttendanceService = new ProcessAttendanceService();
        return new AttendanceController(inputView, outputView, myClass, processAttendanceService);
    }
}
