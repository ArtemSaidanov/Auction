import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.auction.entities.User;
import by.saidanov.dao.BaseDao;
import by.saidanov.dao.impl.BaseDaoImpl;
import by.saidanov.exceptions.DaoException;
import by.saidanov.utils.HibernateUtil;
import com.sun.corba.se.spi.servicecontext.UnknownServiceContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
public class BaseDAOTest {

    /**
     * This test firstly adds entity in table, then deletes this entity from table
     */
    @Test
    public void saveAndDeleteTest() {
        User user = User.builder()
                                .login("LoginHibTest")
                                .password("PassHibTest")
                                .firstName("NameHibTest")
                                .lastName("SurHibTest")
                                .accessLevel(0)
                                .build();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        try {
            /* Firstly add entity in table*/
            session.beginTransaction();
            baseDao.save(user);

            /* Secondly delete entity from table */
            session.delete(user);
            session.getTransaction().commit();
        } catch (DaoException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void getFromTableTest() {
        User expected = User.builder()
                                .id(1)
                                .accessLevel(0)
                                .firstName("Artem")
                                .lastName("Saidanov")
                                .login("Art")
                                .password("1234")
                                .build();

        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        try {
            User actual = (User) baseDao.get(User.class, expected.getId());
            Assert.assertEquals(expected, actual);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    /**
     * This test checks update method. Firstly test creates an entity, then updates it.
     * Finally test compares entity from table and entity from code
     */
    @Test
    public void updateEntityTest() {
        User user = User.builder()
                                .login("LoginNEW")
                                .password("PassNEW")
                                .firstName("NameNEW")
                                .lastName("SurnameNEW")
                                .accessLevel(0)
                                .build();

        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            baseDao.save(user);
            session.getTransaction().commit();

            session.beginTransaction();
            user.setLogin("LoginUpdated");
            baseDao.update(user);
            session.getTransaction().commit();

            session.beginTransaction();
            baseDao.delete(user);
            session.getTransaction().commit();
        } catch (DaoException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test(expected = DaoException.class)
    public void getNullFromTableTest() throws DaoException {
        int unrealId = 1000000000;
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        baseDao.get(User.class, unrealId);
    }
}