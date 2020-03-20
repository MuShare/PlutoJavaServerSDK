package org.mushare.pluto;

public class Pluto {

    private PublicKeyManager keyManager;
    private String appId;

    private Pluto() {
        super();
    }

    public PublicKeyManager getKeyManager() {
        return keyManager;
    }

    public void setKeyManager(PublicKeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    private static Pluto shared = new Pluto();

    public static void setup(String server, String appId) {
        shared.setKeyManager(new PublicKeyManager(server));
        shared.setAppId(appId);
    }

    public static void auth(String token) {

    }

    public static void auth(String token, String scope) {

    }

}
