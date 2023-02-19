package demilade.resetpassword.demiladeresetpassword.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import demilade.resetpassword.demiladeresetpassword.dto.request.MailRequest;
import demilade.resetpassword.demiladeresetpassword.exception.InvalidTokenException;
import demilade.resetpassword.demiladeresetpassword.exception.UserCannotBeFoundException;
import demilade.resetpassword.demiladeresetpassword.dto.request.UserLoginRequestModel;

import demilade.resetpassword.demiladeresetpassword.dto.response.UserLoginResponse;
import demilade.resetpassword.demiladeresetpassword.model.Role;
import demilade.resetpassword.demiladeresetpassword.model.User;
import demilade.resetpassword.demiladeresetpassword.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

import java.util.Set;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServicesImpl implements UserServices, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(User userRegisterRequest) {
        User user = new User();
        user.setName(userRegisterRequest.getName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
//        sendMail(user);
        return userRepository.save(user);

    }





    @Override
    public UserLoginResponse loginUser(UserLoginRequestModel userLoginRequestModel) {
        var user = userRepository.findUserByEmail(userLoginRequestModel.getEmail());
        if (user != null && user.getPassword().equals(userLoginRequestModel.getPassword())) ;
        return buildSuccessfulLoginResponse(user);

    }

    private UserLoginResponse buildSuccessfulLoginResponse(User user) {
        return UserLoginResponse.builder()
                .message("Login successful")
                .build();

    }

    @Override
    public User findUserByEmail(String email) throws UserCannotBeFoundException {
        User foundUser = userRepository.findUserByEmail(email);
        if (foundUser != null) {
            return foundUser;
        } else {
            throw new UserCannotBeFoundException("User with email " + email + "cannot be found");
        }

    }

    @Override
    public String forgotPassword(String email) throws UserCannotBeFoundException, UnirestException {
      User foundUser =  userRepository.findUserByEmail(email);
      if(foundUser != null){
         String token = UUID.randomUUID().toString();
          sendTokenMail(foundUser, token);
          foundUser.setPasswordResetToken(token);
          userRepository.save(foundUser);
          return  token;
      }
      throw new UserCannotBeFoundException("User with email "+ email + "cannot be found");
    }


    private void sendTokenMail(User user, String token) throws UnirestException {
        MailRequest mailRequest = MailRequest.builder()
                .sender(System.getenv("SENDER"))
                .receiver(user.getEmail())
                .subject("Kindly reset your passowrd")
                .body("Here is your otp " + token)

                .build();
        emailService.sendSimpleMail(mailRequest);

    }
    @Override
    public String resetPassword(String token, String newPassword) throws UserCannotBeFoundException, InvalidTokenException {
     User foundUser =   userRepository.findUserByPasswordResetToken(token);
     if(foundUser!= null){
         if(foundUser.getPasswordResetToken().equals(token)){
             foundUser.setPassword(passwordEncoder.encode(newPassword) );
             userRepository.save(foundUser);

         }
         else {
             throw new InvalidTokenException("Invalid token");
         }
     }
     else {
         throw new UserCannotBeFoundException("User cannot be found");
     }
     return "Password return successful";

    }

    private void sendMail(User user) throws UnirestException {
        MailRequest mailRequest = MailRequest.builder()
                .sender(System.getenv("SENDER"))
                .receiver(user.getEmail())
                .subject("You are welcome")
                .body("Hello " + user.getName() + ". We are glad to let you know you have successfully registered")
                .build();
        emailService.sendSimpleMail(mailRequest);

    }



        @Override
        public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
            User user = userRepository.findUserByEmail(username);
            if (user != null) {
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthorities(user.getRoleHashSet()));
            }
            throw new UsernameNotFoundException("User with email " + username + " does not exist");
        }

        private Collection<? extends GrantedAuthority> getAuthorities (Set < Role > roles) {
            return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleType().name())).collect(Collectors.toSet());
        }
    }

