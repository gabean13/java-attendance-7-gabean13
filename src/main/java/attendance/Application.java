package attendance;

import attendance.controller.AttendanceController;

public class Application {
    private final AttendanceController attendanceController;

    public Application() {
        this.attendanceController = new ApplicationConfig().createAttendanceController();
    }

    public static void main(String[] args) {
        new Application().attendanceController.run();
    }
}
