package br.gov.serpro.sterna.dao.exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author SERPRO
 */
public class IllegalOrphanException extends Exception {
    private final List<String> messages;

    /**
     *
     * @param messages
     */
    public IllegalOrphanException(List<String> messages) {
        super((messages != null && messages.size() > 0 ? messages.get(0) : null));
        if (messages == null) {
            this.messages = new ArrayList<>();
        }
        else {
            this.messages = messages;
        }
    }

    /**
     *
     * @return
     */
    public List<String> getMessages() {
        return Collections.unmodifiableList(messages);
    }
    private static final Logger LOG = Logger.getLogger(IllegalOrphanException.class.getName());
}
