package com.tianzh.admin.business.analysis.controller.servlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Created by pig on 2015-06-07.
 */
public class ProxyServlet extends HttpServlet {
    String proxyServletName;
    InitServlet proxy;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());

        proxyServletName = getServletName();

        proxy = (InitServlet) webApplicationContext.getBean(proxyServletName);

        if (proxy != null) {
            proxy.init(config);
        }
    }
}
