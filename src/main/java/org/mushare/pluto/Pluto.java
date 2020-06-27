package org.mushare.pluto;

import com.github.kevinsawicki.http.HttpRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.mushare.pluto.exception.PlutoErrorCode;
import org.mushare.pluto.exception.PlutoException;

import java.io.UnsupportedEncodingException;
import java.security.Signature;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
        if (token == null) {
            throw new PlutoException(PlutoErrorCode.jwtFormatError);
        }
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new PlutoException(PlutoErrorCode.jwtFormatError);
        }

        String header = null;
        JSONObject payload = null;
        try {
            header = new String(Base64.getDecoder().decode(parts[0]), "utf-8");
            payload = JSONObject.fromObject(new String(Base64.getDecoder().decode(parts[1]), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new PlutoException(PlutoErrorCode.other);
        }
        // Verify the appId of the jwt token.
        if (!payload.getString("appId").equals(shared.appId)) {
            throw new PlutoException(PlutoErrorCode.appIdError);
        }
        Long expire = payload.getLong("expire_time") * 1000;
        if (expire < System.currentTimeMillis()) {
            throw new PlutoException(PlutoErrorCode.expired);
        }

        boolean verified = false;
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(shared.keyManager.getPublicKey());
            signature.update((header + payload).getBytes());
            verified = signature.verify(Base64.getDecoder().decode(parts[2]));
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlutoException(PlutoErrorCode.other);
        }

        if (!verified) {
            throw new PlutoException(PlutoErrorCode.notVerified);
        }
        return new PlutoUser(payload);
    }

    public static List<PlutoUserInfo> fetUserInfos(List<Long> userIds) {
        if (userIds == null || userIds.size() == 0) {
            return new ArrayList<>();
        }
        String ids = userIds.stream()
                .map(userId -> userId + "-")
                .reduce("", (s1, s2) -> {
                    return s1 + s2;
                });
        ids = ids.substring(0, ids.length() - 1);
        String response = HttpRequest.get(shared.server + "api/user/info/" + ids).body();
        JSONArray body = JSONObject.fromObject(response).getJSONArray("body");
        return IntStream.range(0, body.size())
                .mapToObj(index -> {
                    JSONObject object = body.getJSONObject(index);
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
