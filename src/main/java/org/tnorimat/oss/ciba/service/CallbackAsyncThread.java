package org.tnorimat.oss.ciba.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.tnorimat.oss.ciba.bean.AuthenticationResultNotification;
import org.tnorimat.oss.ciba.util.BasicAuthHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CallbackAsyncThread extends Thread {

    private static final Log log = LogFactory.getLog(CallbackAsyncThread.class);

    final private String loginHint;
    final private String jwtBearerToken;
    final String authResult;
    final int waitInSec;
    final String callbackEndpoint;
    final String clientId;
    final String clientSecret;

    public CallbackAsyncThread(
            String loginHint, String authenticationChannelId, String authResult,
            int waitInSec, String callbackEndpoint, String clientId, String clientSecret) {
        this.loginHint = loginHint;
        this.jwtBearerToken = authenticationChannelId;
        this.authResult = authResult;
        this.waitInSec = waitInSec;
        this.callbackEndpoint = callbackEndpoint;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public void run() {
        log.info("Async function started. authResult: " + authResult + " loginHint: " + loginHint + " jwtBearerToken " + jwtBearerToken + " waitInSec: " + waitInSec);
        try {
            Thread.sleep(waitInSec * 1000L);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        String reason = null;
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpPost post = new HttpPost(callbackEndpoint);
            //String authorization = BasicAuthHelper.createHeader(clientId, clientSecret);
            post.setHeader("Authorization", "Bearer " + jwtBearerToken);
            AuthenticationResultNotification notification = new AuthenticationResultNotification();
            notification.setStatus(authResult);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(notification);
            StringEntity entity = new StringEntity(json);
            post.setEntity(entity);
            //post.setHeader("Accept", "application/json");
            post.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(post);
            reason =  response.getStatusLine().getReasonPhrase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Completed. reason = " + reason);
    }
}
