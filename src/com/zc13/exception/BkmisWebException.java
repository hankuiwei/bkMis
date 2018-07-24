package com.zc13.exception;

/**
 * Any unchecked exception that will be thrown when a Metafacade processing error occurs.
 */
public class BkmisWebException
    extends RuntimeException
{
    /**
     * Constructs an instance of MetafacadeException.
     *
     * @param parent
     */
    public BkmisWebException(Throwable parent)
    {
        super(parent);
    }

    /**
     * Constructs an instance of MetafacadeException.
     *
     * @param message
     */
    public BkmisWebException(String message)
    {
        super(message);
    }

    /**
     * Constructs an instance of MetafacadeException.
     *
     * @param message
     * @param parent
     */
    public BkmisWebException(
        String message,
        Throwable parent)
    {
        super(message, parent);
    }
}