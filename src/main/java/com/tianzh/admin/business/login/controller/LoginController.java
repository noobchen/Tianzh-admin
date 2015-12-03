package com.tianzh.admin.business.login.controller;

import com.tianzh.admin.common.permission.model.Operator;
import com.tianzh.admin.common.permission.service.OperatorService;
import com.tianzh.admin.common.permission.util.UrlEncryption;
import com.tianzh.admin.common.spring.mvc.session.SessionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cyc
 * Date: 12-9-7
 * Time: 下午7:36
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);
    public static final String USER = "user";
    public static final String MODULE = "module";
    public static final String MODULE_NAME = "moduleName";
    public static final String MENUS = "menus";
    public static final String MENU = "menu";
    public static final String MENU_NAME = "menuName";
    public static final String OPERATES = "operates";
    public static final String OPERATE = "operate";

    @Autowired
    private OperatorService operatorService;

    @RequestMapping(value = "index.do")
    public ModelAndView index() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "login.do")
    public ModelAndView login(@RequestParam(value = "account", required = false) String account,
                              @RequestParam(value = "password", required = false) String password) {
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("result", false);
            model.put("errorMsg", "请输入账号和密码.");
            return new ModelAndView("login", model);
        }

        String result = operatorService.login(account, password);

        if (null == result) {
            return new ModelAndView(new RedirectView("main.do"));
        } else {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("result", false);
            model.put("errorMsg", result);
            return new ModelAndView("login", model);
        }
    }

    @RequestMapping(value = "logout.do")
    public ModelAndView logout() {
        Operator operator = (Operator) SessionUtils.getSessionAttribute(LoginController.USER);
        if (null != operator) {
            SessionUtils.removeLoginUser(operator.getAccount());
        }
        SessionUtils.removeSessionAllAttribute();

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("result", true);
        model.put("errorMsg", "您已成功登出。");
        return new ModelAndView("login", model);
    }


    @RequestMapping(value = "checkUser.do")
    @ResponseBody
    public Object checkUser(@RequestParam("infoParam") String infoParam) {
        String account = UrlEncryption.getAccount(infoParam);
        String oldR = SessionUtils.getLoginUser(account);

        String r = UrlEncryption.getR(infoParam);

        if (StringUtils.equals(oldR, r)) {
            //已经登陆
            return UrlEncryption.encrypt("true");
        } else {
            //没有登陆
            return UrlEncryption.encrypt("false");
        }
    }

    @RequestMapping(value = "main.do")
    public RedirectView main() {
        Operator operator = (Operator) SessionUtils.getSessionAttribute("user");

        if (operator.getAccountType().equals("0")) {
            return new RedirectView("queryBasicProductAnalysis.do");
        } else {
            return new RedirectView("productAnalysis.do");
        }
    }

    @RequestMapping(value = "*")
    public ModelAndView all() {
        return new ModelAndView("main");
    }
}
