import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.auction.entities.User;
import by.saidanov.dao.BaseDao;
import by.saidanov.dao.impl.BaseDaoImpl;
import by.saidanov.exceptions.DaoException;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.AccountService;
import by.saidanov.services.impl.UserService;
import by.saidanov.utils.HibernateUtil;
import org.hibernate.*;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Random;

/**
 * Description: this class contains tests for AccountService
 *
 * @author Artiom Saidanov.
 */
public class AccountServiceTest {

    @Test
    public void updateTest() {
        Account account = Account.builder()
                                .amountOfMoney(9999999)
                                .build();

        User user = User.builder()
                                .accessLevel(0)
                                .firstName("UserWithAcc")
                                .lastName("UserWithAcc")
                                .login("UserWithAcc")
                                .password("UserWithAcc")
                                .account(account)
                                .build();
        account.setUser(user);
        user.setAccount(account);
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            baseDao.save(user);
            session.getTransaction().commit();

            account.setAmountOfMoney(1);
            AccountService.getInstance().update(account);

            session.beginTransaction();
            baseDao.delete(account);
            session.getTransaction().commit();
        } catch (ServiceException e) {
            session.getTransaction().rollback();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test(expected = TransactionException.class)
    public void updateTestWithException() throws ServiceException {
        Account accWithNullFields = Account.builder().build();
        AccountService.getInstance().update(accWithNullFields);
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void getByUserIdTest() {
        int userId = (int) (Math.random() * 50000 + 100000000);
        Account expected = Account.builder()
                                .userId(userId)
                                .amountOfMoney(18999)
                                .build();
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            session.replicate(expected, ReplicationMode.EXCEPTION);
            session.getTransaction().commit();

            Account actual = AccountService.getInstance().getByUserId(expected.getUserId());

            Assert.assertEquals(expected, actual);
            session.beginTransaction();
            HibernateUtil.getHibernateUtil().getSession().delete(expected);
            session.getTransaction().commit();
        } catch (ServiceException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test(expected = ServiceException.class)
    public void getByUserTestWithException() throws ServiceException {
        Account accWithNullFields = Account.builder().userId(1234567).build();
        AccountService.getInstance().getByUserId(accWithNullFields.getId());
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void putMoneyTest() {
        int userId = (int) (Math.random() * 50000 + 100000000);
        Account expectedAccount = Account.builder()
                                .userId(userId)
                                .amountOfMoney(10)
                                .build();
        int moneyToPut = 2;

        HibernateUtil.getHibernateUtil().closeSession();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            session.replicate(expectedAccount, ReplicationMode.EXCEPTION);
            session.getTransaction().commit();
            AccountService.getInstance().putMoney(expectedAccount, moneyToPut);

            int expectedMoney = expectedAccount.getAmountOfMoney();

            int actualMoney = AccountService.getInstance().getByUserId(expectedAccount.getUserId()).getAmountOfMoney();
            Assert.assertEquals(expectedMoney, actualMoney);
            AccountService.getInstance().delete(expectedAccount);
        } catch (SQLException | ServiceException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void takeMoneyTest(){
        int userId = (int) (Math.random() * 50000 + 100000000);
        Account expectedAccount = Account.builder()
                                .userId(userId)
                                .amountOfMoney(10)
                                .build();
        int moneyToTake = 2;

        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            session.replicate(expectedAccount, ReplicationMode.EXCEPTION);
            session.getTransaction().commit();
            AccountService.getInstance().takeMoney(expectedAccount, moneyToTake);

            int expectedMoney = expectedAccount.getAmountOfMoney();

            int actualMoney = AccountService.getInstance().getByUserId(expectedAccount.getUserId()).getAmountOfMoney();
            Assert.assertEquals(expectedMoney, actualMoney);
            AccountService.getInstance().delete(expectedAccount);
        } catch (SQLException | ServiceException e) {
            if (session.getTransaction().isActive()){
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    //TODO узнать про StaleStateException

    @Test(expected = Exception.class)
    public void takeMoneyTestWithException() throws ServiceException {
        Account accWithNullFields = Account.builder().userId(1234567).amountOfMoney(100).build();
        int moneyToPut = 100;
        AccountService.getInstance().putMoney(accWithNullFields, moneyToPut);
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test(expected = Exception.class)
    public void putMoneyTestWithException() throws ServiceException {
        Account accWithNullFields = Account.builder().userId(1234567).amountOfMoney(100).build();
        int moneyToPut = 100;
        AccountService.getInstance().putMoney(accWithNullFields, moneyToPut);
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void getInstanceTest(){
        AccountService accountService = AccountService.getInstance();
        Assert.assertNotNull(accountService);
    }
}
