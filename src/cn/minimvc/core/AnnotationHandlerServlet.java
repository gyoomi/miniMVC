/**
 * Copyright © 2018, TaoDing
 * <p>
 * All Rights Reserved.
 */

package cn.minimvc.core;

import cn.minimvc.core.annotation.ControllerKey;
import cn.minimvc.core.annotation.RequestMappingKey;
import cn.minimvc.core.bean.RequestMapingMap;
import cn.minimvc.core.bean.View;
import cn.minimvc.core.bean.WebContext;
import cn.minimvc.core.enums.DispatchActionEnum;
import cn.minimvc.core.utils.BeanUtils;
import cn.minimvc.core.utils.ScanClassUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 注解处理器
 *
 * @author Leon
 * @version 2018/4/10 15:22
 */
public class AnnotationHandlerServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 必须调用父类方法，不然在getServletContext()时会报NPE
        super.init(config);
        System.out.println("---Init RequestMappingMap Start---");
        String basePackage = config.getInitParameter("basePackage");
        if (basePackage.indexOf(",") > 0) {
            String[] bpsArrToUse = basePackage.split(",");
            for (String bpToUse : bpsArrToUse) {
                initRequestMappingMap(bpToUse);
            }
        } else {
            initRequestMappingMap(basePackage);
        }
        System.out.println("---Init RequestMappingMap End---");
    }

    /**
     *扫描包下的controller注解类，
     * 并初始化RequestMappingMap
     *
     *
     * @param packageName
     */
    public void initRequestMappingMap(String packageName){
        Set<Class<?>> classes = ScanClassUtil.getClasses(packageName);
        classes.forEach((clazz) -> {
            // 判断是否是ControllerKey的注解类
            if (clazz.isAnnotationPresent(ControllerKey.class)){
                Method[] methodsToMap = BeanUtils.findDeclaredMethods(clazz);
                // 判断方法含有RequestMappingKey的方法
                for (Method method : methodsToMap) {
                    if (method.isAnnotationPresent(RequestMappingKey.class)){
                        String urlToMap = method.getAnnotation(RequestMappingKey.class).value();
                        if (urlToMap != null && urlToMap.trim() != ""){
                            if (RequestMapingMap.getRequesetMap().containsKey(urlToMap)){
                                throw new RuntimeException("RequestMappingKey Value Must Not Be The Same");
                            }
                            RequestMapingMap.put(urlToMap, clazz);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    /**
     * url处理
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        // 将当前req对象存在ThreadLoacl中，以便其他类中进行使用
        WebContext.requestHolder.set(req);
        WebContext.responseHolder.set(resp);
        String url = parseRequestURL(req);
        Class<?> clazz = RequestMapingMap.getRequesetMap().get(url);
        Object instanceClazz = BeanUtils.instanceClass(clazz);
        Method m = null;
        Method[] methods = BeanUtils.findDeclaredMethods(clazz);
        for (Method method : methods) {
            if (method.isAnnotationPresent(RequestMappingKey.class)) {
                String valueToUse = method.getAnnotation(RequestMappingKey.class).value();
                if (valueToUse != null && valueToUse.trim() != "" && url.equals(valueToUse)){
                    m = method;
                    break;
                }
            }
        }

        try {
            Object returnObject = m.invoke(instanceClazz);
            if (returnObject != null) {
                View view = (View) returnObject;
                String dispathcAction = view.getDispathcAction();
                if (DispatchActionEnum.FORWARD.getKey().equals(dispathcAction)){
                    req.getRequestDispatcher(view.getUrl()).forward(req, resp);
                } else if (DispatchActionEnum.REDIRECT.getKey().equals(dispathcAction)){
                    resp.sendRedirect(req.getContextPath() + view.getUrl());
                } else {
                    req.getRequestDispatcher(view.getUrl()).forward(req, resp);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析请求url
     *
     * @param req
     * @return
     */
    public String parseRequestURL(HttpServletRequest req) {
        String contextPath = req.getContextPath();
        String requestURI = req.getRequestURI();
        String urlToUse = requestURI.replaceFirst(contextPath, "");
        return urlToUse.substring(0, urlToUse.lastIndexOf("."));
    }
}
