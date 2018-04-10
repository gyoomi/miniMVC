/**
 * Copyright © 2018, TaoDing
 * <p>
 * All Rights Reserved.
 */

package cn.minimvc.core.enums;

/**
 * 跳转方式的改变
 *
 * @author Leon
 * @version 2018/4/2 14:56
 */
public enum DispatchActionEnum {
    FORWARD("forward"),REDIRECT("redirect");

    private String key;

    private DispatchActionEnum(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
