/**
 * Copyright © 2018, TaoDing
 * <p>
 * All Rights Reserved.
 */

package cn.minimvc.core.bean;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用来存储当前线程中req和resp
 *
 * @author Leon
 * @version 2018/4/2 14:46
 */
public class WebContext {

    public static ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();
    public static ThreadLocal<HttpServletResponse> responseHolder = new ThreadLocal<>();

    public HttpServletRequest getRequest(){
        return requestHolder.get();
    }

    public HttpSession getSession(){
        return requestHolder.get().getSession();
    }

    public ServletContext getServletContext(){
        return requestHolder.get().getSession().getServletContext();
    }

    public HttpServletResponse getServletResponse(){
        return responseHolder.get();
    }

    public static ThreadLocal<HttpServletRequest> getRequestHolder() {
        return requestHolder;
    }

    public static void setRequestHolder(ThreadLocal<HttpServletRequest> requestHolder) {
        WebContext.requestHolder = requestHolder;
    }

    public static ThreadLocal<HttpServletResponse> getResponseHolder() {
        return responseHolder;
    }

    public static void setResponseHolder(ThreadLocal<HttpServletResponse> responseHolder) {
        WebContext.responseHolder = responseHolder;
    }
}
