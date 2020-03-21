package org.mushare.pluto;

import net.sf.json.JSONObject;
import org.mushare.pluto.exception.PlutoErrorCode;
import org.mushare.pluto.exception.PlutoException;

import java.io.UnsupportedEncodingException;
import java.security.Signature;
import java.util.Base64;

public class Pluto {

    private PublicKeyManager keyManager;
    private String appId;

    private Pluto() {
        super();
    }

    private static Pluto shared = new Pluto();

    public static void setup(String server, String appId) {
        shared.keyManager = new PublicKeyManager(server);
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

}
