package by.saidanov.sheduler;

import by.saidanov.auction.entities.Lot;
import by.saidanov.dao.impl.LotDAO;
import by.saidanov.exceptions.DaoException;
import by.saidanov.managers.PoolManager;
import by.saidanov.utils.AuctionLogger;
import org.quartz.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Description: this class is responsible for lot price reduction. One object of this class creates for every lot.
 *
 * @author Artiom Saidanov.
 */
public class LotJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap lotMap = context.getJobDetail().getJobDataMap();
        Lot irrelevantLot = (Lot) lotMap.get("lot");
        int lotId = irrelevantLot.getId();
        Connection connection;
        try {
            AuctionLogger.getInstance().log(getClass(), "LotJoB Works!!!");
            connection = PoolManager.getDataSource().getConnection();
            Lot relevantLot = LotDAO.getInstance().getById(lotId, connection);
            Scheduler scheduler = context.getScheduler();

            int priceCutStep = relevantLot.getPriceCutStep();
            int currentPrice = relevantLot.getCurrentPrice();
            int minPrice = relevantLot.getMinPrice();
            boolean isOpen = relevantLot.isOpen();
            boolean isNew = relevantLot.isNew();

            if (!isOpen) {
                scheduler.shutdown();
            }

            if (isNew){
                relevantLot.setNew(false);
                LotDAO.getInstance().update(relevantLot, connection);
            }else {
                currentPrice -= priceCutStep;
            }

            if (currentPrice >= minPrice) {
                relevantLot.setCurrentPrice(currentPrice);
                LotDAO.getInstance().update(relevantLot, connection);
            } else if (currentPrice < minPrice) {
                LotDAO.getInstance().delete(lotId, connection);
                scheduler.shutdown();
            }
        } catch (SQLException | DaoException | SchedulerException e) {
            AuctionLogger.getInstance().log(
                                    getClass(), "LotJob \"execute()\" method throw exception " + e.getMessage());
        }
    }
}
