import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.entities.User;
import by.saidanov.dao.impl.LotDAO;
import by.saidanov.exceptions.DaoException;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.managers.PoolManager;
import by.saidanov.services.impl.LotService;
import org.junit.Assert;
import org.junit.Test;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
public class LotServiceTest {

    @Test
    public void addTest() {
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
                                .userId(1)
                                .isNew(false)
                                .build();
        try {
            LotService.getInstance().add(expected);
            Connection connection = PoolManager.getDataSource().getConnection();
            int lotId = LotDAO.getInstance().getMaxId(connection);
            expected.setId(lotId);
            Thread.sleep(500);
            Lot actual = LotService.getInstance().getById(lotId);
            Assert.assertEquals(expected, actual);
            LotService.getInstance().delete(lotId);
        } catch (SQLException | ServiceException | DaoException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ServiceException.class)
    public void addNullLotTes() throws SQLException, ServiceException{
        Lot lot = Lot.builder().build();
        LotService.getInstance().add(lot);
    }

    @Test
    public void getAllLotsTest() {
        try {
            List<Lot> lotList = LotService.getInstance().getAll();
            Assert.assertNotNull(lotList);
        } catch (SQLException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllUsersLotsTest() {
        User user = User.builder().id(1).build();
        User badIdUser = User.builder().id(1234567891).build();

        try {
            List<Lot> lotList = LotService.getInstance().getUserLots(user.getId());
            List<Lot> badLotList = LotService.getInstance().getUserLots(badIdUser.getId());
            Assert.assertNotNull(lotList);
            Assert.assertTrue(badLotList.isEmpty());
        } catch (SQLException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllLotsExceptUsersLots() {
        User user = User.builder().id(1).build();

        try {
            List<Lot> lotList = LotService.getInstance().getAll(user.getId());
            Assert.assertNotNull(lotList);
            Assert.assertFalse(lotList.isEmpty());
        } catch (SQLException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void buyLotTest() {
        int lotId;
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
                                .userId(1)
                                .build();
        User user = User.builder().id(expected.getUserId()).build();
        try {
            LotService.getInstance().add(expected);

            Connection connection = PoolManager.getDataSource().getConnection();
            lotId = LotDAO.getInstance().getMaxId(connection);
            expected.setId(lotId);

            LotService.getInstance().buyLot(expected, user);

            Lot actual = LotService.getInstance().getById(lotId);
            Assert.assertEquals(expected.getQuantity(), actual.getQuantity());
            LotService.getInstance().delete(lotId);
        } catch (SQLException | ServiceException | DaoException e) {
            e.printStackTrace();
        }

    }



}
