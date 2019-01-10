package br.gov.serpro.sterna.dao.exceptions;

import java.util.logging.Logger;

/**
 *
 * @author SERPRO
 */
public class NonexistentEntityException extends Exception {

    /**
     *
     * @param message
     * @param cause
     */
    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param message
     */
    public NonexistentEntityException(String message) {
        super(message);
    }
    private static final Logger LOG = Logger.getLogger(NonexistentEntityException.class.getName());
}
