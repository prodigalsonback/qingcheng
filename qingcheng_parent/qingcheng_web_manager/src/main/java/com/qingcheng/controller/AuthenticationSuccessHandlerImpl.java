package com.qingcheng.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.qingcheng.pojo.system.LoginLog;
import com.qingcheng.service.system.LoginLogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Reference
    private LoginLogService loginLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //登录后会调用
        System.out.println("登录成功了，我要在这里记录日志");

        String loginName = authentication.getName();
        String ip = httpServletRequest.getRemoteAddr();

        LoginLog loginLog=new LoginLog();
        loginLog.setLoginName(loginName);//当前登录管理员
        loginLog.setLoginTime(new Date());//当前登录时间
        loginLog.setIp(ip);//远程客户端ip
        loginLog.setLocation(getCityByIP(ip)); //地区
        String agent = httpServletRequest.getHeader("user-agent");
        System.out.println("agent:"+agent);
        loginLog.setBrowserName(getBrowserName(agent));//浏览器名称

        loginLogService.add(loginLog);
        httpServletRequest.getRequestDispatcher("/main.html").forward(httpServletRequest,httpServletResponse);

    }
    //GET字符集设置处理
    public static Map<String, String> convertCharsetToUTF8(Map<String, String> searchMap) throws Exception {
        Iterator<Map.Entry<String, String>> entries = searchMap.entrySet().iterator();
        Map map = new HashMap();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            map.put(new String(entry.getKey().getBytes("ISO8859-1"), "UTF-8"), new String(entry.getValue().getBytes("ISO8859-1"), "UTF-8"));
        }
        return map;
    }

    public static String getCityByIP(String ip) throws JSONException {
        if(ip.equals("0:0:0:0:0:0:0:1")){
            return "本地";
        }
        try {
            URL url = new URL("http://opendata.baidu.com/api.php?query=" + ip + "&co=&resource_id=6006&t=1433920989928&ie=utf8&oe=utf-8&format=json");
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            JSONObject jsStr = JSONObject.parseObject(result.toString());
            JSONArray jsData = (JSONArray) jsStr.get("data");
            JSONObject data = (JSONObject) jsData.get(0);//位置
            return (String) data.get("location");
        } catch (IOException e) {
            return "读取失败";
        }
    }


    //浏览器类型
    public static String getBrowserName(String agent) {
        agent=agent.toLowerCase();
        if (agent.indexOf("msie 7") > 0) {
            return "ie7";
        } else if (agent.indexOf("msie 8") > 0) {
            return "ie8";
        } else if (agent.indexOf("msie 9") > 0) {
            return "ie9";
        } else if (agent.indexOf("msie 10") > 0) {
            return "ie10";
        } else if (agent.indexOf("msie") > 0) {
            return "ie";
        } else if (agent.indexOf("opera") > 0) {
            return "opera";
        } else if (agent.indexOf("chrome") > 0) {
            return "chrome";
        } else if (agent.indexOf("firefox") > 0) {
            return "firefox";
        } else if (agent.indexOf("webkit") > 0) {
            return "webkit";
        } else if (agent.indexOf("gecko") > 0 && agent.indexOf("rv:11") > 0) {
            return "ie11";
        } else {
            return "others";
        }
    }
}

