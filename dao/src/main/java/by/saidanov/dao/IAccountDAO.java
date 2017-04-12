package by.saidanov.dao;

import by.saidanov.auction.entities.Account;
import by.saidanov.exceptions.DaoException;
import org.hibernate.Session;

/**
 * Description: interface contains methods which only "account" table needs
 *
 * @author Artiom Saidanov.
 */
public interface IAccountDAO {

    /**
     * This method return account by user id.
     * "account" table and "user" table has 1 to 1 relationship, so we can find account by userId
     *
     * @param userId unique user id
     * @return @{@link Account} object
     * @throws DaoException - throws an exception on the service layer
     */
    Account getByUserId(Integer userId) throws DaoException;
}
