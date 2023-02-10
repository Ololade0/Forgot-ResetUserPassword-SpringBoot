package demilade.resetpassword.demiladeresetpassword.dto.response;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Setter
@Getter
@Builder
public class UserLoginResponse {
    private String message;
}
