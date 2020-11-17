package com.hitachi.oss.ciba.endpoint;

import com.hitachi.oss.ciba.DecoupledAuthnControlParameterStore;
import com.hitachi.oss.ciba.bean.AuthDelegationRequest;
import com.hitachi.oss.ciba.bean.DecoupledAuthenticationRequest;
import com.hitachi.oss.ciba.service.CallbackAsyncThread;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/request-decoupled-authentication")
public class DecoupledAuthnRequestController {

    private static final Log log = LogFactory.getLog(DecoupledAuthnRequestController.class);

    @Autowired
    private ApplicationContext cibaContext;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> doDecoupledAuthenticationAndConsent(@RequestBody AuthDelegationRequest request) {
        DecoupledAuthenticationRequest decoupledAuthnRequest = new DecoupledAuthenticationRequest();

        decoupledAuthnRequest.setBindingMessage(request.getBindingMessage());
        decoupledAuthnRequest.setConsentRequired(request.isConsentRequired());
        decoupledAuthnRequest.setDecoupledAuthId(request.getDecoupledAuthId());
        decoupledAuthnRequest.setUserInfo(request.getSubject());
        decoupledAuthnRequest.setScope(request.getScope());
        dumpDecoupledAuthenticationRequest(decoupledAuthnRequest);

        log.warn("request started");
        CallbackAsyncThread callbackProcess = new CallbackAsyncThread(
                decoupledAuthnRequest.getUserInfo(),
                decoupledAuthnRequest.getDecoupledAuthId(),
                DecoupledAuthnControlParameterStore.getInstance().getAuthnResult(),
                DecoupledAuthnControlParameterStore.getInstance().getWaitingInSec(),
                cibaContext.getCallbackUri(),
                cibaContext.getClientId(),
                cibaContext.getClientSecret());
        callbackProcess.start();

        HttpStatus status = HttpStatus.OK;
        return new ResponseEntity<>(status);
    }

    private void dumpDecoupledAuthenticationRequest(DecoupledAuthenticationRequest request) {
        log.info(" binding_message = " + request.getBindingMessage());
        log.info(" is_consent_required = " + request.isConsentRequired());
        log.info(" decoupled_auhtn_binding_id = " + request.getDecoupledAuthId());
        log.info(" user_info = " + request.getUserInfo());
        log.info(" scope = " + request.getScope());
    }

}