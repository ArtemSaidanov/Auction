package by.saidanov.dao.impl;

import by.saidanov.auction.entities.Account;
import by.saidanov.dao.BaseDao;
import by.saidanov.dao.IAccountDAO;
import by.saidanov.exceptions.DaoException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Description: this class contains implementation of specific methods which only "account" table needs
 *
 * @author Artiom Saidanov.
 */
public class AccountDaoImpl extends BaseDaoImpl implements IAccountDAO {

    private volatile static AccountDaoImpl instance;

    private AccountDaoImpl() {
    }

    public static AccountDaoImpl getInstance() {
        if (instance == null) {
            synchronized (AccountDaoImpl.class) {
                if (instance == null) {
                    instance = new AccountDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Account getByUserId(Integer userId) throws DaoException {
        Criteria criteria = getSession().createCriteria(Account.class);
        criteria.add(Restrictions.eq("userId", userId));
        List accounts = criteria.list();
        Account account = null;
        for (Object o : accounts) {
            account = (Account) o;
        }
        if (account == null){
            throw new DaoException("No account with this userId");
        }
        return account;
    }
}
