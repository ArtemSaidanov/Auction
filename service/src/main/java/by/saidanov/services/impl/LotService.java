package by.saidanov.services.impl;

import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.EntityMarker;
import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.entities.User;
import by.saidanov.dao.BaseDao;
import by.saidanov.dao.ILotDAO;
import by.saidanov.dao.impl.BaseDaoImpl;
import by.saidanov.dao.impl.LotDAOImpl;
import by.saidanov.exceptions.DaoException;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.Service;
import by.saidanov.sheduler.LotScheduler;
import by.saidanov.utils.AuctionLogger;
import org.quartz.SchedulerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: contains business-logic and provides transactional work for lot entity
 *
 * @author Artiom Saidanov.
 */
public class LotService implements Service {

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
     * Calls Dao save() method
     *
     * @param lot to add to the database
     * @throws ServiceException
     */
    public void add(Lot lot) throws ServiceException {
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        try {
            getSession().beginTransaction();
            baseDao.save(lot);
            LotScheduler.startScheduling(lot);
            getSession().getTransaction().commit();
        } catch (SchedulerException | DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
    }

    /**
     * This method get all lots
     */
    public List<Lot> getAll() throws ServiceException {
        ILotDAO lotDao = LotDAOImpl.getInstance();
        List<Lot> lotList;
        try {
            getSession().beginTransaction();
            lotList = lotDao.getAll();
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
        return lotList;
    }

    /**
     * This method get all lots except lot that belong to user called this method
     */
    public List<Lot> getAll(User user, Integer lotPage) throws ServiceException {
        LotDAOImpl lotDao = LotDAOImpl.getInstance();
        List<Lot> lotList = null;

        try {
            getSession().beginTransaction();
            int amountOfRowsOnPage = 3;
            /*TODO назвать правильно. Это число выведено мной в ходе поиска алгоритма*/
            int i = amountOfRowsOnPage - 1;
            Integer firstResult = (lotPage * amountOfRowsOnPage) - i;
            if (firstResult < 0){
                firstResult = 0;
            } else firstResult -= 1;
            lotList = lotDao.getAll(user, firstResult);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
        return lotList;
    }

    /**
     * This method get lot by lotId
     */
    public Lot getById(Integer id) throws ServiceException {
        ILotDAO lotDao = LotDAOImpl.getInstance();
        Lot lot;
        try {
            getSession().beginTransaction();
            lot = lotDao.getById(id);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
        return lot;
    }

    /**
     * This method updates lot. It changes quantity when someone bought lot.
     * Also this method is used by Quartz Scheduler to change current lot price per time.
     */
    public void update(Lot lot) throws ServiceException {
        BaseDao<EntityMarker> baseDao = BaseDaoImpl.getInstance();
        try {
            getSession().beginTransaction();
            baseDao.update(lot);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
    }

    /**
     * This method changes lot's boolean isOpen to false
     */
    public void delete(int id) throws ServiceException {
        ILotDAO lotDao = LotDAOImpl.getInstance();
        try {
            getSession().beginTransaction();
            lotDao.delete(id);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
    }

    /**
     * This method is responsible for buying lots.
     * Method check user's account, and if user have enough money to buy lot
     * money is debited from user's account and is credited to the lot owner's account
     */
    public boolean buyLot(Lot lot, User buyer, Integer quantity) throws ServiceException {
        boolean isBought = false;
        try {
            //Lot lot = LotDAOImpl.getInstance().getById(lotInfo.getId());
            Account buyersAccount = AccountService.getInstance().getByUserId(buyer.getId());
            int lotPrice = lot.getCurrentPrice() * quantity;
            int lotQuantity = lot.getQuantity();
            int buyersMoney = buyersAccount.getAmountOfMoney();
            if (buyersMoney > lotPrice) {
                buyersAccount.setAmountOfMoney(buyersMoney - lotPrice);
                AccountService.getInstance().update(buyersAccount);

                int lotOwnerId = lot.getUser().getId();
                Account lotOwnerAccount = AccountService.getInstance().getByUserId(lotOwnerId);
                int lotOwnerMoney = lotOwnerAccount.getAmountOfMoney();
                lotOwnerAccount.setAmountOfMoney(lotOwnerMoney + lotPrice);
                AccountService.getInstance().update(lotOwnerAccount);
                int actualQuantity = lotQuantity - quantity;
                lot.setQuantity(actualQuantity);
                if (actualQuantity == 0) {
                    LotService.getInstance().delete(lot.getId());
                } else {
                    LotService.getInstance().update(lot);
                }
                isBought = true;
            }
        } catch (Exception e) {
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
        return isBought;
    }

    /**
     * This method gets all user's lots
     */
    public List<Lot> getUserLots(User user) throws ServiceException {
        ILotDAO lotDao = LotDAOImpl.getInstance();
        List<Lot> lotList;
        try {
            getSession().beginTransaction();
            lotList = lotDao.getUserLots(user);
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
        return lotList;
    }


    public List<Integer> getPageList(User user) throws ServiceException {
        ILotDAO lotDao = LotDAOImpl.getInstance();
        List<Integer> pageList = new ArrayList<>();
        try {
            getSession().beginTransaction();
            Integer rowCount = lotDao.getRowCount(user);
            Integer pageNumber;
            if (rowCount % 3 == 0) {
                pageNumber = rowCount / 3;
            } else pageNumber = (rowCount / 3) + 1;
            for (int i = 0; i < pageNumber; i++) {
                pageList.add(i+1);
            }
            getSession().getTransaction().commit();
        } catch (DaoException e) {
            getSession().getTransaction().rollback();
            String message = e.toString() + " " + getClass();
            AuctionLogger.getInstance().log(getClass(), message);
            throw new ServiceException(message);
        }
        return pageList;
    }
}