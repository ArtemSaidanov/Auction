import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.entities.User;
import by.saidanov.dao.BaseDao;
import by.saidanov.dao.impl.BaseDaoImpl;
import by.saidanov.exceptions.DaoException;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.LotService;
import by.saidanov.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
public class LotServiceTest {

    @Test
    public void addTestAndGetByIdTest() {
        Lot expected = Lot.builder()
                                .name("TEST LOT")
                                .description("TEST LOT")
                                .currentPrice(10000)
                                .startPrice(10000)
                                .minPrice(1000)
                                .quantity(10)
                                .priceCutStep(100)
                                .timeToNextCut(15)
                                .isOpen(true)
                                .isNew(false)
                                .build();
        User user = User.builder()
                                .accessLevel(0)
                                .firstName("UserWithLot")
                                .lastName("UserWithLot")
                                .login("UserWithLot")
                                .password("UserWithLot")
                                .build();
        user.getLots().add(expected);
        expected.setUser(user);

        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            baseDao.save(user);
            session.getTransaction().commit();

            LotService.getInstance().add(expected);
            Lot actual = LotService.getInstance().getById(expected.getId());

            Assert.assertEquals(expected, actual);

            session.beginTransaction();
            expected.setUser(null);
            user.setLots(null);
            baseDao.update(user);
            baseDao.delete(expected);
            baseDao.delete(user);
            session.getTransaction().commit();
        } catch (ServiceException | DaoException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

//    @Test(expected = ServiceException.class)
//    public void addNullLotTes() throws SQLException, ServiceException {
//        Lot lot = Lot.builder().build();
//        Session session = HibernateUtil.getHibernateUtil().getSession();
//        LotService.getInstance().add(lot, session);
//    }

    @Test
    public void getAllLotsTest() {
        Lot expected = Lot.builder()
                                .name("TEST LOT")
                                .description("TEST LOT")
                                .currentPrice(10000)
                                .startPrice(10000)
                                .minPrice(1000)
                                .quantity(10)
                                .priceCutStep(100)
                                .timeToNextCut(15)
                                .isOpen(true)
                                .isNew(false)
                                .build();

        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            baseDao.save(expected);
            session.getTransaction().commit();

            List<Lot> lotList = LotService.getInstance().getAll();
            Assert.assertNotNull(lotList);

            session.beginTransaction();
            baseDao.delete(expected);
            session.getTransaction().commit();
        } catch (ServiceException | DaoException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void getAllUsersLotsTest() {
        User user = User.builder().id(1).build();
        User badIdUser = User.builder().id(1234567891).build();
        Lot expected = Lot.builder()
                                .name("TEST LOT")
                                .description("TEST LOT")
                                .currentPrice(10000)
                                .startPrice(10000)
                                .minPrice(1000)
                                .quantity(10)
                                .priceCutStep(100)
                                .timeToNextCut(15)
                                .isOpen(true)
                                .isNew(false)
                                .user(user)
                                .build();
        user.getLots().add(expected);

        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();
        try {
            session.beginTransaction();
            baseDao.save(expected);
            session.getTransaction().commit();

            List<Lot> lotList = LotService.getInstance().getUserLots(user);
            List<Lot> badLotList = LotService.getInstance().getUserLots(badIdUser);
            Assert.assertNotNull(lotList);
            Assert.assertTrue(badLotList.isEmpty());

            session.beginTransaction();
            baseDao.delete(expected);
            session.getTransaction().commit();
        } catch (ServiceException | DaoException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void getAllLotsExceptUsersLots() {
        User user = User.builder().id(1).build();
        Lot expected = Lot.builder()
                                .name("TEST LOT")
                                .description("TEST LOT")
                                .currentPrice(10000)
                                .startPrice(10000)
                                .minPrice(1000)
                                .quantity(10)
                                .priceCutStep(100)
                                .timeToNextCut(15)
                                .isOpen(true)
                                .isNew(false)
                                .user(user)
                                .build();
        user.getLots().add(expected);
        Account account = Account.builder().amountOfMoney(100).build();
        User userActual = User.builder()
                                .login("ActualUser")
                                .password("ActualUser")
                                .firstName("ActualUser")
                                .lastName("ActualUser")
                                .accessLevel(0)
                                .account(account)
                                .build();
        account.setUser(userActual);

        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();

        try {
            session.beginTransaction();
            baseDao.save(expected);
            baseDao.save(userActual);
            session.getTransaction().commit();

            List<Lot> lotList = LotService.getInstance().getAll(userActual);

            session.beginTransaction();
            baseDao.delete(expected);
            baseDao.delete(userActual);
            session.getTransaction().commit();

            Assert.assertNotNull(lotList);
            Assert.assertFalse(lotList.isEmpty());
        } catch (DaoException | ServiceException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }

    @Test
    public void buyLotTest() {
        User lotOwner = User.builder().id(1).build();
        Lot expected = Lot.builder()
                                .name("TEST LOT")
                                .description("TEST LOT")
                                .currentPrice(10000)
                                .startPrice(10000)
                                .minPrice(1000)
                                .quantity(2)
                                .priceCutStep(100)
                                .timeToNextCut(15)
                                .isOpen(true)
                                .user(lotOwner)
                                .build();
        lotOwner.getLots().add(expected);

        Account account = Account.builder().amountOfMoney(30000).build();
        User buyer = User.builder()
                                .login("BuyerUser")
                                .password("BuyerUser")
                                .firstName("BuyerUser")
                                .lastName("BuyerUser")
                                .accessLevel(0)
                                .account(account)
                                .build();
        account.setUser(buyer);

        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        Session session = HibernateUtil.getHibernateUtil().getSession();

        try {
            session.beginTransaction();
            baseDao.save(expected);
            baseDao.save(buyer);
            session.getTransaction().commit();

            LotService.getInstance().buyLot(expected, buyer, expected.getQuantity());

            Lot actual = LotService.getInstance().getById(expected.getId());
            Assert.assertEquals(expected, actual);

            session.beginTransaction();
            baseDao.delete(expected);
            baseDao.delete(buyer);
            session.getTransaction().commit();
        } catch (ServiceException | DaoException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        HibernateUtil.getHibernateUtil().closeSession();
    }
}
