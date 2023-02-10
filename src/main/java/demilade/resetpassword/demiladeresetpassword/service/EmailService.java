package demilade.resetpassword.demiladeresetpassword.service;


import com.mashape.unirest.http.exceptions.UnirestException;
import demilade.resetpassword.demiladeresetpassword.dto.request.MailRequest;
import demilade.resetpassword.demiladeresetpassword.dto.response.MailResponse;

import java.util.concurrent.CompletableFuture;


public interface EmailService {
    CompletableFuture<MailResponse> sendSimpleMail(MailRequest mailRequest) throws UnirestException;
}
