package by.saidanov.auction.commands.impl.lot;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.MessageConstants;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.constants.Parameters;
import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.Lot;
import by.saidanov.auction.entities.User;
import by.saidanov.auction.managers.ConfigurationManager;
import by.saidanov.auction.managers.MessageManager;
import by.saidanov.auction.utils.RequestParamParser;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.AccountService;
import by.saidanov.services.impl.LotService;
import by.saidanov.utils.AuctionLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import static by.saidanov.auction.constants.Parameters.LOT;


/**
 * Description: this command provides lot purchase
 *
 * @author Artiom Saidanov.
 */
public class BuyLotCommand implements BaseCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String message;
        Integer id = RequestParamParser.getLotId(request);
        Integer quantity = RequestParamParser.getLotQuantity(request);
        try {
            Lot lot = LotService.getInstance().getById(id);
            if (quantity == -1){
                request.setAttribute(LOT, lot);
                request.setAttribute(Parameters.LOT_QUANTITY_IS_BLANK, MessageManager.getInstance().getProperty(MessageConstants.BLANK_QUANTITY));
                page = ConfigurationManager.getInstance().getProperty(PagePath.SHOW_LOT_PAGE_PATH);
                return page;
            }
            User user = RequestParamParser.getUserFromSession(request);
            boolean isBought = LotService.getInstance().buyLot(lot, user);
            if (isBought) {
                //TODO добавить обновление кошелька
                Account account = AccountService.getInstance().getByUserId(user.getId());
                request.getSession().setAttribute(Parameters.ACCOUNT, account);
                request.setAttribute(Parameters.LOT_BOUGHT, MessageManager.getInstance().getProperty(MessageConstants.LOT_BOUGHT));
                page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAGE_PATH);
            }else {
                request.setAttribute(Parameters.NOT_ENOUGH_MONEY, MessageManager.getInstance().getProperty(MessageConstants.NOT_ENOUGH_MONEY));
                page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAGE_PATH);
            }

        } catch (SQLException | ServiceException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.DATABASE_ERROR));
            page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
            message = "BuyLotCommand failed " + e.getMessage();
            AuctionLogger.getInstance().log(getClass(), message);
        }
        return page;
    }
}
