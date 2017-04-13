package by.saidanov.auction.commands.impl.lot;

import by.saidanov.auction.commands.BaseCommand;
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
import java.util.List;

import static by.saidanov.auction.constants.Parameters.*;
import static by.saidanov.auction.constants.PagePath.*;
import static by.saidanov.auction.constants.MessageConstants.*;

/**
 * Description: command returns list of all lots to user.
 *
 * @author Artiom Saidanov.
 */
public class LotListCommand implements BaseCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String message = null;
        try {
            User user = RequestParamParser.getUserFromSession(request);
            List<Integer> pageList = LotService.getInstance().getPageList(user);
            Integer lotPage = RequestParamParser.getLotPage(request);



            List<Lot> lotList = LotService.getInstance().getAll(user, lotPage);



            request.setAttribute(LOT_LIST, lotList);
            request.setAttribute("pageList", pageList);
            page = ConfigurationManager.getInstance().getProperty(LOT_LIST_PAGE_PATH);






        } catch (ServiceException e) {
            request.setAttribute(LOT_LIST_ERROR, MessageManager.getInstance().getProperty(LOTS_NOT_AVAILABLE));
            page = ConfigurationManager.getInstance().getProperty(ERROR_PAGE_PATH);
            message = "LotList error." + e.getMessage();
            AuctionLogger.getInstance().log(getClass(), message);
        }
        HibernateUtil.getHibernateUtil().closeSession();
        return page;
    }
}
