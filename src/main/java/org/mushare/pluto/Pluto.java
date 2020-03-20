package org.mushare.pluto;

public class Pluto {

    private PublicKeyManager keyManager;

    private Pluto() {
        super();
    }

    public PublicKeyManager getKeyManager() {
        return keyManager;
    }

    public void setKeyManager(PublicKeyManager keyManager) {
        this.keyManager = keyManager;
    }

    private static Pluto shared = new Pluto();

    public static void setup(String server) {
        shared.setKeyManager(new PublicKeyManager(server));
    }

}
