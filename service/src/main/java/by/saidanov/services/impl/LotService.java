package by.saidanov.services.impl;

import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.entities.User;
import by.saidanov.dao.impl.LotDAO;
import by.saidanov.exceptions.DaoException;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.managers.PoolManager;
import by.saidanov.services.Service;
import by.saidanov.sheduler.LotScheduler;
import by.saidanov.utils.AuctionLogger;
import org.quartz.SchedulerException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Description: contains business-logic and provides transactional work for lot entity
 *
 * @author Artiom Saidanov.
 */
public class LotService {

    private volatile static LotService instance;

    private LotService() {
    }

    public static LotService getInstance() {
        if (instance == null) {
            synchronized (LotService.class) {
                if (instance == null) {
                    instance = new LotService();
                }
            }
        }
        return instance;
    }

    /**
     * Calls Dao add() method
     *
     * @param lot to add to the database
     * @throws SQLException
     */
    public void add(Lot lot) throws SQLException, ServiceException {
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            LotDAO.getInstance().add(lot, connection);
            int maxId = LotDAO.getInstance().getMaxId(connection);
            Lot lotForScheduler = LotDAO.getInstance().getById(maxId, connection);
            connection.commit();
            LotScheduler.startScheduling(lotForScheduler);
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException | SchedulerException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * This method get all lots
     */
    public List<Lot> getAll() throws SQLException, ServiceException {
        Connection connection = null;
        List<Lot> lotList;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            lotList = LotDAO.getInstance().getAll(connection);
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return lotList;
    }

    /**
     * This method get all lots except lot that belong to user called this method
     */
    public List<Lot> getAll(int userId) throws SQLException, ServiceException {
        Connection connection = null;
        List<Lot> lotList;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            lotList = LotDAO.getInstance().getAll(userId, connection);
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return lotList;
    }

    /**
     * This method get lot by lotId
     */
    public Lot getById(int id) throws SQLException, ServiceException {
        Connection connection = null;
        Lot lot;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            lot = LotDAO.getInstance().getById(id, connection);
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return lot;
    }

    /**
     * This method updates lot. It changes quantity when someone bought lot.
     * Also this method is used by Quartz Scheduler to change current lot price per time.
     */
    public void update(Lot lot) throws SQLException, ServiceException {
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            LotDAO.getInstance().update(lot, connection);
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * This method changes lot's boolean isOpen to false
     */
    public void delete(int id) throws SQLException, ServiceException {
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            LotDAO.getInstance().delete(id, connection);
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * This method deletes all user's lots from database
     **/
    public void delete(User user) throws SQLException, ServiceException {
        Connection connection = null;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            LotDAO.getInstance().delete(user, connection);
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * This method is responsible for buying lots.
     * Method check user's account, and if user have enough money to buy lot
     * money is debited from user's account and is credited to the lot owner's account
     */
    public boolean buyLot(Lot lotInfo, User user) throws SQLException, ServiceException {
        Connection connection = null;
        boolean isBought = false;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            Lot lot = LotDAO.getInstance().getById(lotInfo.getId(), connection);
            Account account = AccountService.getInstance().getByUserId(user.getId());
            int lotPrice = lot.getCurrentPrice();
            int lotQuantity = lot.getQuantity();
            int userMoney = account.getAmountOfMoney();
            if (userMoney > lotPrice) {
                account.setAmountOfMoney(userMoney - lotPrice);
                AccountService.getInstance().update(account);

                int lotOwnerId = lot.getUserId();
                Account lotOwnerAccount = AccountService.getInstance().getByUserId(lotOwnerId);
                int lotOwnerMoney = lotOwnerAccount.getAmountOfMoney();
                lotOwnerAccount.setAmountOfMoney(lotOwnerMoney + lotPrice);
                AccountService.getInstance().update(lotOwnerAccount);
                int actualQuantity = lotQuantity - lotInfo.getQuantity();
                lot.setQuantity(actualQuantity);
                if (actualQuantity == 0) {
                    LotService.getInstance().delete(lot.getId());
                } else {
                    LotService.getInstance().update(lot);
                }
                isBought = true;
            }
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return isBought;
    }

    /**
     * This method gets all user's lots
     */
    public List<Lot> getUserLots(int userId) throws SQLException, ServiceException {
        Connection connection = null;
        List<Lot> lotList;
        try {
            connection = PoolManager.getDataSource().getConnection();
            connection.setAutoCommit(false);
            lotList = LotDAO.getInstance().getUserLots(userId, connection);
            connection.commit();
            AuctionLogger.getInstance().log(getClass(), "Transaction succeeded");
        } catch (SQLException | DaoException e) {
            if (connection != null) {
                connection.rollback();
            }
            AuctionLogger.getInstance().log(getClass(), "Transaction failed");
            throw new ServiceException(e.getMessage());
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return lotList;
    }
}
