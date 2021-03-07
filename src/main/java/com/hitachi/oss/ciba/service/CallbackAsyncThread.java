package com.hitachi.oss.ciba.service;

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
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.hitachi.oss.ciba.util.BasicAuthHelper;

public class CallbackAsyncThread extends Thread {

    private static final Log log = LogFactory.getLog(CallbackAsyncThread.class);

    final private String userInfo;
    final private String authenticationChannelId;
    final String authResult;
    final int waitInSec;
    final String callbackEndpoint;
    final String clientId;
    final String clientSecret;

    public CallbackAsyncThread(
            String loginHint, String authenticationChannelId, String authResult,
            int waitInSec, String callbackEndpoint, String clientId, String clientSecret) {
        this.userInfo = loginHint;
        this.authenticationChannelId = authenticationChannelId;
        this.authResult = authResult;
        this.waitInSec = waitInSec;
        this.callbackEndpoint = callbackEndpoint;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public void run() {
        log.info("Async function started. auth_result: " + authResult + " user_info: " + userInfo + " authentication_channel_id " + authenticationChannelId + " waitInSec: " + waitInSec);
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
            List<NameValuePair> parameters = new LinkedList<>();
            parameters.add(new BasicNameValuePair("authentication_channel_id", authenticationChannelId));
            parameters.add(new BasicNameValuePair("user_info", userInfo));
            parameters.add(new BasicNameValuePair("auth_result", authResult));
            UrlEncodedFormEntity formEntity;
            try {
                formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            post.setEntity(formEntity);
            CloseableHttpResponse response = client.execute(post);
            reason =  response.getStatusLine().getReasonPhrase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Completed. reason = " + reason);
    }
}
