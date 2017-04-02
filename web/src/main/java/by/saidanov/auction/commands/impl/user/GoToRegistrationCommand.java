package by.saidanov.auction.commands.impl.user;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: Redirects to the registration page
 *
 * @author Artiom Saidanov.
 */
public class GoToRegistrationCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(PagePath.REGISTRATION_PAGE_PATH);
        return page;
    }
}
