package com.softcore.vtpsales.Model;


import com.google.gson.annotations.SerializedName;

public class SL_LoginResponse {
    @SerializedName("SessionId")
    private String sessionId;

    @SerializedName("Version")
    private String version;

    @SerializedName("SessionTimeout")
    private int sessionTimeout;

    public String getSessionId() {
        return sessionId;
    }

    public String getVersion() {
        return version;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }
}
