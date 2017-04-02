import by.saidanov.auction.entities.Account;
import by.saidanov.dao.impl.AccountDAO;
import by.saidanov.exceptions.DaoException;
import by.saidanov.managers.PoolManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
public class AccountDAOTest {

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
        int userId = (int) (Math.random() * 50000 + 100000000);
        Account account = Account.builder()
                                .userId(userId)
                                .amountOfMoney(1000)
                                .build();
        try {
            AccountDAO.getInstance().add(account, connection);
            Account actual = AccountDAO.getInstance().getByUserId(userId, connection);
            Assert.assertEquals(account.getUserId(), actual.getUserId());
            AccountDAO.getInstance().delete(userId, connection);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getByIdTest() {
        int userId = (int) (Math.random() * 50000 + 100000000);
        Account account = Account.builder()
                                .userId(userId)
                                .amountOfMoney(1000)
                                .build();
        try {
            AccountDAO.getInstance().add(account, connection);
            int accountId = AccountDAO.getInstance().getByUserId(userId, connection).getId();
            account.setId(accountId);
            Account actual = AccountDAO.getInstance().getById(accountId, connection);
            Assert.assertEquals(account, actual);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest() {
        int userId = (int) (Math.random() * 50000 + 100000000);
        int startAmountOfMoney = 1000;
        Account account = Account.builder()
                                .userId(userId)
                                .amountOfMoney(startAmountOfMoney)
                                .build();
        try {
            AccountDAO.getInstance().add(account, connection);
            account.setAmountOfMoney(500);
            AccountDAO.getInstance().update(account, connection);
            Account actual = AccountDAO.getInstance().getByUserId(userId,connection);
            Assert.assertNotEquals(startAmountOfMoney, actual.getAmountOfMoney());
            AccountDAO.getInstance().delete(userId, connection);
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
