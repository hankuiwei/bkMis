package com.zc13.exception;


/**
 * Any unchecked exception that will be thrown when a Metafacade processing error occurs.
 */
public class BkmisDAOException
    extends RuntimeException
{
    /**
     * Constructs an instance of MetafacadeException.
     *
     * @param parent
     */
    public BkmisDAOException(Throwable parent)
    {
        super(parent);
    }

    /**
     * Constructs an instance of MetafacadeException.
     *
     * @param message
     */
    public BkmisDAOException(String message)
    {
        super(message);
    }

    /**
     * Constructs an instance of MetafacadeException.
     *
     * @param message
     * @param parent
     */
    public BkmisDAOException(
        String message,
        Throwable parent)
    {
        super(message, parent);
    }
}
