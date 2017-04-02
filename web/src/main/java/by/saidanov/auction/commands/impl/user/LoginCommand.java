package by.saidanov.auction.commands.impl.user;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.MessageConstants;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.constants.Parameters;
import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.User;
import by.saidanov.auction.managers.ConfigurationManager;
import by.saidanov.auction.managers.MessageManager;
import by.saidanov.auction.util.UserType;
import by.saidanov.auction.utils.RequestParamParser;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.AccountService;
import by.saidanov.services.impl.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Description: When a user tries to sign in this command works.
 *
 * @author Artiom Saidanov.
 */
public class LoginCommand implements BaseCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        User user = RequestParamParser.getUser(request);
        try {
            if (UserService.getInstance().checkUserAuthorization(user.getLogin(), user.getPassword())) {
                user = UserService.getInstance().getUserByLogin(user.getLogin());
                UserType userType = UserService.getInstance().checkAccessLevel(user);
                Account account = AccountService.getInstance().getByUserId(user.getId());
                session.setAttribute(Parameters.ACCOUNT, account);
                session.setAttribute(Parameters.USER, user);
                session.setAttribute(Parameters.USER_TYPE, userType);
                if (UserType.CLIENT.equals(userType)) {
                    page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAGE_PATH);
                } else {
                    page = ConfigurationManager.getInstance().getProperty(PagePath.ADMIN_PAGE_PATH);
                }
            } else {
                page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
                request.setAttribute(Parameters.ERROR_LOGIN_OR_PASSWORD, MessageManager.getInstance().getProperty(MessageConstants.WRONG_LOGIN_OR_PASSWORD));
            }
        } catch (SQLException | ServiceException e) {
            page = ConfigurationManager.getInstance().getProperty(PagePath.ERROR_PAGE_PATH);
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.DATABASE_ERROR));
        }
        return page;
    }
}
