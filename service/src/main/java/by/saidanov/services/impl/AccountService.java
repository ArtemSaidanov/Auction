package by.saidanov.services.impl;

import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.dao.BaseDao;
import by.saidanov.dao.IAccountDAO;
import by.saidanov.dao.impl.AccountDaoImpl;
import by.saidanov.dao.impl.BaseDaoImpl;
import by.saidanov.exceptions.DaoException;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.Service;
import by.saidanov.utils.AuctionLogger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;

/**
 * Description: contains business-logic and provides transactional work for account entity
 *
 * @author Artiom Saidanov.
 */
public class AccountService implements Service {

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

    /**
     * This method updates account by changing amount of money on account
     */
    public void update(Account account) throws ServiceException {
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        try {
            getSession().beginTransaction();
            baseDao.update(account);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
    }

    /**
     * This method return account by user id.
     * "account" table and "user" table has 1 to 1 relationship, so we can find account by userId
     *
     * @param userId unique user id
     * @return @{@link Account} object
     */
    public Account getByUserId(Integer userId) throws ServiceException {
        IAccountDAO accountDAO = AccountDaoImpl.getInstance();
        Account account;
        try {
            getSession().beginTransaction();
            account = accountDAO.getByUserId(userId);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
        return account;
    }

    /**
     * This method takes money from client's account
     *
     * @param account     to take money from
     * @param moneyToTake amount of money
     */
    public void takeMoney(Account account, int moneyToTake) throws ServiceException {
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        try {
            account.setAmountOfMoney(account.getAmountOfMoney() - moneyToTake);
            getSession().beginTransaction();
            baseDao.update(account);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
    }

    /**
     * This method puts money on client's account
     *
     * @param account    to put money
     * @param moneyToPut amount of money
     */
    public void putMoney(Account account, int moneyToPut) throws ServiceException {
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        try {
            account.setAmountOfMoney(account.getAmountOfMoney() + moneyToPut);
            getSession().beginTransaction();
            baseDao.update(account);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
    }


    /**
     * This method is used only in test classes and for the needs of tests
     */
    public void add(Account account) throws ServiceException {
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        try {
            getSession().beginTransaction();
            baseDao.save(account);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
    }

    /**
     * This method is used only in test classes and for the needs of tests
     */
    public void delete(Account account) throws SQLException, ServiceException {
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        try {
            getSession().beginTransaction();
            baseDao.delete(account);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
    }
}
