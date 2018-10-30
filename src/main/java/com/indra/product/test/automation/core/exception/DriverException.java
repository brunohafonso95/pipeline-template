package com.indra.product.test.automation.core.exception;

/**
 * O Objetivo desta classe é prover exceção para os drives de web.
 * 
 * @author msergio
 * @since Setembro de 2018
 * @version {version}
 */
public class DriverException extends Exception {

    private static final long serialVersionUID = 5471212168234904569L;

    public DriverException() {
        super();
    }

    public DriverException(String message) {
        super(message);
    }

    public DriverException(String message, Exception cause) {
        super(message, cause);
    }

    public DriverException(Exception cause) {
        super(cause);
    }

}