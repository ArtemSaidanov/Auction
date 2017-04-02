package by.saidanov.auction.commands.impl.lot;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: this command redirects user on create lot page
 *
 * @author Artiom Saidanov.
 */
public class GoToCreateLot implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(PagePath.CREATE_LOT_PAGE_PATH);
        return page;
    }
}
