package org.mushare.pluto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlutoUser {
    private long userId;
    private String deviceId;
    private List<String> scopes;
    private LoginType loginType;

    public long getUserId() {
        return userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public PlutoUser(JSONObject payload) {
        userId = payload.getLong("userId");
        deviceId = payload.getString("deviceId");
        scopes = new ArrayList<>();
        if (payload.containsKey("scopes") && !payload.get("scopes").equals("null")) {
            JSONArray scopeArray = payload.getJSONArray("scopes");
            for (int i = 0; i < scopeArray.size(); i++) {
                scopes.add(scopeArray.getString(i));
            }
        }
        loginType = LoginType.fromIdentifier(payload.getString("login_type"));
    }
}
