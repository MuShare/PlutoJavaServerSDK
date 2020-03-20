package org.mushare.pluto.exception;

public enum PlutoErrorCode {
    expired,
    appIdError,
    unauthorized,
    signatureError;

    @Override
    public String toString() {
        switch (this) {
            case expired:
                return "JWT token is expired.";
            case appIdError:
                return "App id is not compatiable.";
            case unauthorized:
                return "Unauthorized user, make sure the user contains the scopes.";
            case signatureError:
                return "Cannot verify signature";
            default:
                return "Unknown pluto error";
        }
    }
}
