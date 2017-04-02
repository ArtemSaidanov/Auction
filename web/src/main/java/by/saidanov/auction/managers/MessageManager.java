package by.saidanov.auction.managers;

import by.saidanov.auction.constants.ConfigConstant;

import java.util.ResourceBundle;

/**
 * Description: this class works with messages that will be shown to application users
 *
 * @author Artiom Saidanov.
 */
public class MessageManager {

    private volatile static MessageManager instance;
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstant.MESSAGES_SOURCE);

    private MessageManager() {}

    public static MessageManager getInstance() {
        if (instance == null) {
            synchronized (MessageManager.class) {
                if (instance == null) {
                    instance = new MessageManager();
                }
            }
        }
        return instance;
    }

    /** @return message from message.properties*/
    public String getProperty(String key){
        return bundle.getString(key);
    }
}
