package com.hitachi.oss.ciba.endpoint;

import com.hitachi.oss.ciba.bean.AuthDelegationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DecoupledAuthnRequestControllerTest {

    private static final String USER_INFO = "user info";
    private static final String DECOUPLED_AUTH_ID = "decoupled auth id";

    @InjectMocks
    private DecoupledAuthnRequestController controller;

    @Mock
    private ApplicationContext cibaContext;

    @Test
    void doDecoupledAuthenticationAndConsent() {
        // Given
        AuthDelegationRequest request = new AuthDelegationRequest();

        request.setSubject(USER_INFO);
        request.setDecoupledAuthId(DECOUPLED_AUTH_ID);

        // When
        ResponseEntity<String> actual = controller.doDecoupledAuthenticationAndConsent(request);

        // Then
        assertEquals(HttpStatus.OK, actual.getStatusCode());

        verify(cibaContext).getCallbackUri();
        verify(cibaContext).getClientId();
        verify(cibaContext).getClientSecret();
    }
}