package com.hitachi.oss.ciba.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hitachi.oss.ciba.AuthenticationChannelControlParameterStore;
import com.hitachi.oss.ciba.bean.AuthenticationChannelRequest;
import com.hitachi.oss.ciba.service.CallbackAsyncThread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/request-authentication-channel")
public class AuthenticationChannelRequestController {

    private static final Log log = LogFactory.getLog(AuthenticationChannelRequestController.class);

    @Autowired
    private ApplicationContext cibaContext;

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<String> doAuthenticationChannel(@RequestBody MultiValueMap<String,String> request) {
        AuthenticationChannelRequest authenticationChannelRequest = new AuthenticationChannelRequest();
        authenticationChannelRequest.setBindingMessage(request.getFirst("binding_message"));
        authenticationChannelRequest.setConsentRequired(Boolean.valueOf(request.getFirst("is_consent_required")));
        authenticationChannelRequest.setAuthenticationChannelId(request.getFirst("authentication_channel_id"));
        authenticationChannelRequest.setUserInfo(request.getFirst("user_info"));
        authenticationChannelRequest.setScope(request.getFirst("scope"));
        dumpAuthenticationChannelRequest(authenticationChannelRequest);

        log.warn("request started");
        CallbackAsyncThread callbackProcess = new CallbackAsyncThread(
                authenticationChannelRequest.getUserInfo(),
                authenticationChannelRequest.getAuthenticationChannelId(),
                AuthenticationChannelControlParameterStore.getInstance().getAuthnResult(),
                AuthenticationChannelControlParameterStore.getInstance().getWaitingInSec(),
                cibaContext.getCallbackUri(),
                cibaContext.getClientId(),
                cibaContext.getClientSecret());
        callbackProcess.start();

        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

    private void dumpAuthenticationChannelRequest(AuthenticationChannelRequest request) {
        log.info(" binding_message = " + request.getBindingMessage());
        log.info(" is_consent_required = " + request.isConsentRequired());
        log.info(" authentication_channel_id = " + request.getAuthenticationChannelId());
        log.info(" user_info = " + request.getUserInfo());
        log.info(" scope = " + request.getScope());
    }

}