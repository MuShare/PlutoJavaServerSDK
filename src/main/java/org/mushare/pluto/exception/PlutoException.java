package org.mushare.pluto.exception;

public class PlutoException extends RuntimeException {

    private final PlutoError error;

    public PlutoException(PlutoErrorCode code) {
        super();
        this.error = new PlutoError(code);
    }

    @Override
    public String getMessage() {
        return error.getMessage();
    }
}
