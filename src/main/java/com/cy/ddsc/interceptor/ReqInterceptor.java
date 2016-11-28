package com.cy.ddsc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cy.ddsc.entity.UseLogin;

import java.io.PrintWriter;

/**
 Created by 宣其成 on 2016/5/8 0008.
 * 拦截器 用法 sessionKey 用于拦截的Object key redirectUrl 拦截后重定向的url requestUrlKey
 * 用于记录拦截前的url 存的key inflag true 拦截后先弹窗再跳转，false 不弹窗提示，直接跳转
 * */
public class ReqInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private String mappingURL;//利用正则映射到不拦截的路径  
    private String indexURL;
    private String countURL;

    public void setCountURL(String countURL) {    
        this.countURL = countURL;    
    }
    public void setIndexURL(String indexURL) {    
        this.indexURL = indexURL;    
    }
    
    public void setMappingURL(String mappingURL) {    
        this.mappingURL = mappingURL;    
    } 
    @Override
    public void afterCompletion(HttpServletRequest req,
                                HttpServletResponse resp, Object obj, Exception ex)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse resp,
                           Object obj, ModelAndView mv) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
    	logger.info("request.getServletPath():"+request.getServletPath());
    	HttpSession session = request.getSession(false);
    	if(session==null){
    		redirectLoginPage(request,response);
    		return false;
    	}
    	if(session.getAttribute("user")==null){
    		redirectLoginPage(request,response);
    		return false;
    	}
    	 UseLogin user = (UseLogin) session.getAttribute("user");
    	 int userType = user.getUserType();
 	    if(userType==1||userType==2){
 	    	return true;
 	    }
 	    if(userType==3){
 	    	String url=request.getRequestURI().toString();
 	    	if(countURL==null||url.matches(countURL)||url.matches(indexURL)){
 	        	return true;
 	        }	
 	    }
 	    if(userType==4){
 	        String url=request.getRequestURI().toString();
 	        if(mappingURL==null||url.matches(mappingURL)||url.matches(indexURL)){
 	        	return true;
 	        }
 	    }
 		return false;
    }

    /**
     * 判断是正常的request还是ajax请求 true:正常请求 false:ajax请求
     * @param request
     * @return
     */
    public boolean isHttpRequest(HttpServletRequest request) {
        // 如果是ajax请求响应头会有，x-requested-with；
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase(
                "XMLHttpRequest")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 未登录用户重定向到登录页面
     * @param request
     * @param response
     */
    public void redirectLoginPage(HttpServletRequest request,
                                  HttpServletResponse response) {
        try {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<html><head></head>");
            out.println("<body><script>");
            out.println("var wnd=this.window;");
            out.println("while(1){");
            out.println("if(wnd==wnd.parent){");
            out.println("break;");
            out.println("}else");
            out.println("wnd=wnd.parent;");
            out.println("}");
            out.println("wnd.location.href='"
                    + ((HttpServletRequest) request).getContextPath() + "/tologin'");
            out.println("</script>");
            out.println("</body></html>");
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}

