package demilade.resetpassword.demiladeresetpassword.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCannotBeFoundException extends Throwable {
    private int statusCode;
    public UserCannotBeFoundException(String message) {
        super(message);
    }

    public UserCannotBeFoundException(int statusCode) {
        this.statusCode = statusCode;
    }

    public UserCannotBeFoundException(String message, int statusCode) {
    }
}
