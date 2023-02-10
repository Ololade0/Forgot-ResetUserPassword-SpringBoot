package demilade.resetpassword.demiladeresetpassword.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import demilade.resetpassword.demiladeresetpassword.dto.request.UserLoginRequestModel;
import demilade.resetpassword.demiladeresetpassword.exception.InvalidTokenException;
import demilade.resetpassword.demiladeresetpassword.exception.UserCannotBeFoundException;
import demilade.resetpassword.demiladeresetpassword.model.AuthToken;
import demilade.resetpassword.demiladeresetpassword.model.User;
import demilade.resetpassword.demiladeresetpassword.security.jwt.TokenProvider;
import demilade.resetpassword.demiladeresetpassword.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")

public class UserController {
    @Autowired
    private UserServices userService;

    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User userRegisterRequest) throws UnirestException {
        User user = userService.registerUser(userRegisterRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestModel loginRequest) throws UserCannotBeFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateJWTToken(authentication);
        User user = userService.findUserByEmail(loginRequest.getEmail());
        return new ResponseEntity<>(new AuthToken(token, user.getUserId()), HttpStatus.OK);
    }
    @PostMapping("/forgotpassword/{email}")
    public ResponseEntity<?> forgotPassword(@PathVariable String email) throws UserCannotBeFoundException, UnirestException {
        String passwordResetToken = userService.forgotPassword(email);
        return new ResponseEntity<>(passwordResetToken, HttpStatus.OK);
    }
@PostMapping("/resetpassword/{token}/{newPassword}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @PathVariable String newPassword) throws UserCannotBeFoundException, InvalidTokenException {
       String message = userService.resetPassword(token, newPassword);
       return new ResponseEntity<>(message, HttpStatus.OK);

    }











}
