package by.saidanov.services.impl;

import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.User;
import by.saidanov.auction.util.UserType;
import by.saidanov.dao.impl.AccountDAO;
import by.saidanov.dao.impl.UserDAO;
import by.saidanov.exceptions.DaoException;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.managers.PoolManager;
import by.saidanov.utils.AuctionLogger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description: contains business-logic and provides transactional work for user entity
 *
 * @author Artiom Saidanov.
 */
public class UserService {

    private volatile static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    /**
     * This method checks user login and password
     */
    public boolean checkUserAuthorization(String login, String password) throws SQLException, ServiceException {
        boolean isAuthorized;
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            isAuthorized = UserDAO.getInstance().isAuthorized(login, password, connection);
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return isAuthorized;
    }

    /**
     * This method gets user by his login. All logins in application are unique
     */
    public User getUserByLogin(String login) throws SQLException, ServiceException {
        User user;
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            user = UserDAO.getInstance().getByLogin(login, connection);
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return user;
    }

    /**
     * This method checks uniqueness of user login
     */
    public boolean checkIsUserNew(User user) throws SQLException, ServiceException {
        boolean isNew = false;
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            if (UserDAO.getInstance().isNewUser(user.getLogin(), connection)) {
                isNew = true;
            }
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return isNew;
    }

    /**
     * This method adds user and user's account to database when user passes the registration
     */
    public void add(User user, Account account) throws SQLException, ServiceException {
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            UserDAO.getInstance().add(user, connection);
            int userId = UserDAO.getInstance().getMaxIdPlusOne(connection);
            account.setUserId(userId);
            AccountService.getInstance().add(account);
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * This method separates client and admins
     */
    public UserType checkAccessLevel(User user) {
        UserType userType;
        if (UserType.CLIENT.ordinal() == user.getAccessLevel()) {
            userType = UserType.CLIENT;
        } else {
            userType = UserType.ADMIN;
        }
        return userType;
    }

    public void delete(User user) throws SQLException, ServiceException {
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            UserDAO.getInstance().delete(user.getId(), connection);
            AccountService.getInstance().delete(user.getId());
            LotService.getInstance().delete(user);
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
