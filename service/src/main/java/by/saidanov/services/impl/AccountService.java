package by.saidanov.services.impl;

import by.saidanov.auction.entities.Account;
import by.saidanov.dao.impl.AccountDAO;
import by.saidanov.dao.impl.LotDAO;
import by.saidanov.exceptions.DaoException;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.managers.PoolManager;
import by.saidanov.services.Service;
import by.saidanov.utils.AuctionLogger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Description: contains business-logic and provides transactional work for account entity
 *
 * @author Artiom Saidanov.
 */
public class AccountService {

    private volatile static AccountService instance;

    private AccountService() {
    }

    public static AccountService getInstance() {
        if (instance == null) {
            synchronized (AccountService.class) {
                if (instance == null) {
                    instance = new AccountService();
                }
            }
        }
        return instance;
    }

    public void add(Account entity) throws SQLException, ServiceException {
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            AccountDAO.getInstance().add(entity, connection);
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
     * This method gets account by id. It called when user try to take or put money on account
     */
    public Account getById(int id) throws SQLException, ServiceException {
        Connection connection = null;
        Account account = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            account = AccountDAO.getInstance().getById(id, connection);
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
        return account;
    }

    /**
     * This method updates account by changing amount of money on account
     */
    public void update(Account account) throws SQLException, ServiceException {
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            AccountDAO.getInstance().update(account, connection);
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
     * This method return account by user id.
     * "account" table and "user" table has 1 to 1 relationship, so we can find account by userId
     *
     * @param userId unique user id
     * @return @{@link Account} object
     */
    public Account getByUserId(int userId) throws SQLException, ServiceException {
        Connection connection = null;
        Account account = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            account = AccountDAO.getInstance().getByUserId(userId, connection);
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
        return account;
    }

    /**
     * This method takes money from client's account
     *
     * @param account     to take money from
     * @param moneyToTake amount of money
     */
    public void takeMoney(Account account, int moneyToTake) throws SQLException, ServiceException {
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            int amountOfMoney = account.getAmountOfMoney();
            account.setAmountOfMoney(amountOfMoney - moneyToTake);
            AccountDAO.getInstance().update(account, connection);
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
     * This method puts money on client's account
     *
     * @param account    to put money
     * @param moneyToPut amount of money
     */
    public void putMoney(Account account, int moneyToPut) throws SQLException, ServiceException {
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            int amountOfMoney = account.getAmountOfMoney();
            account.setAmountOfMoney(amountOfMoney + moneyToPut);
            AccountDAO.getInstance().update(account, connection);
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

    public void delete(int userId) throws SQLException, ServiceException {
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            AccountDAO.getInstance().delete(userId, connection);
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
