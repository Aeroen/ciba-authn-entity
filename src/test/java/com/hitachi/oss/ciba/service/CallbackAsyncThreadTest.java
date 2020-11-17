package com.hitachi.oss.ciba.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.oss.ciba.bean.AuthNotificationResultRequest;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackAsyncThreadTest {

    private static final String USER_INFO = "user info";
    private static final String DECOUPLED_AUTH_ID = "decoupled auth id";
    private static final String AUTH_RESULT = "success";

    private final CallbackAsyncThread callbackAsyncThread = new CallbackAsyncThread(USER_INFO, DECOUPLED_AUTH_ID, AUTH_RESULT, 10, "http://localhost:666/callback", "0x29a", "secret");

    @Test
    void run() throws IOException {
        // When
        StringEntity actual = callbackAsyncThread.getStringEntity();

        // Then
        AuthNotificationResultRequest request = new ObjectMapper().readValue(actual.getContent(), AuthNotificationResultRequest.class);

        assertEquals(USER_INFO, request.getSubject());
        assertEquals(AUTH_RESULT, request.getAuthResult());
        assertEquals(DECOUPLED_AUTH_ID, request.getDecoupledAuthId());
    }
}