package by.saidanov.auction.commands.impl;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.managers.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
public class DefaultCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ConfigurationManager.getInstance().getProperty(PagePath.LOGIN_PAGE_PATH);
        return page;
    }
}
