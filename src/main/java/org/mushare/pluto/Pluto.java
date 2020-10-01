package org.mushare.pluto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.kevinsawicki.http.HttpRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.mushare.pluto.exception.PlutoErrorCode;
import org.mushare.pluto.exception.PlutoException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Pluto {

    private PublicKeyManager keyManager;
    private String server;
    private String appId;

    private Pluto() {
        super();
    }

    private static Pluto shared = new Pluto();

    public static void setup(String server, String appId) {
        shared.keyManager = new PublicKeyManager(server);
        shared.server = server;
        shared.appId = appId;
    }

    public static PlutoUser auth(String token) throws PlutoException {
        JWTVerifier verifier = JWT.require(Algorithm.RSA256((RSAPublicKey) shared.keyManager.getPublicKey()))
                .withIssuer(shared.appId)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return new PlutoUser(jwt.getClaims());
    }

    public static List<PlutoUserInfo> fetUserInfos(List<Long> userIds) {
        if (userIds == null || userIds.size() == 0) {
            return new ArrayList<>();
        }
        String ids = userIds.stream()
                .map(userId -> "ids=" + userId)
                .reduce("", (s1, s2) -> {
                    return s1 + "&" + s2;
                });
        System.out.println(shared.server + "v1/user/info/public?" + ids);
        String response = HttpRequest.get(shared.server + "v1/user/info/public?" + ids).body();
        JSONObject body = JSONObject.fromObject(response).getJSONObject("body");

        return Arrays.stream(body.keySet().toArray())
                .map(key -> {
                    JSONObject object = body.getJSONObject((String) key);
                    PlutoUserInfo info = new PlutoUserInfo();
                    info.setUserId(object.getLong("id"));
                    if (object.containsKey("err_code") && object.getInt("err_code") == 403) {
                        return info;
                    }
                    info.setAvatar(object.getString("avatar"));
                    info.setName(object.getString("name"));
                    return info;
                })
                .collect(Collectors.toList());
    }

}
