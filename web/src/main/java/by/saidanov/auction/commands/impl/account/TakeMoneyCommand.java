package by.saidanov.auction.commands.impl.account;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.MessageConstants;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.constants.Parameters;
import by.saidanov.auction.entities.Account;
import by.saidanov.auction.managers.ConfigurationManager;
import by.saidanov.auction.managers.MessageManager;
import by.saidanov.auction.utils.RequestParamParser;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.AccountService;
import by.saidanov.utils.AuctionLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.SQLException;

import static by.saidanov.auction.constants.MessageConstants.*;
import static by.saidanov.auction.constants.Parameters.*;

/**
 * Description: this command withdraw money from client's account
 *
 * @author Artiom Saidanov.
 */
public class TakeMoneyCommand implements BaseCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String message;
        int moneyToTake = RequestParamParser.getMoney(request);
        if (moneyToTake == -1) {
            request.setAttribute(BLANK_MONEY_FIELD, MessageManager.getInstance().getProperty(YOU_ENTERED_BLACK_FIELD));
            page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAGE_PATH);
            return page;
        }
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute(Parameters.ACCOUNT);
        try {
            AccountService.getInstance().takeMoney(account, moneyToTake);
            request.setAttribute(PUT_TAKE_SUCCESS, MessageManager.getInstance().getProperty(MONEY_WITHDRAWN_SUCCESSFULLY));
            session.setAttribute(ACCOUNT, AccountService.getInstance().getById(account.getId()));
            page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAGE_PATH);
            return page;
        } catch (SQLException | ServiceException e) {
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.DATABASE_ERROR));
            page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
            message = "TakeMoneyCommand failed " + e.getMessage();
            AuctionLogger.getInstance().log(getClass(), message);
        }
        return page;
    }
}
