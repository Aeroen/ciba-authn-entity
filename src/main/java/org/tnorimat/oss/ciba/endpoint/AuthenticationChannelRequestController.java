package org.tnorimat.oss.ciba.endpoint;

import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tnorimat.oss.ciba.AuthenticationChannelControlParameterStore;
import org.tnorimat.oss.ciba.bean.AuthenticationChannelRequest;
import org.tnorimat.oss.ciba.service.CallbackAsyncThread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/request-authentication-channel")
public class AuthenticationChannelRequestController {

    private static final Log log = LogFactory.getLog(AuthenticationChannelRequestController.class);

    private static final String BEARER = "Bearer";

    private static final Pattern WHITESPACES = Pattern.compile("\\s+");

    @Autowired
    private ApplicationContext cibaContext;

    @RequestMapping(method=RequestMethod.POST, consumes="application/json;charset=UTF-8")
    public ResponseEntity<String> doAuthenticationChannel(@RequestBody AuthenticationChannelRequest request, @RequestHeader("Authorization") String authHeader) {
        //AuthenticationChannelRequest authenticationChannelRequest = new AuthenticationChannelRequest();
        request.setJwtBearerToken(extractTokenStringFromAuthHeader(authHeader));
        //authenticationChannelRequest.setLoginHint(request.getFirst("loginHint"));
        //authenticationChannelRequest.setConsentRequired(Boolean.valueOf(request.getFirst("consentRequired")));
        //authenticationChannelRequest.setScope(request.getFirst("scope"));
        //authenticationChannelRequest.setBindingMessage(request.getFirst("bindingMessage"));
        //authenticationChannelRequest.setAcrValues(request.getFirst("acrValues"));
        dumpAuthenticationChannelRequest(request);

        log.warn("request started");
        CallbackAsyncThread callbackProcess = new CallbackAsyncThread(
                request.getLoginHint(),
                request.getJwtBearerToken(),
                AuthenticationChannelControlParameterStore.getInstance().getAuthnResult(),
                AuthenticationChannelControlParameterStore.getInstance().getWaitingInSec(),
                cibaContext.getCallbackUri(),
                cibaContext.getClientId(),
                cibaContext.getClientSecret());
        callbackProcess.start();

        HttpStatus status = HttpStatus.CREATED;
        return new ResponseEntity<>(status);
    }

    private static String extractTokenStringFromAuthHeader(String authHeader) {

        if (authHeader == null) {
            return null;
        }

        String[] split = WHITESPACES.split(authHeader.trim());
        if (split.length != 2){
            return null;
        }

        String bearerPart = split[0];
        if (!bearerPart.equalsIgnoreCase(BEARER)){
            return null;
        }

        String tokenString = split[1];
        if (tokenString.isEmpty()) {
            return null;
        }

        return tokenString;
    }

    private void dumpAuthenticationChannelRequest(AuthenticationChannelRequest request) {
        log.info(" JwtBearerToken = " + request.getJwtBearerToken());
        log.info(" loginHint = " + request.getLoginHint());
        log.info(" consentRequired = " + request.isConsentRequired());
        log.info(" scope = " + request.getScope());
        log.info(" bindingMessage = " + request.getBindingMessage());
        log.info(" acrValues = " + request.getAcrValues());
    }

}