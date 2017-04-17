package by.saidanov.dao;

import by.saidanov.auction.entities.User;
import by.saidanov.exceptions.DaoException;
import org.hibernate.Session;

/**
 * Description: interface contains methods which only "user" table needs
 *
 * @author Artiom Saidanov.
 */
public interface IUserDAO {

    /**
     * This method checks for the presence of this pair of login and password in "user" table
     *
     * @param login    - entered in the "login" field by user
     * @param password - entered in the "password" field by user
     * @return true if pair of login and password exists
     * @throws DaoException - throws an exception on the service layer
     */
    boolean isAuthorized(String login, String password) throws DaoException;

    /**
     * This method returns User entity by user login
     *
     * @param login   - entered in the "login" field by user
     * @return User entity
     * @throws DaoException - throws an exception on the service layer
     */
    User getByLogin(String login) throws DaoException;

    /**
     * This method provides uniqueness of users logins.
     *
     * @param login   - entered in the "login" field by user
     * @return "false" if login already exists, "true" if login is new
     */
    boolean isNewUser(String login) throws DaoException;
}
