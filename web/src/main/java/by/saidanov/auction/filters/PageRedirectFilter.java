package by.saidanov.auction.filters;

import by.saidanov.auction.constants.PagePath;
import by.saidanov.auction.managers.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author Artiom Saidanov.
 */
@WebFilter(filterName = "PageRedirectFilter")
public class PageRedirectFilter implements Filter {

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        httpResponse.sendRedirect(httpRequest.getContextPath() + ConfigurationManager.getInstance().getProperty(PagePath.INDEX_PAGE_PATH));
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
    }
}
