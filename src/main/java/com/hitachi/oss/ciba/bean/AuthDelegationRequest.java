package com.hitachi.oss.ciba.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hitachi.oss.ciba.util.StringOrArrayDeserializer;
import com.hitachi.oss.ciba.util.StringOrArraySerializer;

public class AuthDelegationRequest {

    @JsonProperty("sub")
    private String subject;

    @JsonProperty("aud")
    @JsonSerialize(using = StringOrArraySerializer.class)
    @JsonDeserialize(using = StringOrArrayDeserializer.class)
    private String[] audience;

    @JsonProperty("iss")
    private String issuer;

    private Long iat;

    private Long exp;

    @JsonProperty("jti")
    private String id;

    @JsonProperty("decoupled_auth_id")
    private String decoupledAuthId;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("is_consent_required")
    private boolean isConsentRequired;

    @JsonProperty("default_client_scope")
    private String defaultClientScope;

    @JsonProperty("binding_message")
    private String bindingMessage;

    @JsonProperty("user_code")
    private String userCode;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String[] getAudience() {
        return audience;
    }

    public void setAudience(String[] audience) {
        this.audience = audience;
    }

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

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDecoupledAuthId() {
        return decoupledAuthId;
    }

    public void setDecoupledAuthId(String decoupledAuthId) {
        this.decoupledAuthId = decoupledAuthId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isConsentRequired() {
        return isConsentRequired;
    }

    public void setConsentRequired(boolean consentRequired) {
        isConsentRequired = consentRequired;
    }

    public String getDefaultClientScope() {
        return defaultClientScope;
    }

    public void setDefaultClientScope(String defaultClientScope) {
        this.defaultClientScope = defaultClientScope;
    }

    public String getBindingMessage() {
        return bindingMessage;
    }

    public void setBindingMessage(String bindingMessage) {
        this.bindingMessage = bindingMessage;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
