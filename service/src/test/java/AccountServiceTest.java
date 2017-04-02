import by.saidanov.auction.entities.Account;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.AccountService;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Random;

/**
 * Description: this class contains tests for AccountService
 *
 * @author Artiom Saidanov.
 */
public class AccountServiceTest {

    @Test
    public void addTest() {
        int userId = (int) (Math.random() * 50000 + 100000000);
        Account expected = Account.builder()
                                .userId(userId)
                                .amountOfMoney(999999)
                                .build();
        try {
            AccountService.getInstance().add(expected);
            Account actual = AccountService.getInstance().getByUserId(userId);
            Assert.assertEquals(expected.getAmountOfMoney(), actual.getAmountOfMoney());
            Assert.assertEquals(expected.getUserId(), actual.getUserId());
            AccountService.getInstance().delete(userId);
        } catch (SQLException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getByIdTest() {
        Account expectedAcc = Account.builder().id(5).build();
        try {
            Account actualAcc = AccountService.getInstance().getById(expectedAcc.getId());
            Assert.assertEquals(expectedAcc.getId(), actualAcc.getId());
            Assert.assertNotEquals(actualAcc.getUserId(), expectedAcc.getUserId());
            Assert.assertNotEquals(actualAcc.getAmountOfMoney(), expectedAcc.getAmountOfMoney());
        } catch (SQLException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateTest() {
        Account expectedAcc = Account.builder()
                                .id(5)
                                .userId(56)
                                .amountOfMoney(9999999)
                                .build();
        try {
            int realAmountOfMoney = AccountService.getInstance().getById(5).getAmountOfMoney();
            AccountService.getInstance().update(expectedAcc);
            Account actualAcc = AccountService.getInstance().getById(expectedAcc.getId());

            Assert.assertEquals(expectedAcc.getAmountOfMoney(), actualAcc.getAmountOfMoney());

            expectedAcc.setAmountOfMoney(realAmountOfMoney);
            AccountService.getInstance().update(expectedAcc);
        } catch (SQLException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTest() {
        int userId = (int) (Math.random() * 50000 + 100000000);
        Account account = Account.builder()
                                .userId(userId)
                                .amountOfMoney(18999)
                                .build();
        try {
            AccountService.getInstance().add(account);
            AccountService.getInstance().delete(account.getUserId());
            Account actual = AccountService.getInstance().getByUserId(account.getUserId());
            Account expected = Account.builder().build();
            Assert.assertEquals(expected, actual);
        } catch (SQLException | ServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getByUserIdTest(){
        int userId = (int) (Math.random() * 50000 + 100000000);
        Account expected = Account.builder()
                                .userId(userId)
                                .amountOfMoney(18999)
                                .build();
        try {
            AccountService.getInstance().add(expected);
            Account actual = AccountService.getInstance().getByUserId(expected.getUserId());
            Assert.assertEquals(expected.getAmountOfMoney(), actual.getAmountOfMoney());
            Assert.assertEquals(expected.getUserId(), actual.getUserId());
            AccountService.getInstance().delete(expected.getUserId());
        } catch (SQLException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void putMoneyTest(){
        int userId = (int) (Math.random() * 50000 + 100000000);
        Account expectedAccount = Account.builder()
                                .userId(userId)
                                .amountOfMoney(10)
                                .build();
        int moneyToPut = 2;

        try {
            AccountService.getInstance().add(expectedAccount);
            AccountService.getInstance().putMoney(expectedAccount, moneyToPut);

            int expectedMoney = expectedAccount.getAmountOfMoney();

            int actualMoney = AccountService.getInstance().getByUserId(expectedAccount.getUserId()).getAmountOfMoney();
            Assert.assertEquals(expectedMoney, actualMoney);
            AccountService.getInstance().delete(expectedAccount.getUserId());
        } catch (SQLException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void takeMoneyTest(){
        int userId = (int) (Math.random() * 50000 + 100000000);
        Account expectedAccount = Account.builder()
                                .userId(userId)
                                .amountOfMoney(10)
                                .build();
        int moneyToTake = 2;

        try {
            AccountService.getInstance().add(expectedAccount);
            AccountService.getInstance().takeMoney(expectedAccount, moneyToTake);

            int expectedMoney = expectedAccount.getAmountOfMoney();

            int actualMoney = AccountService.getInstance().getByUserId(expectedAccount.getUserId()).getAmountOfMoney();
            Assert.assertEquals(expectedMoney, actualMoney);
            AccountService.getInstance().delete(expectedAccount.getUserId());
        } catch (SQLException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getInstanceTest(){
        AccountService accountService = AccountService.getInstance();
        Assert.assertNotNull(accountService);
    }
}
