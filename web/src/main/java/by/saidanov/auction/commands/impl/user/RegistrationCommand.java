package by.saidanov.auction.commands.impl.user;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.constants.Parameters;
import by.saidanov.auction.entities.Account;
import by.saidanov.auction.entities.User;
import by.saidanov.auction.managers.ConfigurationManager;
import by.saidanov.auction.managers.MessageManager;
import by.saidanov.auction.utils.RequestParamParser;
import by.saidanov.exceptions.ServiceException;
import by.saidanov.services.impl.UserService;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import static by.saidanov.auction.constants.MessageConstants.*;
import static by.saidanov.auction.constants.PagePath.*;
import static by.saidanov.auction.constants.Parameters.*;

/**
 * Description: when a user tries to register this command works.
 *
 * @author Artiom Saidanov.
 */
public class RegistrationCommand implements BaseCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        try {
            User user = RequestParamParser.getUser(request);
            Account account = RequestParamParser.getAccount(request);
            if (allFieldAreFilled(user) && account != null) {
                if (UserService.getInstance().checkIsUserNew(user)) {
                    UserService.getInstance().add(user, account);
                    request.setAttribute(OPERATION_MESSAGE, MessageManager.getInstance().getProperty(SUCCESS_REGISTRATION));
                    page = ConfigurationManager.getInstance().getProperty(LOGIN_PAGE_PATH);
                } else {
                    request.setAttribute(ERROR_USER_EXISTS, MessageManager.getInstance().getProperty(USER_EXISTS));
                    page = ConfigurationManager.getInstance().getProperty(REGISTRATION_PAGE_PATH);
                }
            } else {
                request.setAttribute(OPERATION_MESSAGE, MessageManager.getInstance().getProperty(EMPTY_FIELDS));
                page = ConfigurationManager.getInstance().getProperty(REGISTRATION_PAGE_PATH);
            }
        } catch (ServiceException | SQLException e) {
            request.setAttribute(ERROR_DATABASE, MessageManager.getInstance().getProperty(DATABASE_ERROR));
            page = ConfigurationManager.getInstance().getProperty(ERROR_PAGE_PATH);
        }
        return page;
    }

    /** This method checks if user left fields blank */
    private boolean allFieldAreFilled(User user) {
        boolean isFilled = false;
        if (!user.getLogin().isEmpty()
                                && !user.getPassword().isEmpty()
                                && !user.getFirstName().isEmpty()
                                && !user.getLastName().isEmpty()) {
            isFilled = true;
        }
        return isFilled;
    }
}
