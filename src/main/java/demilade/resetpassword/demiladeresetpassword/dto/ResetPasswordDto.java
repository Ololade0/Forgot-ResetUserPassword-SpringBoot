package demilade.resetpassword.demiladeresetpassword.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordDto {
    private String token;
    private String password;
}
