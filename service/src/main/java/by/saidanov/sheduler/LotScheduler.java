package by.saidanov.sheduler;

import by.saidanov.auction.entities.Lot;
import by.saidanov.utils.HibernateUtil;
import org.hibernate.Session;
import org.quartz.*;
import org.quartz.core.jmx.JobDataMapSupport;
import org.quartz.impl.StdSchedulerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Description: this class provides integration with Quartz Scheduler Framework.
 *
 * @author Artiom Saidanov.
 */
public class LotScheduler {

    public static void startScheduling(Lot lot) throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        Map<String, Object> lotMap = new HashMap<>();
        lotMap.put("lot", lot);
        JobDataMap jobDataMap = JobDataMapSupport.newJobDataMap(lotMap);

        JobDetail lotJob = JobBuilder.newJob(LotJob.class)
                                .setJobData(jobDataMap)
                                .withIdentity("job" + lot.getId())
                                .build();

        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInMinutes(lot.getTimeToNextCut())
                                .repeatForever();

        Trigger lotTrigger = TriggerBuilder.newTrigger()
                                .withIdentity("trigger" + lot.getId())
                                .withSchedule(scheduleBuilder)
                                .startNow()
                                .build();

        scheduler.scheduleJob(lotJob, lotTrigger);
        scheduler.start();
    }
}
