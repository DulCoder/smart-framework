package com.web.framework;

import com.sun.deploy.net.HttpResponse;
import com.web.framework.bean.Data;
import com.web.framework.bean.Handler;
import com.web.framework.bean.Param;
import com.web.framework.bean.View;
import com.web.framework.helper.*;
import com.web.framework.utils.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhengxianyou on 2018/3/26 0026.
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关Helper类
        HelperLoader.init();
        //获取ServletContext对象 用于注册Servlet
        ServletContext servletContext = config.getServletContext();
        //注册处理jsp的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
        //注册处理静态资源的Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

        UploadHelper.init(servletContext);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper.init(req,resp);
        try {
            //获取请求方法与路径
            String requestMethod = req.getMethod().toLowerCase();
            String requestPath = req.getPathInfo();

            if (requestPath.equals("/favicon.ico")) {
                return;
            }

            //获取Action 处理器
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (null != handler) {
                //获取Controller类及其 Bean实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);
                //创建请求参数对象
                Param param;
                if (UploadHelper.isMultipart(req)) {
                    param = UploadHelper.createParam(req);
                } else {
                    param = RequestHelper.createParam(req);
                }

                //调用Action 方法
                Object result = null;
                Method actionMethod = handler.getActionMethod();
                if (param.isEmpty()) {  //参数为空
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                } else {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }

                //处理 Action 方法返回值
                if (result instanceof View) {
                    //返回Jsp页面
                    handleViewResult((View) result, req, resp);
                } else if (result instanceof Data) {
                    //返回JSON数据
                    handleDataResult((Data) result, resp);
                }
            }
        } finally {
            ServletHelper.destroy();
        }
    }


    /**
     * 处理视图请求
     *
     * @param view
     * @param req
     * @param resp
     * @throws IOException
     * @throws ServletException
     */
    private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                resp.sendRedirect(req.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
            }
        }
    }

    /**
     * 处理数据请求
     *
     * @param data
     * @param resp
     * @throws IOException
     */
    private void handleDataResult(Data data, HttpServletResponse resp) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

}
