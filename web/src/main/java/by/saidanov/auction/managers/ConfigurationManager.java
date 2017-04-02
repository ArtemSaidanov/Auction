package by.saidanov.auction.managers;

import by.saidanov.auction.constants.ConfigConstant;

import java.util.ResourceBundle;

/**
 * Description: this class works with application configuration
 *
 * @author Artiom Saidanov.
 */
public class ConfigurationManager {

    private volatile static ConfigurationManager instance;
    private final ResourceBundle bundle = ResourceBundle.getBundle(ConfigConstant.CONFIGS_SOURCE);

    private ConfigurationManager() {}

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }

    /** @return path.page from config.properties*/
    public String getProperty(String key){
        return bundle.getString(key);
    }
}
