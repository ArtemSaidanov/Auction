import by.saidanov.auction.entities.User;
import by.saidanov.dao.IUserDAO;
import by.saidanov.dao.impl.UserDAOImpl;
import by.saidanov.exceptions.DaoException;
import by.saidanov.utils.HibernateUtil;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;

/**
 * Description: this class contains test on {{@link IUserDAO}} interface methods
 *
 * @author Artiom Saidanov.
 */
public class UserDAOTest {

    @Test
    public void isAuthorizedTest() {
        String nonexistentLogin = "badLogin";
        String nonexistentPassword = "badPassword";
        String existLogin = "Art";
        String existPass = "1234";
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            boolean authorized = UserDAOImpl.getInstance().isAuthorized(nonexistentLogin, nonexistentPassword);
            session.getTransaction().commit();
            Assert.assertFalse(authorized);

            session.beginTransaction();
            authorized = UserDAOImpl.getInstance().isAuthorized(existLogin, existPass);
            session.getTransaction().commit();
            Assert.assertTrue(authorized);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void getByLoginTest() {
        String badLogin = "bodLogin";
        String goodLogin = "Art";
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            User nullUser = UserDAOImpl.getInstance().getByLogin(badLogin);
            User notNullUser = UserDAOImpl.getInstance().getByLogin(goodLogin);
            session.getTransaction().commit();
            Assert.assertNotNull(notNullUser);
            Assert.assertNull(nullUser);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void isNewUserTest() {
        String newUserLogin = "bodLogin";
        String oldUserLogin = "Art";
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            boolean newUser = UserDAOImpl.getInstance().isNewUser(newUserLogin);
            boolean oldUser = UserDAOImpl.getInstance().isNewUser(oldUserLogin);
            session.getTransaction().commit();
            Assert.assertTrue(newUser);
            Assert.assertFalse(oldUser);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }
}
