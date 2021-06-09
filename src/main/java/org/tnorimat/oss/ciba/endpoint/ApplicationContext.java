package org.tnorimat.oss.ciba.endpoint;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="ciba")
public class ApplicationContext {

    private String callbackUri;
    private String clientId;
    private String clientSecret;

    public String getCallbackUri() {
        return callbackUri;
    }
    public void setCallbackUri(String callbackUri) {
        this.callbackUri = callbackUri;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}
