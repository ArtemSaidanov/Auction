package by.saidanov.auction.commands.impl.lot;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.managers.ConfigurationManager;
import by.saidanov.auction.managers.MessageManager;
import by.saidanov.auction.utils.RequestParamParser;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.LotService;
import by.saidanov.utils.AuctionLogger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import static by.saidanov.auction.constants.MessageConstants.*;
import static by.saidanov.auction.constants.PagePath.*;
import static by.saidanov.auction.constants.Parameters.*;

/**
 * Description: this command delete lot from application
 *
 * @author Artiom Saidanov.
 */
public class DeleteLotCommand implements BaseCommand{

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String message = null;
        try {
            LotService.getInstance().delete(RequestParamParser.getLotId(request));
            request.setAttribute(LOT_DELETED, MessageManager.getInstance().getProperty(LOT_WAS_DELETED));
            page = ConfigurationManager.getInstance().getProperty(CLIENT_PAGE_PATH);
        } catch (SQLException | ServiceException e) {
            request.setAttribute(LOT_DELETE_FAILED, MessageManager.getInstance().getProperty(LOT_DELETE_WAS_FAILED));
            page = ConfigurationManager.getInstance().getProperty(ERROR_PAGE_PATH);
            message = "Lot deleting failed " + e.getMessage();
            AuctionLogger.getInstance().log(getClass(), message);
        }
        return page;
    }
}
