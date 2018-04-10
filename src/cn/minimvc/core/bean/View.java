/**
 * Copyright © 2018, TaoDing
 * <p>
 * All Rights Reserved.
 */

package cn.minimvc.core.bean;

import cn.minimvc.core.enums.DispatchActionEnum;

/**
 * 视图
 *
 * @author Leon
 * @version 2018/4/2 15:59
 */
public class View {
    private String url;
    private String dispathcAction = DispatchActionEnum.FORWARD.getKey();

    public View() {
    }

    public View(String url, String dispathcAction) {
        this.url = url;
        this.dispathcAction = dispathcAction;
    }

    public View(String url) {
        this.url = url;
    }

    public View (String url, String dispathcAction, String name, Object value) {
        this.url = url;
        this.dispathcAction = dispathcAction;
        ViewData data = new ViewData();
        data.put(name, value);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDispathcAction() {
        return dispathcAction;
    }

    public void setDispathcAction(String dispathcAction) {
        this.dispathcAction = dispathcAction;
    }
}
