import by.saidanov.auction.entities.User;
import by.saidanov.dao.impl.UserDAO;
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
public class UserDAOTest {

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
        User expected = User.builder()
                                .login("TEST")
                                .password("TEST")
                                .firstName("TEST NAME")
                                .lastName("TEST LASTNAME")
                                .accessLevel(0)
                                .build();
        try {
            UserDAO.getInstance().add(expected, connection);
            User actual = UserDAO.getInstance().getByLogin(expected.getLogin(), connection);
            int userId = UserDAO.getInstance().getMaxId(connection);
            expected.setId(userId);
            Assert.assertEquals(expected, actual);
            UserDAO.getInstance().delete(userId, connection);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMaxIdPlusOneTest() {
        try {
            int maxId = UserDAO.getInstance().getMaxId(connection);
            int maxIdPlusOne = UserDAO.getInstance().getMaxIdPlusOne(connection);
            int expectedId = maxId + 1;
            Assert.assertEquals(expectedId, maxIdPlusOne);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isAuthorizedTest() {
        String badLogin = "";
        String badPassword = "";
        String goodLogin = "Art";
        String goodPassword = "1234";
        try {
            boolean badResult = UserDAO.getInstance().isAuthorized(badLogin, badPassword, connection);
            boolean goodResult = UserDAO.getInstance().isAuthorized(goodLogin, goodPassword, connection);
            Assert.assertTrue(goodResult);
            Assert.assertFalse(badResult);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isNewUserTest(){
        String newLogin = "qwertyuiopljgsabc";
        String notNewLogin = "Art";
        try {
            boolean newUser = UserDAO.getInstance().isNewUser(newLogin, connection);
            boolean oldUser = UserDAO.getInstance().isNewUser(notNewLogin, connection);
            Assert.assertTrue(newUser);
            Assert.assertFalse(oldUser);
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
