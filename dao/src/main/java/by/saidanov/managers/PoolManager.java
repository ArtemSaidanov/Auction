package by.saidanov.managers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;


/**
 * Description: hikari connection pool manager.
 *
 * @author Artiom Saidanov.
 */
public class PoolManager {

    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null){
            HikariConfig config = new HikariConfig();
            config.setDriverClassName(DBConfigManager.getInstance().getProperty("database.driver"));
            config.setJdbcUrl(DBConfigManager.getInstance().getProperty("database.url"));
            config.setUsername(DBConfigManager.getInstance().getProperty("database.user"));
            config.setPassword(DBConfigManager.getInstance().getProperty("database.password"));
            config.setMaximumPoolSize(100);

            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
}
