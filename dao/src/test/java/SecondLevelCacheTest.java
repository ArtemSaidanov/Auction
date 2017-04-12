import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.entities.User;
import by.saidanov.dao.BaseDao;
import by.saidanov.dao.impl.BaseDaoImpl;
import by.saidanov.exceptions.DaoException;
import by.saidanov.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import java.util.Set;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
public class SecondLevelCacheTest {

    @Test
    public void secondLevelCacheTest() {
        User user = User.builder()
                                .login("secondLevelCacheTest")
                                .password("secondLevelCacheTest")
                                .firstName("secondLevelCacheTest")
                                .lastName("secondLevelCacheTest")
                                .accessLevel(0)
                                .build();

        Session firstSession = HibernateUtil.getHibernateUtil().getSession();
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Transaction transaction = null;
        try {
            transaction = firstSession.beginTransaction();
            baseDao.save(user);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();

        cacheTest(user, baseDao);
        cacheTest(user, baseDao);
        cacheTest(user, baseDao);
        cacheTest(user, baseDao);
        cacheTest(user, baseDao);

        Session sessionToDelete = HibernateUtil.getHibernateUtil().getSession();
        try {
            sessionToDelete.beginTransaction();
            baseDao.delete(user);
            sessionToDelete.getTransaction().commit();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    private void cacheTest(User user, BaseDao<EntityMarker> baseDao) {
        Session secondSession = HibernateUtil.getHibernateUtil().getSession();
        try {
            secondSession.beginTransaction();
            baseDao.get(User.class, user.getId());

            secondSession.getTransaction().commit();
        } catch (DaoException e) {
            secondSession.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }
}
