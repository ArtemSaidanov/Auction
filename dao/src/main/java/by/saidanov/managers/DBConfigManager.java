package by.saidanov.managers;

import by.saidanov.constants.DBConfigConstant;

import java.util.ResourceBundle;

/**
 * Description: Class provides work with database properties file
 *
 * @author Artiom Saidanov.
 */
public class DBConfigManager {

    private volatile static DBConfigManager instance;
    private final ResourceBundle bundle = ResourceBundle.getBundle(DBConfigConstant.DATABASE_SOURCE);

    private DBConfigManager() {
    }

    public static DBConfigManager getInstance() {
        if (instance == null) {
            synchronized (DBConfigManager.class) {
                if (instance == null) {
                    instance = new DBConfigManager();
                }
            }
        }
        return instance;
    }

    /**
     * @return property value from database.properties
     */
    public String getProperty(String key) {
        return bundle.getString(key);
    }
}
