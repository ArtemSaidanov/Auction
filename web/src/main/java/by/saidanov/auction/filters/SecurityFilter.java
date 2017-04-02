package by.saidanov.auction.filters;

import by.saidanov.auction.commands.factory.CommandType;
import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.util.UserType;
import by.saidanov.auction.managers.ConfigurationManager;
import by.saidanov.auction.utils.RequestParamParser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Description: this filter provide security of illegal access to pages
 *
 * @author Artiom Saidanov.
 */
@WebFilter(filterName = "SecurityFilter")
public class SecurityFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        UserType userType = RequestParamParser.getUserType(httpRequest);
        try {
            CommandType commandType = RequestParamParser.getCommandType(httpRequest);
            if (userType == null) {
                if (commandType == CommandType.LOGIN) {
                    chain.doFilter(request, response);
                } else if (commandType == CommandType.GOTOREGISTRATION) {
                    chain.doFilter(request, response);
                } else if (commandType == CommandType.REGISTRATION) {
                    chain.doFilter(request, response);
                } else {
                    String page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
                    RequestDispatcher dispatcher = request.getRequestDispatcher(page);
                    dispatcher.forward(httpRequest, httpResponse);
                    session.invalidate();
                }
            } else {
                chain.doFilter(request, response);
            }
        }
        catch(IllegalArgumentException e) {
            String page = ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH);
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(httpRequest, httpResponse);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
