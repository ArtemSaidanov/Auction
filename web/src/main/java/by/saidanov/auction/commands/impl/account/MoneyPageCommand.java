package by.saidanov.auction.commands.impl.account;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.constants.Parameters;
import by.saidanov.auction.managers.ConfigurationManager;
import by.saidanov.auction.utils.RequestParamParser;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: redirects on page where client can take or put money on his account
 *
 * @author Artiom Saidanov.
 */
public class MoneyPageCommand implements BaseCommand{

    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String action = RequestParamParser.getAction(request);
        request.setAttribute(Parameters.ACTIONTYPE, action);
        request.setAttribute(Parameters.BUTTON_NAME, action + " money");
        page = ConfigurationManager.getInstance().getProperty(PagePath.MONEY_PAGE_PATH);
        return page;
    }
}
