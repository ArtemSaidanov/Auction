package by.saidanov.dao.impl;

import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.Lot;
import by.saidanov.constants.SQLQuery;
import by.saidanov.dao.IAccountDAO;
import by.saidanov.exceptions.DaoException;
import by.saidanov.utils.AuctionLogger;
import by.saidanov.utils.ClosingUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Description: this class works with "account" table
 *
 * @author Artiom Saidanov.
 */
public class AccountDAO implements IAccountDAO {

    private volatile static AccountDAO instance;

    private static String message1;

    private AccountDAO() {
    }

    public static AccountDAO getInstance() {
        if (instance == null) {
            synchronized (AccountDAO.class) {
                if (instance == null) {
                    instance = new AccountDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public void add(Account account, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLQuery.ADD_ACCOUNT);
            statement.setInt(1, account.getUserId());
            statement.setInt(2, account.getAmountOfMoney());
            statement.execute();
        } catch (SQLException e) {
            String message = "Unable to add the user account";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
        }
    }

    @Override
    public Account getById(int id, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Account account;

        try {
            statement = connection.prepareStatement(SQLQuery.GET_ACCOUNT_BY_ID);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            account = getAccount(resultSet);
        } catch (SQLException e) {
            String message = "Unable to get account";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
            ClosingUtil.close(resultSet);
        }
        return account;
    }

    /**
     * This method gets account by user id
     */
    public Account getByUserId(int userId, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Account account;

        try {
            statement = connection.prepareStatement(SQLQuery.GET_ACCOUNT_BY_USER_ID);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            account = getAccount(resultSet);
        } catch (SQLException e) {
            String message = "Unable to get account";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
            ClosingUtil.close(resultSet);
        }
        return account;
    }

    @Override
    public void update(Account account, Connection connection) throws DaoException{
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLQuery.UPDATE_ACCOUNT_AMOUNT_OF_MONEY);
            statement.setInt(1, account.getAmountOfMoney());
            statement.setInt(2, account.getUserId());
            statement.execute();
        } catch (SQLException e) {
            String message = "Unable to get account";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
        }
    }

    private Account getAccount(ResultSet resultSet) throws SQLException {
        Account account = Account.builder().build();
        while (resultSet.next()) {
            account.setId(resultSet.getInt("id"));
            account.setUserId(resultSet.getInt("user_id"));
            account.setAmountOfMoney(resultSet.getInt("amount_of_money"));
        }
        return account;
    }

    /**
     * This method deletes account from database
     */
    @Override
    public void delete(int userId, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLQuery.DELETE_ACCOUNT);
            statement.setInt(1, userId);
            statement.execute();
        } catch (SQLException e) {
            String message = "Unable to delete account";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
        }
    }

    /**
     * NO USAGES
     */
    @Override
    public int getMaxId(Connection connection) throws DaoException {
        return 0;
    }

    /**
     * NO USAGES
     */
    @Override
    public List<Account> getAll(Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Account> accountList;
        return null;
    }


}
