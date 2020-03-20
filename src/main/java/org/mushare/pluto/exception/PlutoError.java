package org.mushare.pluto.exception;

public class PlutoError extends Error {

    private PlutoErrorCode code;

    public PlutoError(PlutoErrorCode code) {
        super(code.toString());
        this.code = code;
    }
}
