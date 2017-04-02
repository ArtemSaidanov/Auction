package by.saidanov.auction.utils;

import by.saidanov.auction.commands.BaseCommand;
import by.saidanov.auction.commands.factory.CommandFactory;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.managers.ConfigurationManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * Description: this class takes request and defines command that will be executed
 *
 * @author Artiom Saidanov.
 */
public class RequestHandler {

    public RequestHandler() {
    }

    /** Method defines the page that will be shown to the user */
    public static void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandFactory commandFactory = CommandFactory.getInstance();
        BaseCommand command = commandFactory.defineCommand(request);
        String page = command.execute(request);
        if (page != null) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
