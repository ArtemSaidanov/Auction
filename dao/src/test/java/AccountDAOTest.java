import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.auction.entities.User;
import by.saidanov.dao.BaseDao;
import by.saidanov.dao.impl.AccountDaoImpl;
import by.saidanov.dao.impl.BaseDaoImpl;
import by.saidanov.exceptions.DaoException;
import by.saidanov.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.id.IdentifierGenerationException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
public class AccountDAOTest {

    /**
     * This test checks: adding an account, getting an account and deleting an account from table
     */
    @Test
    public void accountDaoTest() {
        Account account = Account.builder()
                                .amountOfMoney(500)
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
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();

        try {
            session.beginTransaction();
            baseDao.save(user);
            session.getTransaction().commit();

            session.beginTransaction();
            account.getId();
            Account actual = (Account) baseDao.get(Account.class, account.getId());
            session.getTransaction().commit();

            Assert.assertEquals(account, actual);

            session.beginTransaction();
            baseDao.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test(expected = IdentifierGenerationException.class)
    public void singleAccountAddTest() {
        Account account = Account.builder()
                                .amountOfMoney(500)
                                .build();
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            baseDao.save(account);
            session.getTransaction().commit();
        } catch (DaoException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void getByUserIdTest() {
        Account account = Account.builder()
                                .amountOfMoney(500)
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
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
//            session.getTransaction().begin();
            baseDao.save(account);
            session.getTransaction().commit();

            Account actual = AccountDaoImpl.getInstance().getByUserId(user.getId());
            Assert.assertEquals(account, actual);

            session.getTransaction().begin();
            baseDao.delete(user);
            session.getTransaction().commit();
        } catch (DaoException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }
}
