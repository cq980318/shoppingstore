package controller;

import entity.Userinfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import serviceImpl.UserServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class LoginAndRegistController {
    @Autowired
    UserServiceImpl usi;
//QQ
    @RequestMapping("/register")
    public String register(@RequestParam String username,@RequestParam String password,@RequestParam String email){
        //不为空说明用户名已经存在
        if(usi.selectByUsername(username)!=null){
            return "existed";
        }else{
            Userinfo ui=new Userinfo();
            ui.setUsername(username);
            ui.setPassword(DigestUtils.md5Hex(password.getBytes()));
            ui.setEmail(email);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ui.setRegisterTime(sdf.format(date));
            int line = usi.insert(ui);
            if(line>0){
                return "yes";
            }else{
                return  "no";
            }
        }

    }
    @RequestMapping("/login")
    public String login(@RequestParam String username,@RequestParam String password, @RequestParam String flag, HttpServletRequest req,HttpServletResponse resp){
        Userinfo userinfo = usi.selectByUsername(username);
        String md5pwd = DigestUtils.md5Hex(password);
        if(userinfo==null){
            return "unexist";
        }else{
            if(md5pwd.equals(userinfo.getPassword())){
//                if(flag.equals("yes")){
                    Cookie cookieu = new Cookie("username",username);
                    Cookie cookiep= new Cookie("password",password);
                    Cookie cookief = new Cookie("flag", flag);

                    cookieu.setMaxAge(50000);
                    cookiep.setMaxAge(50000);
                    cookief.setMaxAge(50000);
                    resp.addCookie(cookieu);
                    resp.addCookie(cookiep);
                    resp.addCookie(cookief);

                    //有cookie不要session
//                    req.getSession().removeAttribute("username");
//                    userinfo.setPassword(password);
//                    HttpSession session = req.getSession();
//                    session.setAttribute("info",userinfo);
//                    System.out.println("lalalalalalalalalal"+session.getAttribute("info"));

//                }else {
                    //1.req.getSession().setAttribute("username",username);
                    //2.
//                    Cookie cookieu2 = new Cookie(username,username);
//                    cookieu2.setMaxAge(50000);
//                    resp.addCookie(cookieu2);
                  //3.


//                    //只要未勾选,不记住值
//                    //req.getSession().removeAttribute("info");
//                    Cookie[] cookies = req.getCookies();
//                    if (cookies.length > 0) {
//                        for (Cookie cookie : cookies) {
//                            //没有cookie加session
//                            if (cookie.getName().equals("username")){
//
//                            }
//                            if (cookie.getName().equals("username")|| cookie.getName().equals("password")) {
////                                cookie.setMaxAge(0);
//                                cookie.setValue(null);//仅仅设置为null后还有""回显到页面
//                                resp.addCookie(cookie);
//                            }
//                        }
//                    }
//                }
                return "yes";
            }else{
                return "no";
            }
        }
    }

    @RequestMapping("/find")
    public String find(@RequestParam String  username,@RequestParam String pwd){
        Userinfo ui = usi.selectByUsername(username);
        if(ui!=null){
            ui.setPassword(DigestUtils.md5Hex(pwd));
            int line = usi.updateByPrimaryKey(ui);
            System.out.println("line"+line);
            if(line>0){
                return "yes";
            }else{
                return "no";
            }

        }else{
            return "unexist";
        }
    }

//    @RequestMapping("/session")
//    public String getSession(HttpSession session){
//        Userinfo info =(Userinfo) session.getAttribute("info");
//        System.out.println("获取session里的用户名:"+info.getUsername()+",密码:"+info.getPassword());
//        return info.getUsername()+","+info.getPassword();
//    }
//@RequestMapping("/cookie")
//    public String getCookie(HttpServletRequest request){
//    String coo=null;
//    Cookie[] cookies = request.getCookies();
//    if(cookies!=null) {
//        for (Cookie c : cookies) {
//            if ("USER".equals(c.getName())) {
//                String username = c.getValue().split(",")[0];
//                String password = c.getValue().split(",")[1];
//                System.out.println("controller里的名字:" + username + ",密码:" + password);
//                coo = c.getValue();
//                break;
//            }
//        }
//    }
//return coo;
//}

}
