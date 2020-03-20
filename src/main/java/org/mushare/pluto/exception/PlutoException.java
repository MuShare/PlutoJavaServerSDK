package org.mushare.pluto.exception;

public class PlutoException extends RuntimeException {

    private final PlutoError error;

    public PlutoException(PlutoError error) {
        super();
        this.error = error;
    }

}
