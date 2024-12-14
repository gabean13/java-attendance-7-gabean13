package attendance.model;

import attendance.constant.ErrorMessage;
import attendance.service.FileService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyClass {
    private final FileService fileService;
    private Map<String, Attendance> classAttendance;

    public MyClass(FileService fileService) {
        this.fileService = fileService;
        this.classAttendance = saveAttendance();
        checkAbsent();
    }

    private void checkAbsent() {
        for (String name : classAttendance.keySet()) {
            //1. attendance를 가져온다
            //2. 홀리데이가 아닌데 빠진 날이 있는지 체크한다
            //3. 해당 날은 결석으로 추가한다
            Attendance attendance = classAttendance.get(name);
            attendance.checkAbsent();
        }
    }

    private Map<String, Attendance> saveAttendance() {
        classAttendance = new LinkedHashMap<>();
        List<String> records = fileService.readFile();
        for (String record : records) {
            String[] datas = record.split(",");
            String name = datas[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime date = LocalDateTime.parse(datas[1], formatter);
            if(classAttendance.containsKey(name)) {
                Attendance attendance = classAttendance.get(name);
                attendance.addRecord(date);
                continue;
            }
            classAttendance.put(name, new Attendance(date));
        }

        return classAttendance;
    }

    public boolean isNickNameExist(String name) {
        return classAttendance.containsKey(name);
    }

    public boolean isAlreadyAttendant(String name, LocalDateTime dateTime) {
        if(!classAttendance.containsKey(name)) {
            throw new IllegalArgumentException(ErrorMessage.NON_EXIST_NAME);
        }

        Attendance attendance = classAttendance.get(name);
        return attendance.isAlreadyAttendant(dateTime);
    }

    public Attendance getStudentAttendance(String name) {
        return classAttendance.get(name);
    }
}
