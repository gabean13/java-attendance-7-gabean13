package attendance.model;

import attendance.constant.ServiceMessage;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendance {
    private List<Record> records;
    private int attendanceCount;
    private int lateCount;
    private int absentCount;
    private boolean isInterviewNeeded;
    private boolean isWarning;
    private boolean isRemoved;

    public Attendance(LocalDateTime dateTime) {
        records = new ArrayList<>();
        addRecord(dateTime);
    }

    public Record addRecord(LocalDateTime dateTime) {
        Record record = new Record(dateTime);
        records.add(record);
        String recordName = record.getRecordName();
        if (recordName.equals("지각")) {
            lateCount++;
            updateInterview();
            return record;
        }
        if (recordName.equals("결석")) {
            absentCount++;
            updateInterview();
            return record;
        }
        attendanceCount++;
        updateInterview();
        return record;
    }

    private void updateInterview() {
        while(lateCount >= 3) {
            lateCount -= 3;
            absentCount++;
        }

        if(absentCount >= 2) {
            isWarning = true;
        }
        if(absentCount >= 3) {
            isInterviewNeeded = true;
        }
        if(absentCount >= 5) {
            isRemoved = true;
        }
    }

    public void checkAbsent() {
        absentCount += 9 - (attendanceCount + lateCount + absentCount);
    }

    public boolean isAlreadyAttendant(LocalDateTime dateTime) {
        for(Record record : records) {
            if(record.hasDateChecked(dateTime.getDayOfMonth())){
                return true;
            }
        }
        return false;
    }

    public void removeAttendant(LocalDateTime dateTime) {
        int idx = -1;
        for(int i = 0; i < records.size(); i++) {
            if(records.get(i).hasDateChecked(dateTime.getDayOfMonth())){
                idx = i;
                String name = records.get(i).getRecordName();
                if(name.equals("결석")) {
                    lateCount--;
                    break;
                }
                if(name.equals("출석")) {
                    attendanceCount--;
                    break;
                }
                absentCount--;
                break;
            }
        }

        if(idx != -1) {
            records.remove(idx);
        }
    }

    public List<Record> getRecords() {
        return records;
    }

    public Record getRecord(int date) {
        for(Record record : records) {
             if(record.isSameDate(date)) {
                 return record;
             }
        }
        return null;
    }

    public String fixAttendance(LocalDateTime fixDateTime) {
        removeAttendant(fixDateTime);
        Record record = addRecord(fixDateTime);
        String recordName = record.getRecordName();
        return record.getTime() + "(" + recordName + ")";
    }

    public String getTotalRecord() {
        return MessageFormat.format(ServiceMessage.PRINT_RECORD_TOTAL, attendanceCount, lateCount, absentCount);
    }

    public String getInterviewNeeded() {
        if(isRemoved) {
            return MessageFormat.format(ServiceMessage.PRINT_CAUTION_NEEDED, "제적");
        }
        if(isInterviewNeeded) {
            return MessageFormat.format(ServiceMessage.PRINT_CAUTION_NEEDED, "면담");
        }
        if(isWarning) {
            return MessageFormat.format(ServiceMessage.PRINT_CAUTION_NEEDED, "경고");
        }
        return "";
    }
}
