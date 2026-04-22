package Exceptions;


import java.util.logging.Logger;

public class GlobalHandler implements Thread.UncaughtExceptionHandler  {

    private static Logger LOGGER = Logger.getLogger(GlobalHandler.class.getName());

    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.severe("Unhandled exception in thread " + t.getName() + ": " + e.getMessage());
    }
}
