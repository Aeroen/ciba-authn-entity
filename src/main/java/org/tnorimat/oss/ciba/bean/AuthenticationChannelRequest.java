package org.tnorimat.oss.ciba.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationChannelRequest {

    @JsonProperty("binding_message")
    private String bindingMessage;

    @JsonProperty("login_hint")
    private String loginHint;

    @JsonProperty("is_consent_required")
    private boolean consentRequired;

    private String scope;

    @JsonProperty("acr_values")
    private String acrValues;

    // not JSON serialize/deserialize
    private String jwtBearerToken;

    public String getJwtBearerToken() {
        return jwtBearerToken;
    }

    public void setJwtBearerToken(String jwtBearerToken) {
        this.jwtBearerToken = jwtBearerToken;
    }

    public String getLoginHint() {
        return loginHint;
    }

    public void setLoginHint(String loginHint) {
        this.loginHint = loginHint;
    }

    public boolean isConsentRequired() {
        return consentRequired;
    }

    public void setConsentRequired(boolean consentRequired) {
        this.consentRequired = consentRequired;
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

    public String getAcrValues() {
        return acrValues;
    }

    public void setAcrValues(String acrValues) {
        this.acrValues = acrValues;
    }
}
