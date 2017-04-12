package by.saidanov.auction.commands.impl.lot;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.MessageConstants;
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
import java.sql.SQLException;
import java.util.List;

/**
 * Description: shows all client's lots
 *
 * @author Artiom Saidanov.
 */
public class GoToClientLots implements BaseCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String message = null;
        User user = RequestParamParser.getUserFromSession(request);
        try {
            List<Lot> lotList = LotService.getInstance().getUserLots(user);
            request.setAttribute(Parameters.LOT_LIST, lotList);
            page = ConfigurationManager.getInstance().getProperty(PagePath.SHOW_ALL_CLIENT_LOTS);
        } catch (ServiceException e) {
            page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.DATABASE_ERROR));
            message = "GoToClientLots Error" + e.getMessage();
            AuctionLogger.getInstance().log(getClass(), message);
        }
        HibernateUtil.getHibernateUtil().closeSession();
        return page;
    }
}
