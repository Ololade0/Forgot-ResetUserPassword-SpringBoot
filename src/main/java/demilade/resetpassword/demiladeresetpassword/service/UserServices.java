package demilade.resetpassword.demiladeresetpassword.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import demilade.resetpassword.demiladeresetpassword.exception.InvalidTokenException;
import demilade.resetpassword.demiladeresetpassword.exception.UserCannotBeFoundException;
import demilade.resetpassword.demiladeresetpassword.dto.request.UserLoginRequestModel;
import demilade.resetpassword.demiladeresetpassword.dto.response.UserLoginResponse;
import demilade.resetpassword.demiladeresetpassword.model.User;

public interface UserServices  {
    User registerUser(User userRegisterRequest) throws UnirestException;
    UserLoginResponse loginUser(UserLoginRequestModel userLoginRequestModel);

    User findUserByEmail(String email) throws UserCannotBeFoundException;


    String forgotPassword(String email) throws UserCannotBeFoundException, UnirestException;

    String resetPassword(String token, String newPassword) throws UserCannotBeFoundException, InvalidTokenException;
}
