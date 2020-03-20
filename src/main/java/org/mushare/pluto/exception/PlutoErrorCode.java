package org.mushare.pluto.exception;

public enum PlutoErrorCode {
    jwtFormatError,
    expired,
    appIdError,
    notVerified,
    other;

    @Override
    public String toString() {
        switch (this) {
            case jwtFormatError:
                return "JWT must be like `header.payload.sign`";
            case expired:
                return "JWT token is expired.";
            case appIdError:
                return "App id is not compatiable.";
            case notVerified:
                return "Verify failed, cannot verify signature";
            case other:
                return "Unhandled exception: InvalidKeyException, IOException, NoSuchAlgorithmException, SignatureException";
            default:
                return "Unknown pluto error";
        }
    }
}
