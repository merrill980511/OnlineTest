package com.merrill.onlineTest.utils;

import com.merrill.onlineTest.dao.ISortDAO;
import com.merrill.onlineTest.dao.impl.SortDAOImpl;
import com.merrill.onlineTest.domain.Admin;
import com.merrill.onlineTest.domain.Sort;
import com.merrill.onlineTest.domain.TestPaper;
import com.merrill.onlineTest.domain.User;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Req2Obj {
    public static User req2User(HttpServletRequest req, User user){
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String sex = req.getParameter("sex");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String qq = req.getParameter("qq");
        String remark = req.getParameter("remark");
        if (StringUtils.isNotBlank(id)){
            user.setId(Long.valueOf(id));
        }
        if (StringUtils.isNotBlank(password)){
            MD5Util md5Util = new MD5Util();
            user.setPassword(md5Util.getMD5ofStr(password));
        }
        if (StringUtils.isNotBlank(name)){
            user.setName(name);
        }
        if (StringUtils.isNotBlank(sex)){
            user.setSex(sex);
        }
        if (StringUtils.isNotBlank(phone)){
            user.setPhone(phone);
        }
        if (StringUtils.isNotBlank(email)){
            user.setEmail(email);
        }
        if (StringUtils.isNotBlank(qq)){
            user.setQq(qq);
        }
        if (StringUtils.isNotBlank(remark)){
            user.setRemark(remark);
        }
        return user;
    }

    public static Admin req2Admin(HttpServletRequest req, Admin admin){
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String remark = req.getParameter("remark");
        if (StringUtils.isNotBlank(id)){
            admin.setId(new Long(id));
        }
        if (StringUtils.isNotBlank(name)){
            admin.setName(name);
        }
        if (StringUtils.isNotBlank(sex)){
            admin.setSex(sex);
        }
        if (StringUtils.isNotBlank(email)){
            admin.setEmail(email);
        }
        if (StringUtils.isNotBlank(phone)){
            admin.setPhone(phone);
        }
        if (StringUtils.isNotBlank(remark)){
            admin.setRemark(remark);
        }
        String password = req.getParameter("password");
        if (StringUtils.isNotBlank(password)){
            MD5Util md5Util = new MD5Util();
            admin.setPassword(md5Util.getMD5ofStr(password));
        }
        return admin;
    }

    public static TestPaper req2TestPaper(HttpServletRequest req, TestPaper testPaper){
        TestPaper testpaper_in_session = (TestPaper) req.getSession().getAttribute("TESTPAPER_IN_SESSION");
        if (testpaper_in_session != null){
            return testpaper_in_session;
        } else {
            String testPaperId = req.getParameter("testPaperId");
            String name = req.getParameter("name");
            String sort = req.getParameter("sort");
            String startTime = req.getParameter("startTime");
            String endTime = req.getParameter("endTime");
            String time = req.getParameter("time");
            if (StringUtils.isNotBlank(testPaperId)) {
                testPaper.setId(new Long(testPaperId));
            }
            if (StringUtils.isNotBlank(name)){
                testPaper.setName(name);
            }
            if (StringUtils.isNotBlank(sort)){
                ISortDAO dao = new SortDAOImpl();
                Sort sort1 = dao.getSortById(new Long(sort));
                testPaper.setSort(sort1);
            }
            if (StringUtils.isNotBlank(startTime)){
                testPaper.setStartTime(Timestamp.valueOf(startTime));
            }
            if (StringUtils.isNotBlank(endTime)){
                testPaper.setStartTime(Timestamp.valueOf(endTime));
            }
            if (StringUtils.isNotBlank(time)){
                testPaper.setTime(Integer.parseInt(time));
            }
            System.out.println(testPaper);
            req.getSession().setAttribute("TESTPAPER_IN_SESSION", testPaper);
            return testPaper;
        }
    }
}
