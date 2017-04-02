import by.saidanov.auction.entities.Lot;
import by.saidanov.dao.impl.LotDAO;
import by.saidanov.exceptions.DaoException;
import by.saidanov.managers.PoolManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
public class LotDAOTest {

    private Connection connection;

    @Before
    public void createConnection() {
        try {
            connection = PoolManager.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addTest() {
        Lot lot = Lot.builder()
                                .userId(1)
                                .name("TEST")
                                .description("TEST")
                                .quantity(10)
                                .startPrice(1000)
                                .minPrice(100)
                                .currentPrice(1000)
                                .priceCutStep(100)
                                .timeToNextCut(5)
                                .isOpen(true)
                                .isNew(true)
                                .build();

        try {
            LotDAO.getInstance().add(lot, connection);
            int lotId = LotDAO.getInstance().getMaxId(connection);
            lot.setId(lotId);
            Lot actual = LotDAO.getInstance().getById(lotId, connection);
            Assert.assertEquals(lot, actual);
            LotDAO.getInstance().delete(lotId, connection);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = DaoException.class)
    public void addNullLotTest() throws DaoException {
        LotDAO.getInstance().add(null, connection);
    }

    @Test(expected = DaoException.class)
    public void addLotWithNullFieldTest() throws DaoException {
        Lot lot = Lot.builder()
                                .userId(1)
                                .name(null)
                                .build();
        LotDAO.getInstance().add(lot, connection);
    }

    @Test
    public void updateTest() {
        Lot lot = Lot.builder()
                                .userId(1)
                                .name("TEST")
                                .description("TEST")
                                .quantity(10)
                                .startPrice(1000)
                                .minPrice(100)
                                .currentPrice(1000)
                                .priceCutStep(100)
                                .timeToNextCut(5)
                                .isOpen(true)
                                .build();

        try {
            LotDAO.getInstance().add(lot, connection);
            int lotId = LotDAO.getInstance().getMaxId(connection);
            lot.setId(lotId);

            lot.setQuantity(3);
            lot.setCurrentPrice(333);
            LotDAO.getInstance().update(lot, connection);

            Lot actual = LotDAO.getInstance().getById(lotId, connection);
            Assert.assertEquals(lot, actual);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserLotsTest() {
        int userId = 1;
        try {
            List<Lot> lotList = LotDAO.getInstance().getUserLots(userId, connection);
            for (Lot lot : lotList) {
                Assert.assertEquals(1, lot.getUserId());
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllLotsTest() {
        try {
            List<Lot> lotList = LotDAO.getInstance().getAll(connection);
            Assert.assertFalse(lotList.isEmpty());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllLotsExceptUserLots() {
        int userId = 1;
        try {
            List<Lot> lotList = LotDAO.getInstance().getAll(userId, connection);
            for (Lot lot : lotList) {
                Assert.assertNotEquals(userId, lot.getUserId());
            }
            Assert.assertFalse(lotList.isEmpty());
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }


    @After
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
