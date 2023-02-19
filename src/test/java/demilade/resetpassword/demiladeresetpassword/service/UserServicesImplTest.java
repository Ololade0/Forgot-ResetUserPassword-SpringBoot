package demilade.resetpassword.demiladeresetpassword.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import demilade.resetpassword.demiladeresetpassword.dto.request.UserLoginRequestModel;
import demilade.resetpassword.demiladeresetpassword.dto.response.UserLoginResponse;
import demilade.resetpassword.demiladeresetpassword.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServicesImplTest {
    User savedUser;
    @Autowired
    private UserServices userService;


    @BeforeEach
    void setUp() throws UnirestException {
        User userRegisterRequest = new User();
        userRegisterRequest.setEmail("adesuyiololad@gmail.com");
        userRegisterRequest.setName("Adesuyi Ololade");
        userRegisterRequest.setPassword("1234");
         savedUser = userService.registerUser(userRegisterRequest);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testThatUserCanBeRegistered() throws UnirestException {

        User userRegisterRequest = new User();
        userRegisterRequest.setEmail("adesuyiololad@gmail.com");
        userRegisterRequest.setName("Adesuyi Ololade");
        userRegisterRequest.setPassword("1234");
       User savedUser = userService.registerUser(userRegisterRequest);
        assertThat(savedUser.getEmail()).isEqualTo("adesuyiololad@gmail.com");
        assertThat(savedUser.getUserId()).isNotNull();
    }

    @Test
    public void UserCanLogin() {
        UserLoginRequestModel userLoginRequestModel = new UserLoginRequestModel();
        userLoginRequestModel.setPassword(savedUser.getPassword());
        userLoginRequestModel.setEmail(savedUser.getEmail());
        UserLoginResponse response =  userService.loginUser(userLoginRequestModel);
        assertEquals("Login successful", response.getMessage());
//       assertEquals(200, response.getCode());

    }
}