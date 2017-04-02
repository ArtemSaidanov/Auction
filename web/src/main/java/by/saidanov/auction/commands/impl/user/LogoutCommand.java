package by.saidanov.auction.commands.impl.user;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: this command redirect user to index.page
 *
 * @author Artiom Saidanov.
 */
public class LogoutCommand implements BaseCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
        request.getSession().invalidate();
        return page;
    }
}
