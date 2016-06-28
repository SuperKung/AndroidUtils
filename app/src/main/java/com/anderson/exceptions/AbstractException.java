package com.anderson.exceptions;


import java.util.HashMap;
import java.util.Map;

public abstract class AbstractException extends RuntimeException {

    private static final long serialVersionUID = 7063914690303952076L;

    private Map<String, Object> parameters = new HashMap<>();

    /**
     * Constructor
     */
    public AbstractException() {
        super();
    }

    /**
     * @param message message
     * @param cause cause
     */
    public AbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message message
     */
    public AbstractException(String message) {
        super(message);
    }

    /**
     * @param cause cause
     */
    public AbstractException(Throwable cause) {
        super(cause);
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public Boolean hasParameter(String key) {
        return parameters.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public <E> E getParameter(String key) {
        return (E) parameters.get(key);
    }

    public void addParameter(String key, Object value) {
        parameters.put(key, value);
    }
}
