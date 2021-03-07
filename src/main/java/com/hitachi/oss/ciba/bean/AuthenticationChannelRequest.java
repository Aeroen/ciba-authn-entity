package com.hitachi.oss.ciba.bean;

public class AuthenticationChannelRequest {

    private String authenticationChannelId;
    private String userInfo;
    private boolean isConsentRequred;
    private String scope;
    private String bindingMessage;

    public String getAuthenticationChannelId() {
        return authenticationChannelId;
    }

    public void setAuthenticationChannelId(String authenticationChannelId) {
        this.authenticationChannelId = authenticationChannelId;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isConsentRequired() {
        return isConsentRequred;
    }

    public void setConsentRequired(boolean isConsentRequred) {
        this.isConsentRequred = isConsentRequred;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getBindingMessage() {
        return bindingMessage;
    }

    public void setBindingMessage(String bindingMessage) {
        this.bindingMessage = bindingMessage;
    }
}
