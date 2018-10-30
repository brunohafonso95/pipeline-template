package com.indra.product.test.automation.core.exception;

/**
 * O Objetivo desta classe é prover exceção para os steps dos cenários de testes.
 * 
 * @author msergio
 * @since Setembro de 2018
 * @version {version}
 */
public class StepsException extends Exception {

        private static final long serialVersionUID = -1483027845406929609L;

        public StepsException() {
                super();
        }

        public StepsException(String message) {
                super(message);
        }

        public StepsException(String message, Exception cause) {
                super(message, cause);
        }

        public StepsException(Exception cause) {
                super(cause);
        }

}