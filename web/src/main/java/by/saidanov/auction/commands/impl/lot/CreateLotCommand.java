package by.saidanov.auction.commands.impl.lot;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.constants.Parameters;
import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.entities.User;
import by.saidanov.auction.managers.ConfigurationManager;
import by.saidanov.auction.managers.MessageManager;
import by.saidanov.auction.utils.RequestParamParser;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.LotService;
import by.saidanov.utils.AuctionLogger;
import by.saidanov.utils.HibernateUtil;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

import static by.saidanov.auction.constants.MessageConstants.*;
import static by.saidanov.auction.constants.Parameters.*;
import static by.saidanov.auction.constants.PagePath.*;

/**
 * Description: this command creates new lot
 *
 * @author Artiom Saidanov.
 */
public class CreateLotCommand implements BaseCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String message = null;
        HttpSession session = request.getSession();
        User user = RequestParamParser.getUserFromSession(request);
        Lot lot = RequestParamParser.getLot(request);
        if (lot == null) {
            page = ConfigurationManager.getInstance().getProperty(CLIENT_PAGE_PATH);
            request.setAttribute(LOT_CREATION_FAILED, MessageManager.getInstance().getProperty(EMPTY_FIELDS));
            HibernateUtil.getHibernateUtil().closeSession();
            return page;
        }
        lot.setUser(user);
        try {
            LotService.getInstance().add(lot);
            request.setAttribute(NEW_LOT_CREATED, MessageManager.getInstance().getProperty(LOT_CREATED));
            page = ConfigurationManager.getInstance().getProperty(CLIENT_PAGE_PATH);
            message = "Lot id = " + lot.getId() + " was added successfully.";
            AuctionLogger.getInstance().log(getClass(), message);
        } catch (ServiceException e) {
            page = ConfigurationManager.getInstance().getProperty(ERROR_PAGE_PATH);
            request.setAttribute(LOT_CREATION_FAILED, MessageManager.getInstance().getProperty(CREATION_FAILED));
            message = "Lot creation failed! " + e.getMessage();
            AuctionLogger.getInstance().log(getClass(), message);
        }
        HibernateUtil.getHibernateUtil().closeSession();
        return page;
    }

}
