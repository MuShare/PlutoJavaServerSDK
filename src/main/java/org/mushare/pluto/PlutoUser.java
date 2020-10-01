package org.mushare.pluto;

import com.auth0.jwt.interfaces.Claim;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlutoUser {
    private long userId;
    private List<String> scopes;

    public long getUserId() {
        return userId;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public PlutoUser(Map<String, Claim> claims) {
        userId = claims.get("sub").asLong();
        scopes = claims.get("scopes").asList(String.class);
    }
}
