/**
 * Copyright © 2018, TaoDing
 * <p>
 * All Rights Reserved.
 */

package cn.minimvc.core.bean;

import javax.servlet.http.HttpServletRequest;

/**
 * request范围的数据存储类
 *
 * @author Leon
 * @version 2018/4/2 16:01
 */
public class ViewData {
    private HttpServletRequest req;

    public ViewData() {
        this.req = WebContext.getRequestHolder().get();
    }

    public void put(String name, Object value) {
        this.req.setAttribute(name, value);
    }
}
