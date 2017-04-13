import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.entities.User;
import by.saidanov.dao.BaseDao;
import by.saidanov.dao.ILotDAO;
import by.saidanov.dao.impl.BaseDaoImpl;
import by.saidanov.dao.impl.LotDAOImpl;
import by.saidanov.exceptions.DaoException;
import by.saidanov.utils.HibernateUtil;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
public class LotDAOTest {

    @Test
    public void lotTest() {
        User user = User.builder()
                                .accessLevel(0)
                                .firstName("UserWithLot")
                                .lastName("UserWithLot")
                                .login("UserWithLot")
                                .password("UserWithLot")
                                .build();
        Set<Lot> lots = getLotSet(user);
        user.getLots().addAll(lots);

        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();

        try {
            session.getTransaction().begin();
            baseDao.save(user);
//            for (Lot lot : lots) {
//                baseDao.save(lot, session);
//            }
            session.getTransaction().commit();

            session.getTransaction().begin();
            baseDao.delete(user);
            session.getTransaction().commit();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void getAllLotsExceptUserLots() {
        User user = User.builder()
                                .accessLevel(0)
                                .firstName("UserWithLot")
                                .lastName("UserWithLot")
                                .login("UserWithLot")
                                .password("UserWithLot")
                                .build();
        Set<Lot> lots = getLotSet(user);
        user.getLots().addAll(lots);


        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();

        try {
            session.beginTransaction();
            baseDao.save(user);
            session.getTransaction().commit();

            List<Lot> expectedList = new ArrayList<>();
            expectedList.addAll(lots);

            LotDAOImpl lotDao = by.saidanov.dao.impl.LotDAOImpl.getInstance();
            //TODO may be problems
            List<Lot> actualList = lotDao.getAll(user, 0);

            session.beginTransaction();
            baseDao.delete(user);
            session.getTransaction().commit();

            Assert.assertNotEquals(expectedList, actualList);
        } catch (DaoException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void getAllLotsTest() {
        User user = User.builder()
                                .accessLevel(0)
                                .firstName("UserWithLot")
                                .lastName("UserWithLot")
                                .login("UserWithLot")
                                .password("UserWithLot")
                                .build();
        Set<Lot> lots = getLotSet(user);
        user.getLots().addAll(lots);


        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();

        try {
            session.beginTransaction();
            baseDao.save(user);
            session.getTransaction().commit();

            ILotDAO lotDao = by.saidanov.dao.impl.LotDAOImpl.getInstance();
            List<Lot> actualList = lotDao.getAll();

            session.beginTransaction();
            baseDao.delete(user);
            session.getTransaction().commit();

            Assert.assertNotNull(actualList);
            Assert.assertFalse(actualList.isEmpty());
        } catch (DaoException e) {
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void deleteLotTest() {
        User user = User.builder()
                                .accessLevel(0)
                                .firstName("UserWithLot")
                                .lastName("UserWithLot")
                                .login("UserWithLot")
                                .password("UserWithLot")
                                .build();
        Set<Lot> lots = getLotSet(user);
        user.getLots().addAll(lots);


        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();

        try {
            session.beginTransaction();
            baseDao.save(user);

            ILotDAO lotDao = LotDAOImpl.getInstance();
            List<Lot> actualList = lotDao.getAll();

            Lot lotToDelete = actualList.get(0);
            lotDao.delete(lotToDelete.getId());

            baseDao.delete(user);
            session.getTransaction().commit();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void getByIdTest() {
        User user = User.builder()
                                .accessLevel(0)
                                .firstName("UserWithLot")
                                .lastName("UserWithLot")
                                .login("UserWithLot")
                                .password("UserWithLot")
                                .build();
        Set<Lot> lots = getLotSet(user);
        user.getLots().addAll(lots);


        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            baseDao.save(user);

            ILotDAO lotDao = by.saidanov.dao.impl.LotDAOImpl.getInstance();
            List<Lot> actualList = lotDao.getAll();

            Lot lotToGet = actualList.get(0);

            Lot actual = lotDao.getById(lotToGet.getId());

            Assert.assertEquals(lotToGet, actual);
            baseDao.delete(user);
            session.getTransaction().commit();
        } catch (DaoException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void getUserLotsTest(){
        User user = User.builder()
                                .accessLevel(0)
                                .firstName("UserWithLot")
                                .lastName("UserWithLot")
                                .login("UserWithLot")
                                .password("UserWithLot")
                                .build();
        Set<Lot> expectedSet = getLotSet(user);
        user.getLots().addAll(expectedSet);


        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            baseDao.save(user);

            ILotDAO lotDao = by.saidanov.dao.impl.LotDAOImpl.getInstance();
            Set<Lot> actualSet = new HashSet<>();
            actualSet.addAll(lotDao.getUserLots(user));

            baseDao.delete(user);
            session.getTransaction().commit();
            HibernateUtil.getHibernateUtil().closeSession();

            Assert.assertTrue(actualSet.containsAll(expectedSet));
        } catch (DaoException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Test
    public void getInstanceTest(){
        LotDAOImpl lotDao = by.saidanov.dao.impl.LotDAOImpl.getInstance();
        Assert.assertTrue(lotDao != null);
    }

    @Ignore
    @Test
    public void getRowCountTest(){
//        LotDAOImpl lotDAO = LotDAOImpl.getInstance();
//        try {
//            Integer rowCount = lotDAO.getRowCount();
//            Integer i = 12;
//            Assert.assertEquals(i, rowCount);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
    }

    private Set<Lot> getLotSet(User user) {
        Lot lot = Lot.builder()
                                .name("TestLot1")
                                .description("TestLot1")
                                .minPrice(10)
                                .currentPrice(100)
                                .priceCutStep(10)
                                .quantity(15)
                                .startPrice(100)
                                .timeToNextCut(10)
                                .isOpen(true)
                                .isNew(true)
                                .user(user)
                                .build();

        Lot lot2 = Lot.builder()
                                .name("TestLot2")
                                .description("TestLot2")
                                .minPrice(10)
                                .currentPrice(100)
                                .priceCutStep(10)
                                .quantity(15)
                                .startPrice(100)
                                .timeToNextCut(10)
                                .isOpen(true)
                                .isNew(true)
                                .user(user)
                                .build();

        Lot lot3 = Lot.builder()
                                .name("TestLot3")
                                .description("TestLot3")
                                .minPrice(10)
                                .currentPrice(100)
                                .priceCutStep(10)
                                .quantity(15)
                                .startPrice(100)
                                .timeToNextCut(10)
                                .isOpen(true)
                                .isNew(true)
                                .user(user)
                                .build();

        Set<Lot> lots = new HashSet<>();
        lots.add(lot);
        lots.add(lot2);
        lots.add(lot3);
        return lots;
    }
}

