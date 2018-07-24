package com.zc13.exception;

/**
 * Any unchecked exception that will be thrown when a Metafacade processing error occurs.
 */
public class BkmisServiceException
    extends RuntimeException
{
    /**
     * Constructs an instance of MetafacadeException.
     *
     * @param parent
     */
    public BkmisServiceException(Throwable parent)
    {
        super(parent);
    }

    /**
     * Constructs an instance of MetafacadeException.
     *
     * @param message
     */
    public BkmisServiceException(String message)
    {
        super(message);
    }

    /**
     * Constructs an instance of MetafacadeException.
     *
     * @param message
     * @param parent
     */
    public BkmisServiceException(
        String message,
        Throwable parent)
    {
        super(message, parent);
    }
    
    public BkmisServiceException(){}
}
