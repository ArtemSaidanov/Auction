package by.saidanov.auction.commands.impl.lot;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.Parameters;
import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.managers.ConfigurationManager;
import by.saidanov.auction.managers.MessageManager;
import by.saidanov.auction.utils.RequestParamParser;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.LotService;
import by.saidanov.utils.AuctionLogger;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import static by.saidanov.auction.constants.MessageConstants.*;
import static by.saidanov.auction.constants.Parameters.*;
import static by.saidanov.auction.constants.PagePath.*;

/**
 * Description: this command show one lot
 *
 * @author Artiom Saidanov.
 */
public class ShowLotCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String message = null;
        int lotId = RequestParamParser.getLotId(request);
        try {
            Lot lot = LotService.getInstance().getById(lotId);
            request.setAttribute(LOT, lot);
            page = ConfigurationManager.getInstance().getProperty(SHOW_LOT_PAGE_PATH);
        } catch (SQLException | ServiceException e) {
            page = ConfigurationManager.getInstance().getProperty(ERROR_PAGE_PATH);
            request.setAttribute(LOT_NOT_FOUND, MessageManager.getInstance().getProperty(LOTS_NOT_AVAILABLE));
            message = "ShowLotCommand failed! " + e.getMessage();
            AuctionLogger.getInstance().log(getClass(), message);
        }
        return page;
    }
}
