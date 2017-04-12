package by.saidanov.sheduler;

import by.saidanov.auction.entities.Lot;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.LotService;
import by.saidanov.utils.AuctionLogger;
import org.quartz.*;


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
        try {
            AuctionLogger.getInstance().log(getClass(), "LotJoB Works!!!");
            Lot relevantLot = LotService.getInstance().getById(lotId);
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
                LotService.getInstance().update(relevantLot);
            }else {
                currentPrice -= priceCutStep;
            }

            if (currentPrice >= minPrice) {
                relevantLot.setCurrentPrice(currentPrice);
                LotService.getInstance().update(relevantLot);
            } else if (currentPrice < minPrice) {
                LotService.getInstance().delete(lotId);
                scheduler.shutdown();
            }
        } catch (ServiceException | SchedulerException e) {
            AuctionLogger.getInstance().log(
                                    getClass(), "LotJob \"execute()\" method throw exception " + e.getMessage());
        }
    }
}
