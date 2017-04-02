package by.saidanov.dao.impl;

import by.saidanov.auction.entities.User;
import by.saidanov.constants.ColumnName;
import by.saidanov.constants.SQLQuery;
import by.saidanov.dao.IUserDAO;
import by.saidanov.exceptions.DaoException;
import by.saidanov.managers.PoolManager;
import by.saidanov.utils.AuctionLogger;
import by.saidanov.utils.ClosingUtil;

import java.sql.*;
import java.util.List;

/**
 * Description: this class works with "user" table
 *
 * @author Artiom Saidanov.
 */
public class UserDAO implements IUserDAO {

    private volatile static UserDAO instance;

    private static String message;

    private UserDAO() {
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            synchronized (UserDAO.class) {
                if (instance == null) {
                    instance = new UserDAO();
                }
            }
        }
        return instance;
    }

    @Override
    public void add(User user, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLQuery.ADD_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, user.getAccessLevel());
            statement.executeUpdate();
        } catch (SQLException e) {
            String message = "Unable to add the user account ";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(statement);
        }
    }

    @Override
    public int getMaxId(Connection connection) throws DaoException {
        int lastId = -1;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            statement = connection.prepareStatement(SQLQuery.GET_LAST_USER_ID);
            result = statement.executeQuery();
            while (result.next()) {
                lastId = result.getInt(1);
            }
        } catch (SQLException e) {
            String message = "Unable to return max id of accounts ";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return lastId;
    }

    /**
     * This method gets max id from user table and increments it on one. It needed because connection autoCommit is false
     * in UserService add() method. So when we add account to user we must have user id but id database we have only
     * previous id and we must increment it.
     */
    public int getMaxIdPlusOne(Connection connection) throws DaoException {
        int lastId = -1;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            statement = connection.prepareStatement(SQLQuery.GET_LAST_USER_ID);
            result = statement.executeQuery();
            while (result.next()) {
                lastId = result.getInt(1) + 1;
            }
        } catch (SQLException e) {
            String message = "Unable to return max id of accounts ";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return lastId;
    }

    @Override
    public boolean isAuthorized(String login, String password, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet result = null;
        boolean isLogIn = false;
        try {
            statement = connection.prepareStatement(SQLQuery.CHECK_AUTHORIZATION);
            statement.setString(1, login);
            statement.setString(2, password);
            result = statement.executeQuery();
            if (result.next()) {
                isLogIn = true;
            }

        } catch (SQLException e) {
            String message = "Unable to check the user";
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return isLogIn;
    }

    @Override
    public User getByLogin(String login, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet result = null;
        User user = null;
        try {
            statement = connection.prepareStatement(SQLQuery.GET_USER_BY_LOGIN);
            statement.setString(1, login);
            result = statement.executeQuery();
            while (result.next()) {
                user = buildUser(result);
            }
        } catch (SQLException e) {
            String message = "Unable to return user ";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(result);
            ClosingUtil.close(statement);
        }
        return user;
    }

    @Override
    public boolean isNewUser(String login, Connection connection) throws DaoException {
        boolean isNew = true;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SQLQuery.CHECK_LOGIN);
            statement.setString(1, login);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isNew = false;
            }
        } catch (SQLException e) {
            String message = "Unable to check user";
            AuctionLogger.getInstance().log(getClass(), message);
            throw new DaoException(message, e);
        } finally {
            ClosingUtil.close(resultSet);
            ClosingUtil.close(statement);
        }
        return isNew;
    }

    private User buildUser(ResultSet result) throws SQLException {
        int id = result.getInt(ColumnName.USER_ID);
        String firstName = result.getString(ColumnName.USER_FIRST_NAME);
        String lastName = result.getString(ColumnName.USER_LAST_NAME);
        String login = result.getString(ColumnName.USER_LOGIN);
        String password = result.getString(ColumnName.USER_PASSWORD);
        int accessLevel = result.getInt(ColumnName.USER_ACCESS_LEVEL);
        return User.builder()
                                .id(id)
                                .login(login)
                                .password(password)
                                .firstName(firstName)
                                .lastName(lastName)
                                .accessLevel(accessLevel)
                                .build();
    }

    /**
     * Deletes user and his account from database
     */
    @Override
    public void delete(int id, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQLQuery.DELETE_USER);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            String message = "Unable to delete user";
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
    public List<User> getAll(Connection connection) throws DaoException {
        return null;
    }

    /**
     * NO USAGES
     */
    @Override
    public User getById(int id, Connection connection) throws DaoException {
        return null;
    }
}
