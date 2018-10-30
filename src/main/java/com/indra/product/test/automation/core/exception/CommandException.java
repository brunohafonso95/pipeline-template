package com.indra.product.test.automation.core.exception;

/**
 * O Objetivo desta classe é prover exceção para os comandos de elementos dom.
 * 
 * @author msergio
 * @since Setembro de 2018
 * @version {version}
 */
public class CommandException extends Exception {

    private static final long serialVersionUID = 5471212168234904569L;

    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Exception cause) {
        super(message, cause);
    }

    public CommandException(Exception cause) {
        super(cause);
    }

}