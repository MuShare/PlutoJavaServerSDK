package org.mushare.pluto.exception;

public class PlutoError extends Error {

    private PlutoErrorCode code;

    public PlutoErrorCode getCode() {
        return code;
    }

    public PlutoError(PlutoErrorCode code) {
        super(code.toString());
        this.code = code;
    }

}
