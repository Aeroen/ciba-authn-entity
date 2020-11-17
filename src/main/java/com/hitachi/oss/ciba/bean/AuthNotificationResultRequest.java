package com.hitachi.oss.ciba.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthNotificationResultRequest {

    @JsonProperty("iss")
    private String issuer;

    private Long iat;

    @JsonProperty("jti")
    private String id;

    @JsonProperty("aud")
    private String[] audience;

    @JsonProperty("sub")
    private String subject;

    @JsonProperty("decoupled_auth_id")
    private String decoupledAuthId;

    @JsonProperty("auth_result")
    private String authResult;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getAudience() {
        return audience;
    }

    public void setAudience(String[] audience) {
        this.audience = audience;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDecoupledAuthId() {
        return decoupledAuthId;
    }

    public void setDecoupledAuthId(String decoupledAuthId) {
        this.decoupledAuthId = decoupledAuthId;
    }

    public String getAuthResult() {
        return authResult;
    }

    public void setAuthResult(String authResult) {
        this.authResult = authResult;
    }
}
