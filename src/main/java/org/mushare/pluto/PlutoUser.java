package org.mushare.pluto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PlutoUser {
    private long userId;
    private String deviceId;
    private String [] scopes;
    private LoginType loginType;

    public long getUserId() {
        return userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public PlutoUser(JSONObject payload) {
        userId = payload.getLong("userId");
        deviceId = payload.getString("deviceId");
        JSONArray scopeArray = payload.getJSONArray("scopes");
        scopes = new String[scopeArray.size()];
        for (int i = 0; i < scopeArray.size(); i++) {
            scopes[i] = scopeArray.getString(i);
        }
        loginType = LoginType.fromIdentifier(payload.getString("login_type"));
    }
}
