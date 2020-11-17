package com.hitachi.oss.ciba.service;

import com.hitachi.oss.ciba.bean.AuthNotificationResultRequest;
import com.hitachi.oss.ciba.util.BasicAuthHelper;
import com.hitachi.oss.ciba.util.JsonHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class CallbackAsyncThread extends Thread {

    private static final Log log = LogFactory.getLog(CallbackAsyncThread.class);

    final private String userInfo;
    final private String decoupledAuthId;
    final String authResult;
    final int waitInSec;
    final String callbackEndpoint;
    final String clientId;
    final String clientSecret;

    public CallbackAsyncThread(
            String loginHint, String decoupledAuthnBindingId, String authResult,
            int waitInSec, String callbackEndpoint, String clientId, String clientSecret) {
        this.userInfo = loginHint;
        this.decoupledAuthId = decoupledAuthnBindingId;
        this.authResult = authResult;
        this.waitInSec = waitInSec;
        this.callbackEndpoint = callbackEndpoint;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public void run() {
        log.info("Async function started. auth_result: " + authResult + " user_info: " + userInfo + " decoupled_auth_id " + decoupledAuthId + " waitInSec: " + waitInSec);
        try {
            Thread.sleep(waitInSec * 1000L);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        String reason = null;
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPost post = new HttpPost(callbackEndpoint);
            String authorization = BasicAuthHelper.createHeader(clientId, clientSecret);
            post.setHeader("Authorization", authorization);

            StringEntity stringEntity = getStringEntity();

            post.setEntity(stringEntity);
            CloseableHttpResponse response = client.execute(post);
            reason = response.getStatusLine().getReasonPhrase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Completed. reason = " + reason);
    }

    public StringEntity getStringEntity() {
        AuthNotificationResultRequest request = new AuthNotificationResultRequest();

        request.setDecoupledAuthId(decoupledAuthId);
        request.setSubject(userInfo);
        request.setAuthResult(authResult);

        String requestAsString = JsonHelper.convertToJson(request);

        return new StringEntity(requestAsString, ContentType.APPLICATION_JSON);
    }
}
