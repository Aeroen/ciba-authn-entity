package com.hitachi.oss.ciba;

public class AuthenticationChannelControlParameterStore {

    private static AuthenticationChannelControlParameterStore SINGLETON = new AuthenticationChannelControlParameterStore();

    public static AuthenticationChannelControlParameterStore getInstance() {
        return SINGLETON;
    }

    private int waitingInSec = 10;
    private String authResult = AuthenticationChannelStatus.SUCCEEDED;

    public int getWaitingInSec() {
        return waitingInSec;
    }

    public void setWaitingInSec(int waitingInSec) {
        this.waitingInSec = waitingInSec;
    }

    public String getAuthnResult() {
        return authResult;
    }

    public void setAuthResult(String authnResult) {
        this.authResult = authnResult;
    }

    
}
