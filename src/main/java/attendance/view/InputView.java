package attendance.view;

import attendance.model.MyClass;
import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private final InputValidator inputValidator;

    public InputView(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public String getOrder() {
        String input = Console.readLine().trim();
        inputValidator.checkGetOrder(input);
        return input;
    }

    public String getNickNameInput(MyClass myClass) {
        String input = Console.readLine().trim();
        inputValidator.checkNickNameInput(input, myClass);
        return input;
    }

    public String getAttendanceTimeInput(MyClass myClass, String name) {
        String input = Console.readLine().trim();
        inputValidator.checkAttendanceTime(myClass, input, name);
        return input;
    }

    public String getFixDateInput() {
        String input = Console.readLine().trim();
        inputValidator.checkFixDateInput(input);
        return input;
    }

    public String getFixNickNameInput(MyClass myClass) {
        String input = Console.readLine().trim();
        inputValidator.checkFixNickNameInput(myClass, input);
        return input;
    }
}
