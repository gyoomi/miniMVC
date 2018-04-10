/**
 * Copyright © 2018, TaoDing
 * <p>
 * All Rights Reserved.
 */

package cn.minimvc.core.test;

import cn.minimvc.core.annotation.ControllerKey;
import cn.minimvc.core.annotation.RequestMappingKey;
import cn.minimvc.core.bean.View;
import cn.minimvc.core.bean.WebContext;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录   Controller
 *
 * @author Leon
 * @version 2018/4/10 16:16
 */
@ControllerKey
public class LoginController {

    @RequestMappingKey(value = "/index")
    public View index(){
        return new View("/index.jsp");
    }

    @RequestMappingKey(value = "/login/validate")
    public View validate(){
        HttpServletRequest req = WebContext.requestHolder.get();
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        if ("tom".equals(name) && "123".equals(password)) {
            req.getSession().setAttribute("user", name);
            return new View("/success.jsp");
        } else{
            return new View("/index.jsp");
        }
    }

}
