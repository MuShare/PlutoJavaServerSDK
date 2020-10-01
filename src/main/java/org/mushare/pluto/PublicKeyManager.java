package org.mushare.pluto;

import com.github.kevinsawicki.http.HttpRequest;
import net.sf.json.JSONObject;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class PublicKeyManager {

    private String server;
    private PublicKey publicKey;

    public PublicKeyManager(String server) {
        super();
        this.server = server;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PublicKey getPublicKey() {
        if (publicKey == null) {
            String response = HttpRequest.get(server + "v1/token/publickey").body();
            String text = JSONObject.fromObject(response).getJSONObject("body").getString("public_key");
            text = text.replaceAll("\r|\n", "")
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "");
            byte[] data = Base64.getDecoder().decode(text);
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(data);
            try {
                publicKey = KeyFactory.getInstance("RSA").generatePublic(x509);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        return publicKey;
    }

}
