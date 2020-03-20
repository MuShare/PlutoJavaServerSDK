package org.mushare.pluto;

public enum LoginType {
    mail,
    google,
    apple;

    public static LoginType fromIdentifier(String identifier) {
        switch (identifier) {
            case "mail":
                return mail;
            case "google":
                return google;
            case "apple":
                return apple;
            default:
                return null;
        }
    }
}
