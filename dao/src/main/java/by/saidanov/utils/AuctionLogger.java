package by.saidanov.utils;


import org.apache.log4j.Logger;

/**
 * Description: this class provides logging in whole project.
 *
 * @author Artiom Saidanov.
 */
public class AuctionLogger {

    private volatile static AuctionLogger instance;
    private Logger logger;

    private AuctionLogger() {
    }

    public static AuctionLogger getInstance() {
        if (instance == null) {
            synchronized (AuctionLogger.class) {
                if (instance == null) {
                    instance = new AuctionLogger();
                }
            }
        }
        return instance;
    }

    public void log(Class sender, String message){
        logger = Logger.getLogger(sender);
        logger.error(message);
    }
}
