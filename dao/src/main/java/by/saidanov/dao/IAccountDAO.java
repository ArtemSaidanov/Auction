package by.saidanov.dao;

import by.saidanov.auction.entities.Account;
import by.saidanov.exceptions.DaoException;

import java.sql.Connection;

/**
 * Description: interface contains methods which only "account" table needs.
 *
 * @author Artiom Saidanov.
 */
public interface IAccountDAO extends DAO<Account> {
    void update(Account account, Connection connection) throws DaoException;
}
