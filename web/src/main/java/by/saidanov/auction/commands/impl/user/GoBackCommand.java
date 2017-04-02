package by.saidanov.auction.commands.impl.user;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.managers.ConfigurationManager;
import by.saidanov.auction.utils.RequestParamParser;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
public class GoBackCommand implements BaseCommand {

    @Override
    public String execute(HttpServletRequest request) {
        String goToPage = RequestParamParser.getPage(request);
        String page;
        if (goToPage != null && goToPage.equals("login")) {
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            return page;
        }
        page = ConfigurationManager.getInstance().getProperty(PagePath.CLIENT_PAGE_PATH);
        return page;
    }
}
