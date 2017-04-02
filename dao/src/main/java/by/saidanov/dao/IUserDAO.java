package by.saidanov.dao;

import by.saidanov.auction.entities.User;
import by.saidanov.exceptions.DaoException;

import java.sql.Connection;

/**
 * Description: interface contains methods which only "user" table needs.
 *
 * @author Artiom Saidanov.
 */
public interface IUserDAO extends DAO<User> {

    /**
     * This method checks for the presence of this pair of login and password in "user" table
     *
     * @param login      entered in the "login" field by user
     * @param password   entered in the "password" field by user
     * @param connection transmitted from the service to provide transactions
     */
    boolean isAuthorized(String login, String password, Connection connection) throws DaoException;

    /**
     * This method returns User object by user login
     *
     * @param login      entered in the "login" field by user
     * @param connection transmitted from the service to provide transactions
     */
    User getByLogin(String login, Connection connection) throws DaoException;

    /**
     * This method provides uniqueness of users logins.
     *
     * @param login      entered in the "login" field by user
     * @param connection transmitted from the service to provide transactions
     * @return "false" if login already exists, "true" if login is new
     */
    boolean isNewUser(String login, Connection connection) throws DaoException;
}
