package com.company.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "")
public class TimeServlet extends HttpServlet {
    private final static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'x");
    String initTime;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        initTime=OffsetDateTime.now().atZoneSameInstant(ZoneId.of(parseTimeZone(req))).toOffsetDateTime().format(FORMAT);

        resp.setContentType("text/html; charset=utf-8");
        resp.getWriter().write(initTime);
        resp.getWriter().close();
    }
    private String parseTimeZone (HttpServletRequest request){
        if(request.getParameterMap().containsKey("timezone")){
            return request.getParameter("timezone");
        }
        return "UTC";
    }

    @Override
    public void destroy() {
        initTime=null;
    }
}
