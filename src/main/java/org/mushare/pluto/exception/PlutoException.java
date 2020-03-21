package org.mushare.pluto.exception;

public class PlutoException extends Exception {

    private final PlutoError error;

    public PlutoException(PlutoErrorCode code) {
        super();
        this.error = new PlutoError(code);
    }

    public PlutoError getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return error.getMessage();
    }

}
