package mapoAttendance.attendanceCheck.exception;

public class NotRegisterException extends RuntimeException {
    public NotRegisterException() {
        super();
    }

    public NotRegisterException(String message) {
        super(message);
    }

    public NotRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotRegisterException(Throwable cause) {
        super(cause);
    }
}
