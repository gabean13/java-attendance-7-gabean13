package attendance.constant;

public class ServiceMessage {
    public static final String TODAY_DATE = "오늘은 12월 {0}일 {1}요일입니다. 기능을 선택해 주세요.\n";
    public static final String ORDER = "1. 출석 확인\n"
            + "2. 출석 수정\n"
            + "3. 크루별 출석 기록 확인\n"
            + "4. 제적 위험자 확인\n"
            + "Q. 종료";
    public static final String RECORD_NICKNAME = "닉네임을 입력해 주세요.";
    public static final String RECORD_TIME = "등교 시간을 입력해 주세요.";
    public static final String RECORD_INFO = "12월 {0}일 {1}요일 {2}:{3} ({4})";

    public static final String FIX_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    public static final String FIX_DATE = "수정하려는 날짜(일)를 입력해 주세요.";
    public static final String FIX_TIME = "언제로 변경하겠습니까?";

    public static final String PRINT_RECORD_START = "이번 달 {0}의 출석 기록입니다.\n";
    public static final String PRINT_RECORD_TOTAL = "\n출석: {0}회\n"
            + "지각: {1}회\n"
            + "결석: {2}회\n";
    public static final String PRINT_CAUTION_NEEDED  = "\n{0} 대상자입니다.\n";
}
